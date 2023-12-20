package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//DTO Class for the Vehicle
public class Vehicle {
    private Long vehicleId;
    private String brandName;
    private String modelName;
    private String modelNumber;
    private Long batteryCapacity;
    private String supportsFastCharging;
    private String supportsSuperFastCharging;
    private Long seatingCapacity;
    private Long drivingRange;

}
