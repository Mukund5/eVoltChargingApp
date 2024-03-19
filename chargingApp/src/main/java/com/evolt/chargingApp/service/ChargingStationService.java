package com.evolt.chargingApp.service;

import com.evolt.chargingApp.dto.ResponseObject;

import java.util.Map;

public interface ChargingStationService {
    public ResponseObject getChargingStations(Map<String,Object> input);

    public ResponseObject getChargingPortDetails(Map<String,Object> input);

    public ResponseObject getChargingApptDetails(Map<String,Object> input);

    public ResponseObject bookChargingAppt(Map<String,Object> input);

}
