package cc.phung.graph.api.services;

import cc.phung.graph.api.models.dtos.EdgeDTO;
import cc.phung.graph.api.models.dtos.GraphDTO;
import cc.phung.graph.api.models.dtos.NodeDTO;
import cc.phung.graph.api.repo.EdgeRepo;
import cc.phung.graph.api.repo.NodeRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GraphService {
    @Autowired
    private EdgeRepo edgeRepo;
    @Autowired
    private NodeRepo nodeRepo;

    @Value("${graph.self-linking}")
    private boolean selfLinking;

    public ResponseEntity<GraphDTO> getGraph() {
        return new ResponseEntity<GraphDTO>(new GraphDTO(edgeRepo.findAll(), nodeRepo.findAll()), HttpStatus.OK);
    }

    public void getTopological() {

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
        var removeEdges = edgeRepo.findBySourceOrDestionantion(uuid, uuid).stream().map(EdgeDTO::getUuid).toList();
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
        if(!selfLinking) {
            if(edgeDTO.getDestionantion().equals(edgeDTO.getSource())) {
                return getDefaultResponse(HttpStatus.BAD_REQUEST, "self linking is not enabled");
            }
        }
        var sourceNode = nodeRepo.findByUuid(edgeDTO.getSource());
        var destinationNode = nodeRepo.findByUuid(edgeDTO.getDestionantion());
        if(sourceNode.isEmpty()) {
            return getDefaultResponse(HttpStatus.BAD_REQUEST, "the source id is invalid");
        }
        if(destinationNode.isEmpty()) {
            return getDefaultResponse(HttpStatus.BAD_REQUEST, "the destination id is invalid");
        }
        edgeRepo.save(edgeDTO);
        return getDefaultResponse(HttpStatus.OK, "edge has been saved successfully");
    };

    public ResponseEntity<JSONObject> removeEdge(String uuid) {
        var deleted = edgeRepo.deleteByUuid(uuid);
        if(deleted == 0) {
            return getDefaultResponse(HttpStatus.NOT_FOUND, "the provided edge does not exists");
        }
        return getDefaultResponse(HttpStatus.OK, "edge have been remove successfully");
    }

    public ResponseEntity<JSONObject> getDefaultResponse(HttpStatusCode httpStatus, String message) {
        JSONObject response = new JSONObject();
        response.put("message", message);
        return new ResponseEntity<JSONObject>(response, httpStatus);
    }
}
