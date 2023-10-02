package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//DTO Class for the User Details
public class UserDetails {
    private Long userId;
    private String userType;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Long phoneNumber;
    private String encryptedPassword;
}
