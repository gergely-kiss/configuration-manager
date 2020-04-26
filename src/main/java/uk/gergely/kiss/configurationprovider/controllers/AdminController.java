package uk.gergely.kiss.configurationprovider.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.ADMIN)
public class AdminController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @PostMapping(value = ControllerConstants.SLASH_ID,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity management(@PathVariable String id){

        return new ResponseEntity(  HttpStatus.OK);
    }
}
