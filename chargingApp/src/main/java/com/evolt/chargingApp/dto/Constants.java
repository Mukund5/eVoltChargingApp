package com.evolt.chargingApp.dto;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {
    public static final String SUCCESS_RESPONSE_MESSAGE = "SUCCESS";
    public static final String FAILURE_RESPONSE_MESSAGE = "FAILURE";
    public static final Integer SUCCESS_RESPONSE_CODE = 200;
    public static final Integer FAILURE_RESPONSE_CODE = 424;

    public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static final Integer ENCRYPTION_KEY_BYTES=256;

    public static final String ENCRYPTION_SALT_KEY = "TestEvoltApplicationSalt";

    public static final String ENCRYPTION_PASSWORD="TestEvoltAppPassword";

    public static enum UserTypes {
        Customer,
        Supplier,
        Admin
    }

    public static enum ChargingStationStatus {
        Active,
        Inactive,
        Maintenance
    }

    public static enum ChargingPortStatus {
        Active,
        Inactive,
        Maintenance
    }

    public static enum ChargingAppointmentStatus {
        Available,
        Reserved,
        Ongoing,
        Cancelled,
        Completed
    }

    public static enum ChargingSpeeds {
        Normal,
        Fast,
        Superfast
    }

    public static final String MOBILE_NUMBER_PATTERN = "\\d{10}";

    public static final String PINCODE_PATTERN = "\\d{6}";

    public static final Integer DEFAULT_NORMAL_CHARGING_RATE=30;

    public static final Integer DEFAULT_FAST_CHARGING_RATE=60;

    public static final Integer DEFAULT_SUPER_FAST_CHARGING_RATE=90;

    public static final String TIMESTAMP_FORMAT="DD-MON-YYYY HH24:MI:SS";

}
