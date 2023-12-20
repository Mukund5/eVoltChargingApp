package com.evolt.chargingApp.service;

import com.evolt.chargingApp.dto.ResponseObject;

import java.util.Map;

public interface VehicleDetailsService {
    public ResponseObject getBrandNames(Map<String,Object> input);

    public ResponseObject getModelNames(Map<String,Object> inputObj);

    public ResponseObject registerVehicle(Map<String,Object> inputObj);

    public ResponseObject listVehicles(Map<String,Object> inputObj);

}
