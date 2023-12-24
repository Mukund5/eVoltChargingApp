package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
//DTO Class for the Appointment slot
public class AppointmentSlot {
    private String startTime;
    private String endTime;
    private String slotStatus;
}
