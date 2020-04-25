package uk.gergely.kiss.configurationprovider.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uk.gergely.kiss.configurationprovider.controllers.model.ConfigurationRequest;
import uk.gergely.kiss.configurationprovider.security.vo.UserDetailsVO;

@RestController
@RequestMapping("/cm/admin")
public class AdminController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @PostMapping(value = "/{operationId}",
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity management(@PathVariable String operationId, @AuthenticationPrincipal UserDetailsVO userDetailsVO, ConfigurationRequest configurationRequest){
        LOGGER.info(userDetailsVO.getUsername());
        configurationRequest.getConfigurations().values().forEach((value)->LOGGER.warn("{}", value));
        return new ResponseEntity(operationId, HttpStatus.OK);
    }
}
