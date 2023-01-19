package cc.phung.graph.service;


import cc.phung.graph.models.dtos.EdgeDTO;
import cc.phung.graph.models.dtos.GraphDTO;
import cc.phung.graph.models.dtos.NodeDTO;
import cc.phung.graph.repo.EdgeRepo;
import cc.phung.graph.repo.NodeRepo;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class GraphService {
    @Autowired
    private EdgeRepo edgeRepo;
    @Autowired
    private NodeRepo nodeRepo;

    @Value("${graph.self-linking}")
    private boolean selfLinkingAllowed;
    @Value("${graph.both-direction}")
    private boolean bothDirectionAllowed;


    private final Logger logger = LoggerFactory.getLogger(GraphService.class);

    public ResponseEntity<GraphDTO> getGraph() {
        return new ResponseEntity<GraphDTO>(new GraphDTO(edgeRepo.findAll(), nodeRepo.findAll()), HttpStatus.OK);
    }

    /**
     * get the nodes in topological order
     * @return ResponseEntity of the nodes in topological oder or a error message if any problems occurs
     */
    public ResponseEntity getTopological() {
        final var edges = new ArrayList<>(edgeRepo.findAll());
        final var nodes = new ArrayList<>(nodeRepo.findAll());
        final var sortedNods = new ArrayList<NodeDTO>();
        final var stackNodes = new Stack<String>();

        var loop = false;
        while (nodes.size() > 0 && !loop) {

            if(stackNodes.isEmpty()) {
                stackNodes.push(nodes.get(0).getUuid());
            }
            var activeNodeId = stackNodes.get(stackNodes.size()-1);

            var connections = getFilteredConnections(edges, activeNodeId, sortedNods);

            //check if there is a cycle dependency
            loop = checkForCycleDependency(connections, stackNodes);


            if(connections.isEmpty()) {
                var uuid = stackNodes.pop();
                var foundNode = nodes.stream().filter(node -> node.getUuid().equals(uuid)).toList().get(0);
                sortedNods.add(0, foundNode);
                nodes.remove(foundNode);
            } else {
                stackNodes.add(connections.get(0).getDestionantionId());
            }
        }

        if(loop) {
            return getDefaultResponse(HttpStatus.NOT_FOUND, "no topological sort available due to cycle dependency");
        }
        return new ResponseEntity<ArrayList<NodeDTO>>(sortedNods, HttpStatus.OK);
    }

    /**
     * check if the node ends in a cycle dependency
     * @param connections the connection of active nodes
     * @param stackNodes the stacked nodes
     * @return true if we detected a cycle dependency
     */
    private boolean checkForCycleDependency(List<EdgeDTO> connections, Stack<String> stackNodes) {
        var cycleDetected = false;
        for (var nodeDTO : stackNodes) {
            if (connections.stream().anyMatch(e -> e.getDestionantionId().equals(nodeDTO))) {
                cycleDetected = true;
                break;
            }
        }
        return cycleDetected;
    }

    /**
     * get all connections of the provided node id filtered by the remaining not visited nodes
     * @param edges All edges of the graph
     * @param activeNodeId id of the node to get the connections from
     * @param sortedNods visited nodes
     * @return List of remaining connections
     */
    private List<EdgeDTO> getFilteredConnections(List<EdgeDTO> edges, String activeNodeId, ArrayList<NodeDTO> sortedNods) {
       return edges.stream().filter(edge -> edge.getSourceId().equals(activeNodeId) && sortedNods.stream().noneMatch(e -> e.getUuid().equals(edge.getDestionantionId()))).toList();
    }


    /**
     * add a new node to the graph or override an existing
     * @param nodeDTO node object
     * @return ResponseEntity of the status of the request
     */
    //müsste mann design technisch entscheiden ob direkt an diesem endpoint eine node überschreiben werden darf oder dafür ein extra endpoint angelegt werden sollte
    public ResponseEntity<JSONObject> insertNode(NodeDTO nodeDTO) {
        nodeRepo.save(nodeDTO);
        return getDefaultResponse(HttpStatus.OK, "node have been added successfully");
    };

    /**
     * remove a node with all existing edges from the graph
     * @param uuid id of the node that should get removed
     * @return ResponseEntity of the status of the request
     */
    public ResponseEntity<JSONObject> removeNode(String uuid) {
        var removeEdges = edgeRepo.findBySourceIdOrDestionantionId(uuid, uuid).stream().map(EdgeDTO::getUuid).toList();
        edgeRepo.deleteByUuidIn(removeEdges);
        var deleted = nodeRepo.deleteByUuid(uuid);
        if(deleted == 0) {
            return getDefaultResponse(HttpStatus.NOT_FOUND, "no node has been found");
        }
        return getDefaultResponse(HttpStatus.OK, "node and all connecting edges have been removed");
    };

    /**
     * Add a new Edge to the Graph or override an existing
     * @param edgeDTO edge object with source and destination id of the nodes
     * @return ResponseEntity of the status of the request
     */
    public ResponseEntity<JSONObject> insertEdge(EdgeDTO edgeDTO) {
        if(!selfLinkingAllowed && isSelfLinking(edgeDTO)) {
            return getDefaultResponse(HttpStatus.BAD_REQUEST, "self linking is not enabled");
        }
        if(!bothDirectionAllowed) {
            if(isToDirectionLinking(edgeDTO)) { //reduce unnecessary query
                return getDefaultResponse(HttpStatus.BAD_REQUEST, "both directing is not allowed");
            }
        };

        var sourceNode = nodeRepo.findByUuid(edgeDTO.getSourceId());
        var destinationNode = nodeRepo.findByUuid(edgeDTO.getDestionantionId());
        if(sourceNode.isEmpty()) {
            return getDefaultResponse(HttpStatus.BAD_REQUEST, "the source id is invalid");
        }
        if(destinationNode.isEmpty()) {
            return getDefaultResponse(HttpStatus.BAD_REQUEST, "the destination id is invalid");
        }
        edgeRepo.save(edgeDTO);
        return getDefaultResponse(HttpStatus.OK, "edge has been saved successfully");
    };

    /**
     * check if you try to link a node to itself
     * @param edgeDTO edge with the linking information
     * @return boolean if it is self linking
     */
    private boolean isSelfLinking(EdgeDTO edgeDTO) {
        return edgeDTO.getDestionantionId().equals(edgeDTO.getSourceId());
    }

    private boolean isToDirectionLinking(EdgeDTO edgeDTO) {
        return edgeRepo.existsBySourceIdAndDestionantionId(edgeDTO.getDestionantionId(), edgeDTO.getSourceId());
    }


    /**
     * remove a edge from the graph
     * @param uuid of the edge you want to remove
     * @return status of the operation
     */
    public ResponseEntity<JSONObject> removeEdge(String uuid) {
        var deleted = edgeRepo.deleteByUuid(uuid);
        if(deleted == 1) {
            return getDefaultResponse(HttpStatus.NOT_FOUND, "the provided edge does not exists");
        }
        return getDefaultResponse(HttpStatus.OK, "edge have been remove successfully");
    }

    /**
     * factory method to build a default response
     * @param httpStatus status code of the response
     * @param message message of the response
     * @return Response entity with message and status code
     */
    public ResponseEntity<JSONObject> getDefaultResponse(HttpStatus httpStatus, String message) {
        JSONObject response = new JSONObject();
        response.put("message", message);
        return new ResponseEntity<JSONObject>(response, httpStatus);
    }
}
