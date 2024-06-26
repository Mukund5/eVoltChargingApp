package com.evolt.chargingApp.service;

import com.evolt.chargingApp.dto.ResponseObject;

import java.util.Map;

public interface UserDetailsService {
    public ResponseObject validateLogin(Map<String,Object> loginCredentials);
    public ResponseObject signupNewUser(Map<String,Object> userDetails);

    public ResponseObject getUserDetails(Map<String,Object> loginCredentials);

    public ResponseObject getUserBookings(Map<String,Object> loginCredentials);
}
