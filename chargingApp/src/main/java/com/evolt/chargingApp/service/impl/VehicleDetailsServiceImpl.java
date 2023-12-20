package com.evolt.chargingApp.service.impl;

import com.evolt.chargingApp.dao.VehicleDetailsDAO;
import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.VehicleDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VehicleDetailsServiceImpl implements VehicleDetailsService {


    private static final Logger LOGGER = LogManager.getLogger(VehicleDetailsServiceImpl.class);
    private static final String CLASS_NAME = "VehicleDetailsServiceImpl";

    @Autowired
    VehicleDetailsDAO vehicleDetailsDAO;

    @Override
    public ResponseObject getBrandNames(Map<String,Object> input)
    {
        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getBrandNames";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        response=vehicleDetailsDAO.getBrandNamesList(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject getModelNames(Map<String, Object> input) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getModelNames";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("brand_name") ||
                (!(input.get("brand_name") instanceof String)
                        || StringUtils.isBlank((String) input.get("brand_name")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Brand name");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Brand name");
        }
        else
            response=vehicleDetailsDAO.getModelNamesList(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject registerVehicle(Map<String, Object> input) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "registerVehicle";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("user_id") ||
                (!(input.get("user_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("user_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty User ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty User ID");
        }
        else  if (!input.containsKey("vehicle_id") ||
                (!(input.get("vehicle_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("vehicle_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Vehicle ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Vehicle ID");
        }
        else  if (!input.containsKey("registration_number") ||
                (!(input.get("registration_number") instanceof String)
                        || StringUtils.isBlank((String) input.get("registration_number")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Registration number");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Registration number");
        }
        else
            response=vehicleDetailsDAO.registerNewVehicle(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject listVehicles(Map<String, Object> input) {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "listVehicles";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("user_id") ||
                (!(input.get("user_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("user_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty User ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty User ID");
        }
        else
            response=vehicleDetailsDAO.listVehicles(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

}
