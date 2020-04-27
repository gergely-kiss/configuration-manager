package uk.gergely.kiss.configurationprovider.controllers;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;
import uk.gergely.kiss.configurationprovider.controllers.services.APIOperationsService;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.ADMIN)
public class AdminController {

    private final APIOperationsService apiOperationsService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public AdminController(APIOperationsService apiOperationsService) {
        this.apiOperationsService = apiOperationsService;
    }

    @PostMapping(value = ControllerConstants.SLASH_ID,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity management(@PathVariable String id, @RequestBody (required = false) JSONObject request){
        return new ResponseEntity( apiOperationsService.operate(id, request), HttpStatus.OK);
    }
}
