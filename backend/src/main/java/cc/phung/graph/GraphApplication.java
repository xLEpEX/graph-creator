package cc.phung.graph;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Graph Creator", version = "1.0.0", description = "With this API you can create basic graphs.",	contact = @Contact(url = "https://graph.phung.ccc", name = "Leon Phung", email = "leon@phung.cc")))
@SpringBootApplication
public class GraphApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class, args);
	}

}
