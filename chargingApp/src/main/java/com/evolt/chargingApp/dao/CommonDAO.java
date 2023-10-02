package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.service.CommonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
This class has the common functionality related to Database Access
such as creating a connection, closing a connection etc
 */
@Repository
public class CommonDAO {

    private static final Logger LOGGER = LogManager.getLogger(CommonDAO.class);
    private static final String CLASS_NAME = "CommonDAO";

    @Autowired
    CommonService commonService;

    public Connection createEvuserConnection() {
        final String METHOD_NAME = "createEvuserConnection";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        Connection evuserConnection;
        try {
            //Load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Properties properties = commonService.getDatabaseProperties();
            String databaseIp = properties.getProperty("database.ip");
            String databasePort = properties.getProperty("database.port");
            String dbServiceName = properties.getProperty("database.service.name");
            String evuserDbUserName = properties.getProperty("evuser.database.user.name");
            String evuserDbPassword = properties.getProperty("evuser.database.password");

            //"jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
            //Create  the connection object
            String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + databaseIp + ")(PORT=" + databasePort + ")))(CONNECT_DATA=(SERVICE_NAME=" + dbServiceName + ")))";

            //evuserConnection = DriverManager.getConnection(
            //"jdbc:oracle:thin:@" + databaseIp + ":" + databasePort + ":" + dbServiceName, evuserDbUserName, evuserDbPassword);
            evuserConnection = DriverManager.getConnection(dbURL, evuserDbUserName, evuserDbPassword);

            LOGGER.info(CLASS_NAME + ":" + METHOD_NAME + "- Created Connection successfully");

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Exception occurred in " + CLASS_NAME + ":" + METHOD_NAME + " :" + e.getMessage());
            return null;
        }
        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return evuserConnection;
    }

    public void closeEvuserConnection(Connection connection) {
        final String METHOD_NAME = "closeEvuserConnection";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred in " + CLASS_NAME + ":" + METHOD_NAME + " :" + e.getMessage());
        }
        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
    }

}
