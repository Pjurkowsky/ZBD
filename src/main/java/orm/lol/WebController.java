package orm.lol;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.entites.person.Person;
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

    @RequestMapping(
        value = "/person/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Gets all persons")
    public String getAllPersons() {
        List<Person> personList = personRepo.findAll();
        JSONArray jsonArray = new JSONArray(
            personList.stream().map(person -> person.toJSON()).toList()
        );

        return jsonArray.toString();
    }

    @RequestMapping(
        value = "/person/{guid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get person by GUID")
    public String getAllPersons(@PathVariable UUID guid) {
        Optional<Person> person = personRepo.findByRowguid(guid);

        if (person.isEmpty()) {
            return JSONObject.NULL.toString();
        }

        return person.get().toJSON().toString();
    }
}
