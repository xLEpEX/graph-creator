package cc.phung.graph.controller;


import cc.phung.graph.GraphApplication;
import cc.phung.graph.models.entry.EdgeEntry;
import cc.phung.graph.models.dtos.GraphDTO;
import cc.phung.graph.models.entry.NodeEntry;
import cc.phung.graph.repo.EdgeRepo;
import cc.phung.graph.repo.NodeRepo;
import cc.phung.graph.service.GraphService;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GraphApplication.class)
@AutoConfigureMockMvc
public class GraphControllerTest {
    @InjectMocks
    GraphController graphController;
    @MockBean
    private EdgeRepo edgeRepo;
    @MockBean
    private NodeRepo nodeRepo;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GraphService graphService;

    List<NodeEntry> defaultMockNodeList = List.of(
            new NodeEntry("node-id-1", "cool", 231,321),
            new NodeEntry("node-id-2", "cool", 231,321),
            new NodeEntry("node-id-3", "cool", 231,321),
            new NodeEntry("node-id-4", "cool", 231,321),
            new NodeEntry("node-id-5", "cool", 231,321),
            new NodeEntry("node-id-6", "cool", 231,321),
            new NodeEntry("node-id-7", "cool", 231,321)
    );
    List<EdgeEntry> defaultMockEdgeList = List.of(
            new EdgeEntry("edge-id-1", "node-id-1", "node-id-3"),
            new EdgeEntry("edge-id-2", "node-id-1", "node-id-2"),
            new EdgeEntry("edge-id-3", "node-id-2", "node-id-3"),
            new EdgeEntry("edge-id-4", "node-id-4", "node-id-3"),
            new EdgeEntry("edge-id-5", "node-id-3", "node-id-5"),
            new EdgeEntry("edge-id-6", "node-id-5", "node-id-6"),
            new EdgeEntry("edge-id-7", "node-id-6", "node-id-7")
            //new EdgeDTO("edge-id-1", "node-id-1", "node-id-3")
    );

    @Test
    @DisplayName("Test if the get Graph endpoint returns the right json content")
    public void getGraphTest() throws Exception {

        var responseObject = new GraphDTO(defaultMockEdgeList, defaultMockNodeList);
        Gson gson = new Gson();
        when(nodeRepo.findAll()).thenReturn(defaultMockNodeList);
        when(edgeRepo.findAll()).thenReturn(defaultMockEdgeList);

        mockMvc.perform(get("/api/v1/graph/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(responseObject)));

    }

    @Test
    @DisplayName("Test if the Topological endpoint returns the right json content")
    public void topologicalNormalTest() throws Exception {

        var nodeList = new ArrayList<NodeEntry>(List.of(new NodeEntry("node-id-4", "cool", 231,321),
                new NodeEntry("node-id-1", "cool", 231,321),
                new NodeEntry("node-id-2", "cool", 231,321),
                new NodeEntry("node-id-3", "cool", 231,321),
                new NodeEntry("node-id-5", "cool", 231,321),
                new NodeEntry("node-id-6", "cool", 231,321),
                new NodeEntry("node-id-7", "cool", 231,321)));
        //var refResponse = new ResponseEntity<ArrayList<NodeDTO>>(new ArrayList<>(nodeList), HttpStatus.OK);

        Gson gson = new Gson();
        var nodeListJson = gson.toJson(new ArrayList<>(nodeList));
        when(nodeRepo.findAll()).thenReturn(defaultMockNodeList);
        when(edgeRepo.findAll()).thenReturn(defaultMockEdgeList);
        // When
        mockMvc.perform(get("/api/v1/graph/topological"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(nodeListJson));
    }

    @Test
    @DisplayName("Test Topological endpoint with cycle dependency")
    public void topologicalCycleDependencyTest() throws Exception {
        List<EdgeEntry> cycleMockEdgeList = List.of(
                new EdgeEntry("edge-id-1", "node-id-1", "node-id-3"),
                new EdgeEntry("edge-id-2", "node-id-1", "node-id-2"),
                new EdgeEntry("edge-id-3", "node-id-2", "node-id-3"),
                new EdgeEntry("edge-id-4", "node-id-4", "node-id-3"),
                new EdgeEntry("edge-id-5", "node-id-3", "node-id-5"),
                new EdgeEntry("edge-id-6", "node-id-5", "node-id-6"),
                new EdgeEntry("edge-id-7", "node-id-6", "node-id-7"),
                new EdgeEntry("edge-id-8", "node-id-5", "node-id-2") // connect 5 -> 2
        );

        when(nodeRepo.findAll()).thenReturn(defaultMockNodeList);
        when(edgeRepo.findAll()).thenReturn(cycleMockEdgeList);
        JSONObject response = new JSONObject();
        response.put("message", "no topological sort available due to cycle dependency");

        mockMvc.perform(get("/api/v1/graph/topological"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(response.toJSONString()));
    }

}
