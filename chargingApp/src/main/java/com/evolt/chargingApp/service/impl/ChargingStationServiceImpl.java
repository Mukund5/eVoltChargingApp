package com.evolt.chargingApp.service.impl;

import com.evolt.chargingApp.dao.ChargingStationDAO;
import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.ChargingStationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChargingStationServiceImpl implements ChargingStationService {


    private static final Logger LOGGER = LogManager.getLogger(ChargingStationServiceImpl.class);
    private static final String CLASS_NAME = "ChargingStationServiceImpl";

    @Autowired
    ChargingStationDAO chargingStationDAO;

    @Override
    public ResponseObject getChargingStations(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingStations";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("pincode") ||
                (!(input.get("pincode") instanceof String)
                        || StringUtils.isBlank((String) input.get("pincode")))
                || !isValidPincode((String)input.get("pincode"))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Pincode");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Pincode");
        }
        else
            response=chargingStationDAO.getChargingStations(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    private boolean isValidPincode(String inputPincode)
    {
        Pattern pincodePattern = Pattern.compile(Constants.PINCODE_PATTERN);
        Matcher matcher = pincodePattern.matcher(inputPincode);
        return matcher.matches();
    }

    @Override
    public ResponseObject getChargingPortDetails(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingPortDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("station_id") ||
                (!(input.get("station_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("station_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging station ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging station ID");
        }
        else
            response=chargingStationDAO.getChargingPortDetails(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject getChargingApptDetails(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingApptDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("charging_point_id") ||
                (!(input.get("charging_point_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("charging_point_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging point ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging point ID");
        }
        else if (!input.containsKey("appointment_date") ||
                (!(input.get("appointment_date") instanceof String)
                        || StringUtils.isBlank((String) input.get("appointment_date")))
                || !GenericValidator.isDate((String) input.get("appointment_date"), "dd-MMM-yyyy", true)
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Appointment date");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Appointment date");
        }
        else
            response=chargingStationDAO.getTimeslotDetails(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }


}
