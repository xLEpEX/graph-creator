package cc.phung.graph.api.controllers.graph;

import cc.phung.graph.api.models.dtos.EdgeDTO;
import cc.phung.graph.api.models.dtos.GraphDTO;
import cc.phung.graph.api.models.dtos.NodeDTO;
import cc.phung.graph.api.services.GraphService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

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
public class GraphController {
    @Autowired
    private GraphService graphService;

    @GetMapping("/")
    public ResponseEntity<GraphDTO> getGraph() {
        return graphService.getGraph();
    }

    @GetMapping("/topological")
    public void getTopological() {

    }

    @PostMapping("/nodes")
    public ResponseEntity<JSONObject> addNode(@RequestBody NodeDTO nodeDTO) {
        return graphService.insertNode(nodeDTO);
    };

    @DeleteMapping("/nodes/{uuid}")
    public ResponseEntity<JSONObject> removeNode(@PathVariable String uuid) {
        return graphService.removeNode(uuid);
    };

    @PostMapping("/edges")
    public ResponseEntity<JSONObject> addEdge(@RequestBody EdgeDTO edgeDTO) {
        return  graphService.insertEdge(edgeDTO);
    };

    @DeleteMapping("/edges/{uuid}")
    public ResponseEntity<JSONObject> RemoveEdge(@PathVariable String uuid) {
        return graphService.removeEdge(uuid);
    }




}
