package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
//DTO Class for the Charging Appointment
public class ChargingAppointment {
    private Long appointmentId;
    private Long chargingPointId;
    private Long userId;
    private Date appointmentDate;
    private Timestamp apptStartTime;
    private Timestamp apptEndTime;
    private String apptStatus;
    private Long durationInMins;
    private String selectedChargingType;
    private Long totalFare;
    private String address;
}
