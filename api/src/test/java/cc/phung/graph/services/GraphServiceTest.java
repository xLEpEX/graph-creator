package cc.phung.graph.services;

import cc.phung.graph.service.GraphService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class GraphServiceTest {

    @Autowired
    private GraphService graphService = new GraphService();

    @Test
    void tryAssertCheckForCycleDependency() {
        var okResponse = graphService.getDefaultResponse(HttpStatus.OK, "everything fine");
        var badReqResponse = graphService.getDefaultResponse(HttpStatus.BAD_REQUEST, "oho that is bad");
        var notFoundResponse = graphService.getDefaultResponse(HttpStatus.NOT_FOUND, "i dont no what you want from me");
    }
}
