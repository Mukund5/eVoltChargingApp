package com.evolt.chargingApp.service;

import com.evolt.chargingApp.dto.ResponseObject;

import java.util.Map;

public interface UserDetailsService {
    public ResponseObject validateLogin(Map<String,Object> loginCredentials);
}
