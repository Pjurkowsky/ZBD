package orm.lol.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.dtos.PersonDetailDTO;
import orm.lol.dtos.PersonDto;
import orm.lol.entites.person.EmailAddress;
import orm.lol.entites.person.Person;
import orm.lol.repos.PersonRepository;
import orm.lol.specifications.PersonSpecifications;

@RestController
@RequestMapping("/person")
@Tag(name = "Person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(
        PersonController.class
    );

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
            personList.stream().map(person -> person.toJSON()).toList()
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

    @RequestMapping(
        value = "/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get people by Business Entity Address")
    public ResponseEntity<PersonDto> getPersonsByBusinessEntityAddresses(
        @RequestParam Integer id,
        @RequestParam Integer times,
        @RequestParam Boolean useNative
    ) {
        Person person;

        if (useNative) {
            for (int i = 0; i < times - 1; i++) {
                personRepo.findByBusinessEntity_BusinessEntityId_Native(id);
            }

            person = personRepo
                .findByBusinessEntity_BusinessEntityId_Native(id)
                .orElseThrow();
        } else {
            for (int i = 0; i < times - 1; i++) {
                personRepo.findByBusinessEntity_BusinessEntityId(id);
            }

            person = personRepo
                .findByBusinessEntity_BusinessEntityId(id)
                .orElseThrow();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            PersonDto.toDto(person)
        );
    }

    @RequestMapping(
        value = "/email/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get people by Email Address")
    public String asdads(@RequestParam String mail) {
        Specification<Person> spec = PersonSpecifications.hasEmailLike(mail);
        List<Person> persons = personRepo.findAll(spec);
        // logger.info(
        //     "{} {} {}",
        //     persons.get(0).getFirstName(),
        //     persons.get(0).getLastName(),
        //     persons.get(0).getRowguid()
        // );
        //
        JSONArray jsonArray = new JSONArray(
            persons.stream().map(person -> person.toJSON()).toList()
        );

        return jsonArray.toString();
        // return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @RequestMapping(
        value = "/many-join/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Multiple join")
    public ResponseEntity<List<PersonDetailDTO>> getAllPersonDetails() {
        List<Tuple> tuples = personRepo.findAll(
            PersonSpecifications.fetchAllRelatedEntitiesAsDTO()
        );
        List<PersonDetailDTO> retList = tuples
            .stream()
            .map(tuple ->
                new PersonDetailDTO(
                    tuple.get("personBusinessEntityId", Integer.class),
                    tuple.get("firstName", String.class),
                    false,
                    tuple.get("lastName", String.class),
                    // ... get other Person fields

                    null,
                    null,
                    null,
                    null,
                    tuple.get("businessEntityBusinessEntityId", Integer.class),
                    // ... get other BusinessEntity fields

                    tuple.get("passwordHash", String.class),
                    // ... get other Password fields

                    null,
                    null,
                    null,
                    tuple.get("emailAddressBusinessEntityId", Integer.class),
                    null,
                    tuple.get("emailAddress", String.class),
                    // ... get other EmailAddress fields

                    null,
                    tuple.get("personPhoneBusinessEntityId", Integer.class),
                    null,
                    tuple.get("phoneNumber", String.class),
                    // ... get other PersonPhone fields

                    tuple.get(
                        "phoneNumberTypePhoneNumberTypeId",
                        Integer.class
                    ),
                    tuple.get("phoneNumberTypeName", String.class),
                    // ... get other PhoneNumberType fields

                    null,
                    tuple.get(
                        "businessEntityAddressBusinessEntityId",
                        Integer.class
                    ),
                    // ... get other BusinessEntityAddress fields

                    null,
                    tuple.get("addressAddressId", Integer.class),
                    null,
                    tuple.get("addressLine1", String.class),
                    tuple.get("city", String.class),
                    // ... get other Address fields

                    null,
                    tuple.get("addressTypeAddressTypeId", Integer.class),
                    tuple.get("addressTypeName", String.class),
                    // ... get other AddressType fields

                    null,
                    tuple.get("stateProvinceStateProvinceId", Integer.class),
                    tuple.get("stateProvinceName", String.class),
                    // ... get other StateProvince fields

                    null,
                    tuple.get("countryRegionCountryRegionCode", String.class),
                    tuple.get("countryRegionName", String.class), // ... get other CountryRegion fields
                    null,
                    false,
                    null,
                    null,
                    null
                )
            )
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(retList);
    }
}
