package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//DTO Class for the Charging Port
public class ChargingPort {

    private Long chargingPointId;
    private Long chargingStationId;
    private Long rateNormalCharging;
    private String supportsFastCharging;
    private Long rateFastCharging;
    private String supportsSuperFastCharging;
    private Long rateSuperFastCharging;
    private String status;
}
