package com.evolt.chargingApp.rest;

import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.impl.ChargingStationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Map;


@RestController
@RequestMapping("/evolt/charging/")
public class ChargingStationController {

    private static final Logger LOGGER = LogManager.getLogger(ChargingStationController.class);
    private static final String CLASS_NAME = "ChargingStationController";

    @Autowired
    ChargingStationServiceImpl chargingStationServiceImpl;

    @PostMapping("/getChargingStations")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject getChargingStations(@RequestBody Map<String, Object> inputObject) {
        final String METHOD_NAME = "getChargingStations";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputObject);
        return chargingStationServiceImpl.getChargingStations(inputObject);
    }

    @PostMapping("/getChargingPortsDetails")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject getChargingPortsDetails(@RequestBody Map<String, Object> inputObject) {
        final String METHOD_NAME = "getChargingPortsDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputObject);
        return chargingStationServiceImpl.getChargingPortDetails(inputObject);
    }


}
