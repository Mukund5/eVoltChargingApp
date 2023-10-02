package com.evolt.chargingApp.service.impl;

import com.evolt.chargingApp.service.CommonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Logger LOGGER = LogManager.getLogger(CommonServiceImpl.class);
    private static final String CLASS_NAME = "CommonServiceImpl";

    @Override
    public Properties getDatabaseProperties() {
        final String METHOD_NAME = "getDatabaseProperties";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);

        Properties properties = new Properties();

        //Start of Loading DB configuration properties from property file
        try (InputStream input = CommonServiceImpl.class.getClassLoader().getResourceAsStream("dbConfig.properties")) {

            if (input == null) {
                LOGGER.info("Unable to find the Database configuration properties file");
                return null;
            }

            //load a properties file from class path, inside static method
            properties.load(input);

        } catch (IOException ex) {
            LOGGER.error("Exception occurred in " + CLASS_NAME + ":" + METHOD_NAME + " :" + ex.getMessage());
            return null;
        }

        //End of Loading DB configuration properties from property file
        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return properties;
    }
}
