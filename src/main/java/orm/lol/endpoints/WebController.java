package orm.lol.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.repos.PersonRepository;

@RestController
@Tag(name = "Sporting events")
public class WebController {

    @Autowired
    PersonRepository personRepo;

    @GetMapping("/")
    @Operation(summary = "Gets all sporting events")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
