package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.dto.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Service
public class UserDetailsDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsDAO.class);
    private static final String CLASS_NAME = "UserDetailsDAO";
    @Autowired
    CommonDAO commonDAO;

    @Autowired
    Constants constants;

    public ResponseObject validateLoginCredentials(Map<String, Object> loginCredentials) {

        final String METHOD_NAME = "validateLoginCredentials";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("No such email ID found");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            //Create the prepared statement object
            pstmt = connection.prepareStatement("select user_id,user_type,first_name,last_name,email_address,phone_number,enc_password from user_details where email_address= ?");
            pstmt.setString(1, (String) loginCredentials.get("email_id"));

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String dbEncPassword = resultSet.getString("enc_password");

                if (dbEncPassword.equals((String) loginCredentials.get("password"))) {
                    response.setResponseCode(constants.SUCCESS_RESPONSE_CODE);
                    response.setResponseStatus(constants.SUCCESS_RESPONSE_MESSAGE);
                    UserDetails userDetails = new UserDetails();
                    userDetails.setUserId(resultSet.getLong("user_id"));
                    userDetails.setUserType(resultSet.getString("user_type"));
                    userDetails.setFirstName(resultSet.getString("first_name"));
                    userDetails.setLastName(resultSet.getString("last_name"));
                    userDetails.setEmailAddress(resultSet.getString("email_address"));
                    userDetails.setPhoneNumber(resultSet.getLong("phone_number"));

                    response.setResponseData(userDetails);
                    response.setErrorMessage(null);
                } else {
                    response.setResponseCode(constants.FAILURE_RESPONSE_CODE);
                    response.setResponseStatus(constants.FAILURE_RESPONSE_MESSAGE);
                    response.setErrorMessage("Incorrect password");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }
}
