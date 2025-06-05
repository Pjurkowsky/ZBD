package orm.lol.jdbc.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.entites.person.AddressType;
import orm.lol.jdbc.services.AddressTypeService;

import java.util.List;

@RestController
@RequestMapping("/jdbc/address-type")
@Tag(name = "JdbcAddressType")
public class AddressTypeJdbcController {

    private final AddressTypeService addresTypeService;

    public AddressTypeJdbcController(AddressTypeService addresTypeService) {
        this.addresTypeService = addresTypeService;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get all addresstypes")
    public ResponseEntity<List<AddressType>> getAddressTypes(
            @RequestParam Integer times
    ) {

        for(int i = 0; i < times - 1;  i++) {
            addresTypeService.findAll();
        }

        List<AddressType> types = addresTypeService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(types);

    }
}
