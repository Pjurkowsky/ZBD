package orm.lol.jdbc.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.entites.person.Person;
import orm.lol.jdbc.services.PersonService;

import java.util.List;

@RestController
@RequestMapping("/jdbc/person")
@Tag(name = "JdbcPerson")
public class PersonJdbcController {

    private final PersonService personService;

    public PersonJdbcController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Operation(summary = "Get all persons (jdbc)")
    public String getAllPersons() {
        List<Person> personList = personService.findAll();

        JSONArray jsonArray = new JSONArray(
                personList.stream().map(Person::toJSON).toList()
        );

        return jsonArray.toString();
    }

    @RequestMapping(
            value = "/type/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Person>> getPersonByType(
            @RequestParam String type,
            @RequestParam Integer times
    ) {
        for(int i =0; i < times;  i++) {
            personService.findByPersonType(type);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.findByPersonType(type));
    }
}
