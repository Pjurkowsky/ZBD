package orm.lol.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.entites.person.Person;
import orm.lol.repos.PersonRepository;

@RestController
@RequestMapping("/person")
@Tag(name = "Person")
public class PersonController {

    @Autowired
    PersonRepository personRepo;

    @RequestMapping(
        value = "/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Gets all persons")
    public String getAllPersons() {
        List<Person> personList = personRepo.findAll();
        JSONArray jsonArray = new JSONArray(
            personList.stream().map(Person::toJSON).toList()
        );

        return jsonArray.toString();
    }

    @RequestMapping(
        value = "/{guid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get person by GUID")
    public String getPersonByGUID(
        @PathVariable UUID guid,
        @RequestParam Boolean full
    ) {
        Optional<Person> person = personRepo.findByRowguid(guid);

        if (person.isEmpty()) {
            return JSONObject.NULL.toString();
        }

        return person.get().toJSON().toString();
    }

//    @RequestMapping(
//            value = "/",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    @Operation(summary = "Get people by Business Entity Address")
//    public ResponseEntity<PersonDto> getPersonsByBusinessEntityAddresses(
//            @RequestParam Integer id,
//            @RequestParam Integer times
//    ) {
//        for(int i =0; i < times - 1;  i++) {
//            personRepo.findByBusinessEntity_BusinessEntityId(id);
//        }
//        Person person = personRepo.findByBusinessEntity_BusinessEntityId(id).orElseThrow(
//
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(PersonDto.toDto(person));
//
//    }

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
            personRepo.findByPersonType(type);
        }
        List<Person> persons = List.of();
        return ResponseEntity.status(HttpStatus.OK).body(persons);

    }

    @RequestMapping(
            value = "/type-native/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Person>> getPersonByTypeNative(
            @RequestParam String type,
            @RequestParam Integer times
    ) {
        for(int i =0; i < times;  i++) {
            personRepo.findByPersonTypeNative(type);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(personRepo.findByPersonType(type));
    }
}
