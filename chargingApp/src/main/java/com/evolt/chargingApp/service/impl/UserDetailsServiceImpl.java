package com.evolt.chargingApp.service.impl;

import com.evolt.chargingApp.dao.UserDetailsDAO;
import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.UserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

/*
This method validates the provided Login credentials and checks if they match
the values present in the User details database table
 */

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
    private static final String CLASS_NAME = "UserDetailsServiceImpl";
    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Autowired
    Constants constants;

    @Override
    public ResponseObject validateLogin(Map<String, Object> loginCredentials) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "validateLogin";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(constants.SUCCESS_RESPONSE_MESSAGE);

        if (!loginCredentials.containsKey("email_id") ||
                (!(loginCredentials.get("email_id") instanceof String)
                        || StringUtils.isBlank((String) loginCredentials.get("email_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid Email ID");
            response.setResponseCode(constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid Email ID");

        } else if (!loginCredentials.containsKey("password") ||
                (!(loginCredentials.get("password") instanceof String)
                        || StringUtils.isBlank((String) loginCredentials.get("password")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid Password");
            response.setResponseCode(constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid Password");
        } else
            response=userDetailsDAO.validateLoginCredentials(loginCredentials);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));

        return response;
    }
}
