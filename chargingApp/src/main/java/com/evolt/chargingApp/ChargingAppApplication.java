package com.evolt.chargingApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages={
        "com.evolt.chargingApp"})
public class ChargingAppApplication {

    private static final Logger LOGGER = LogManager.getLogger(ChargingAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ChargingAppApplication.class, args);
        LOGGER.info("Started eVolt Charging SpringBoot application");
    }

}
