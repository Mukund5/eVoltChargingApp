package com.evolt.chargingApp.rest;

import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.impl.VehicleDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/evolt/vehicle/")
public class VehicleDetailsController {

    private static final Logger LOGGER = LogManager.getLogger(VehicleDetailsController.class);
    private static final String CLASS_NAME = "VehicleDetailsController";

    @Autowired
    VehicleDetailsServiceImpl vehicleDetailsService;

    @PostMapping("/getBrandsList")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject getBrandsList(@RequestBody Map<String, Object> inputObject) {
        final String METHOD_NAME = "getBrandsList";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + inputObject);
        return vehicleDetailsService.getBrandNames(inputObject);
    }

    @PostMapping("/getModelsList")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject getModelsList(@RequestBody Map<String, Object> input) {
        final String METHOD_NAME = "getModelsList";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + input);
        return vehicleDetailsService.getModelNames(input);
    }

    @PostMapping("/registerNewVehicle")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject registerNewVehicle(@RequestBody Map<String, Object> input) {
        final String METHOD_NAME = "registerNewVehicle";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + input);
        return vehicleDetailsService.registerVehicle(input);
    }

    @PostMapping("/listRegisteredVehicles")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseObject listRegisteredVehicles(@RequestBody Map<String, Object> input) {
        final String METHOD_NAME = "listRegisteredVehicles";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME + "with inputs: " + input);
        return vehicleDetailsService.listVehicles(input);
    }
}
