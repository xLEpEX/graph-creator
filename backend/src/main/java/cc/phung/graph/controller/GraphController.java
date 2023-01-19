package cc.phung.graph.controller;


import cc.phung.graph.models.dtos.EdgeDTO;
import cc.phung.graph.models.dtos.GraphDTO;
import cc.phung.graph.models.dtos.NodeDTO;
import cc.phung.graph.service.GraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
*
• The API allows management of exactly one graph.
• The API allows to add nodes to the graph.
• The API allows to add edges to the graph.
• The API allows to retrieve the full graph.
• The API allows to retrieve nodes in topological order.
* */
@RestController
@RequestMapping("/api/v1/graph")
@Tag(name = "Graph", description = "Create your basic graph")
public class GraphController {
    private static final Logger logger = LoggerFactory.getLogger(GraphController.class);
    @Autowired
    private GraphService graphService;

    @Operation(summary = "get the graph")
    @GetMapping("/")
    public ResponseEntity<GraphDTO> getGraph() {
        return graphService.getGraph();
    }

    @Operation(summary = "get the nodes of the graph in topological order")
    @GetMapping("/topological")
    public ResponseEntity getTopological() {
        return graphService.getTopological();
    }

    @Operation(summary = "add a node to the graph")
    @PostMapping("/nodes")
    public ResponseEntity<JSONObject> addNode(@RequestBody NodeDTO nodeDTO) {
        return graphService.insertNode(nodeDTO);
    };

    @Operation(summary = "Delete a node by uuid")
    @DeleteMapping("/nodes/{uuid}")
    public ResponseEntity<JSONObject> removeNode(@PathVariable String uuid) {
        return graphService.removeNode(uuid);
    };

    @Operation(summary = "add a edge between to nodes to your graph")
    @PostMapping("/edges")
    public ResponseEntity<JSONObject> addEdge(@RequestBody EdgeDTO edgeDTO) {
        return  graphService.insertEdge(edgeDTO);
    };

    @Operation(summary = "delete a edge by uuid")
    @DeleteMapping("/edges/{uuid}")
    public ResponseEntity<JSONObject> RemoveEdge(@PathVariable String uuid) {
        return graphService.removeEdge(uuid);
    }

}
