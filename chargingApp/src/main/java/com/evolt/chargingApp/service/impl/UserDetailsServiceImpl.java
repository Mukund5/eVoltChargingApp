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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @Override
    public ResponseObject validateLogin(Map<String, Object> loginCredentials) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "validateLogin";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!loginCredentials.containsKey("email_id") ||
                (!(loginCredentials.get("email_id") instanceof String)
                        || StringUtils.isBlank((String) loginCredentials.get("email_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid Email ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid Email ID");

        } else if (!loginCredentials.containsKey("password") ||
                (!(loginCredentials.get("password") instanceof String)
                        || StringUtils.isBlank((String) loginCredentials.get("password")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid Password");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid Password");
        } else
            response=userDetailsDAO.validateLoginCredentials(loginCredentials);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject signupNewUser(Map<String, Object> inputUserDetails) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "signupNewUser";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!inputUserDetails.containsKey("email_id") ||
                (!(inputUserDetails.get("email_id") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("email_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Email ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Email ID");

        } else if (!inputUserDetails.containsKey("password") ||
                (!(inputUserDetails.get("password") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("password")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Password");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Password");
        } else if (!inputUserDetails.containsKey("first_name") ||
                (!(inputUserDetails.get("first_name") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("first_name")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty First name");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty First name");
        } else if (!inputUserDetails.containsKey("last_name") ||
                (!(inputUserDetails.get("last_name") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("last_name")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Last name");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Last name");
        }
        else if (!inputUserDetails.containsKey("driving_license") ||
                (!(inputUserDetails.get("driving_license") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("driving_license")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Driving License");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Driving License");
        } else if (!inputUserDetails.containsKey("phone_number") ||
                (!(inputUserDetails.get("phone_number") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("phone_number")))
                || !isValidPhoneNumber((String)inputUserDetails.get("phone_number"))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Phone number");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Phone number");
        }
        else if (!inputUserDetails.containsKey("user_type") ||
                (!(inputUserDetails.get("user_type") instanceof String)
                        || StringUtils.isBlank((String) inputUserDetails.get("user_type")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty User type");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty User type");
        }
        else
            response=userDetailsDAO.signupUser(inputUserDetails);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    private boolean isValidPhoneNumber(String inputPhoneNumber)
    {
        Pattern phoneNumberPattern = Pattern.compile(Constants.MOBILE_NUMBER_PATTERN);
        Matcher matcher = phoneNumberPattern.matcher(inputPhoneNumber);
        return matcher.matches();
    }
}
