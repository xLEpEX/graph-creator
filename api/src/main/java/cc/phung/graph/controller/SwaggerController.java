package cc.phung.graph.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Hidden
public class SwaggerController {
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public void swaggerOnRootLevel(final HttpServletResponse httpServletResponse, final HttpServletRequest request) {
        httpServletResponse.setHeader("Location", String.format("%s/swagger-ui/index.html", request.getContextPath()));
        httpServletResponse.setStatus(302);
    }
}
