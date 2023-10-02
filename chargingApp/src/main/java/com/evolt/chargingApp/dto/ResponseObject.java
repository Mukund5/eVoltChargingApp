package com.evolt.chargingApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//This DTO is passed as response to any service call
public class ResponseObject {
    private String responseStatus;
    private Integer responseCode;
    private String errorMessage;
    private Object responseData;
}
