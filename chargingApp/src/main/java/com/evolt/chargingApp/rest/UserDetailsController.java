package com.evolt.chargingApp.rest;

import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.impl.UserDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/evolt/user/")
public class UserDetailsController {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsController.class);
    private static final String CLASS_NAME = "UserDetailsController";
    @Autowired
    Constants constants;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/validateLogin")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject validateUserLoginDetails(@RequestBody Map<String, Object> inputUserCredentials) {
        final String METHOD_NAME = "validateUserLoginDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputUserCredentials);
        return userDetailsService.validateLogin(inputUserCredentials);
    }

}
