package orm.lol.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.lol.entites.person.AddressType;
import orm.lol.repos.AddressTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/address-type")
@Tag(name = "AddressType")
public class AddressTypeController {

    @Autowired
    AddressTypeRepository addressTypeRepo;
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
            addressTypeRepo.findAll();
        }

        List<AddressType> types = addressTypeRepo.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(types);

    }

    @RequestMapping(
            value = "/native",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get all addresstypes")
    public ResponseEntity<List<AddressType>> getAddressTypesNative(
            @RequestParam Integer times
    ) {

        for(int i = 0; i < times - 1;  i++) {
            addressTypeRepo.findAllNative();
        }

        List<AddressType> types = addressTypeRepo.findAllNative();

        return ResponseEntity.status(HttpStatus.OK).body(types);

    }
}
