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
import orm.lol.entites.person.StateProvince;
import orm.lol.repos.StateProvinceRepository;

@RestController
@RequestMapping("/state-province")
@Tag(name = "State Province")
public class StateProvinceController {

    @Autowired
    StateProvinceRepository stateProvinceRepository;

    @RequestMapping(
        value = "/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Gets all state provinces")
    public ResponseEntity<List<StateProvince>> getAllProvinces(@RequestParam Integer times) {
        for(int i =0; i < times - 1;  i++) {
            ResponseEntity.ok(stateProvinceRepository.findAll());
        }
        return  ResponseEntity.ok(stateProvinceRepository.findAll());
    }

    @RequestMapping(
        value = "/guid/{guid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get state province by GUID")
    public ResponseEntity<StateProvince> getProvinceByGUID(@PathVariable UUID guid) {
        Optional<StateProvince> province =
            stateProvinceRepository.findByRowguid(guid);

        if (province.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(province.get());
    }

    @RequestMapping(
        value = "/id/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get state province by ID")
    public ResponseEntity<StateProvince> getProvinceByID(@PathVariable Integer id) {
        Optional<StateProvince> province =
            stateProvinceRepository.findByStateProvinceId(id);

        if (province.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(province.get());
    }
}
