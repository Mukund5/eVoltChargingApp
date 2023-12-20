package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//DTO Class for the Vehicle Details
public class VehicleDetails {
    private Long vehicleDetailId;
    private String registrationNumber;
    private Vehicle vehicle;
}
