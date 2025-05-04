package orm.lol.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public String getAllProvinces() {
        List<StateProvince> provinceList = stateProvinceRepository.findAll();
        JSONArray jsonArray = new JSONArray(
            provinceList.stream().map(province -> province.toJSON()).toList()
        );

        return jsonArray.toString();
    }

    @RequestMapping(
        value = "/{guid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get state province by GUID")
    public String getProvinceByGUID(@PathVariable UUID guid) {
        Optional<StateProvince> province =
            stateProvinceRepository.findByRowguid(guid);

        if (province.isEmpty()) {
            return JSONObject.NULL.toString();
        }

        return province.get().toJSON().toString();
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get state province by ID")
    public String getProvinceByID(@PathVariable Integer id) {
        Optional<StateProvince> province =
            stateProvinceRepository.findByStateProvinceId(id);

        if (province.isEmpty()) {
            return JSONObject.NULL.toString();
        }

        return province.get().toJSON().toString();
    }
}
