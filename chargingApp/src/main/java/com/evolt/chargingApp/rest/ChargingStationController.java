package com.evolt.chargingApp.rest;

import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.impl.ChargingStationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Map;


@RestController
@CrossOrigin
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

    @PostMapping("/getChargingApptDetails")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject getChargingApptDetails(@RequestBody Map<String, Object> inputObject) {
        final String METHOD_NAME = "getChargingApptDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputObject);
        return chargingStationServiceImpl.getChargingApptDetails(inputObject);
    }

    @PostMapping("/bookChargingAppt")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject bookChargingAppointment(@RequestBody Map<String, Object> inputObject) {
        final String METHOD_NAME = "bookChargingAppointment";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputObject);
        return chargingStationServiceImpl.bookChargingAppt(inputObject);
    }


}
