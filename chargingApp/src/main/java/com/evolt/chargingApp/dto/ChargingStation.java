package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//DTO Class for the Charging Station
public class ChargingStation {
    private Long chargingStationId;
    private Long pincode;
    private String stationName;
    private String address;
    private String district;
    private String state;
    private String geo_location;
    private String status;
}
