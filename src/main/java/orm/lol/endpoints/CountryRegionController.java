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
import orm.lol.entites.person.CountryRegion;
import orm.lol.repos.CountryRegionRepository;

@RestController
@RequestMapping("/country-region")
@Tag(name = "Country Region")
public class CountryRegionController {

    @Autowired
    CountryRegionRepository countryRegionRepository;

    @RequestMapping(
        value = "/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Gets all country regions")
    public String getAllCountryRegions() {
        List<CountryRegion> regionsList = countryRegionRepository.findAll();
        JSONArray jsonArray = new JSONArray(
            regionsList.stream().map(region -> region.toJSON()).toList()
        );

        return jsonArray.toString();
    }

    @RequestMapping(
        value = "/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get country region by code")
    public String getCountryRegionByCode(@PathVariable String code) {
        Optional<CountryRegion> region =
            countryRegionRepository.findByCountryRegionCode(code);

        if (region.isEmpty()) {
            return JSONObject.NULL.toString();
        }

        return region.get().toJSON().toString();
    }
}
