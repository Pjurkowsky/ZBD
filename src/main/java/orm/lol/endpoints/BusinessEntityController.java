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
import orm.lol.dtos.PersonDto;
import orm.lol.entites.person.BusinessEntity;
import orm.lol.entites.person.Person;
import orm.lol.repos.BusinessEntityRepository;
import orm.lol.repos.PersonRepository;

@RestController
@RequestMapping("/businessentity")
@Tag(name = "BusinessEntity")
public class BusinessEntityController {

    @Autowired
    BusinessEntityRepository businessEntityRepository;

    @RequestMapping(
        value = "/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Gets all persons")
    public String getAllBusinessEntity() {
        List<BusinessEntity> personList = businessEntityRepository.findAll();
        JSONArray jsonArray = new JSONArray(
            personList.stream().map(person -> person.toJSON()).toList()
        );

        return jsonArray.toString();
    }
}
