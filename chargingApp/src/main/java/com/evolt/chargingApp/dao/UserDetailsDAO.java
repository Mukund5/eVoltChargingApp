package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.dto.ChargingAppointment;
import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.dto.UserDetails;
import com.evolt.chargingApp.service.impl.CommonServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailsDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsDAO.class);
    private static final String CLASS_NAME = "UserDetailsDAO";
    @Autowired
    CommonDAO commonDAO;

    @Autowired
    CommonServiceImpl commonService;


    public ResponseObject validateLoginCredentials(Map<String, Object> loginCredentials) {

        final String METHOD_NAME = "validateLoginCredentials";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
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

                String inputPassword=(String) loginCredentials.get("password");

                //SecretKey key = commonService.getKeyFromPassword(inputPassword,Constants.ENCRYPTION_SALT_KEY);
                //IvParameterSpec ivParameterSpec = commonService.generateIv();

               // String plainTextPassword = commonService.decrypt(Constants.ENCRYPTION_ALGORITHM, dbEncPassword, key, ivParameterSpec);

                //String cipherText = commonService.encrypt(Constants.ENCRYPTION_ALGORITHM, (String) loginCredentials.get("password"), key, ivParameterSpec);
                //LOGGER.info("Encrypted value:"+cipherText);

                if (dbEncPassword.equals(inputPassword)) {
                    response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                    response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
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
                    response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                    response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                    response.setErrorMessage("Incorrect password");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

         */
         finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }


    public ResponseObject getUserDetails(Map<String, Object> loginCredentials) {

        final String METHOD_NAME = "getUserDetails";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("No such User ID found");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            //Create the prepared statement object
            pstmt = connection.prepareStatement("select user_id,user_type,first_name,last_name,email_address,phone_number,driving_license from user_details where user_id= ?");
            pstmt.setString(1, (String) loginCredentials.get("user_id"));

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                UserDetails userDetails = new UserDetails();
                userDetails.setUserId(resultSet.getLong("user_id"));
                userDetails.setUserType(resultSet.getString("user_type"));
                userDetails.setFirstName(resultSet.getString("first_name"));
                userDetails.setLastName(resultSet.getString("last_name"));
                userDetails.setEmailAddress(resultSet.getString("email_address"));
                userDetails.setPhoneNumber(resultSet.getLong("phone_number"));
                userDetails.setDrivingLicense(resultSet.getString("driving_license"));

                response.setResponseData(userDetails);
                response.setErrorMessage(null);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

         */
        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    public ResponseObject getUserBookings(Map<String, Object> loginCredentials) {

        final String METHOD_NAME = "getUserBookings";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("No Bookings found for given User ID");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        List<ChargingAppointment> appointmentList=new ArrayList<>();

        try {
            //Create the prepared statement object
            pstmt = connection.prepareStatement("SELECT appt.APPOINTMENT_ID,appt.CHARGING_PT_ID, appt.APPT_DATE, appt.APPT_START_TIME, appt.DURATION_IN_MINS,\n" +
                    "appt.APPT_END_TIME,   appt.APPT_STATUS,      appt.SELECTED_CHARGING_TYPE, appt.TOTAL_FARE, appt.USER_ID,\n" +
                    "station.address as address FROM EVUSER.charging_appointments appt, evuser.charging_points point,evuser.charging_station station where appt.USER_ID= ? and appt.charging_pt_id=point.charging_pt_id and station.charging_station_id=point.charging_station_id order by appt_date,appt_start_time asc");
            pstmt.setString(1, (String) loginCredentials.get("user_id"));

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                ChargingAppointment appointment = new ChargingAppointment();
                appointment.setAppointmentId(resultSet.getLong("APPOINTMENT_ID"));
                appointment.setChargingPointId(resultSet.getLong("CHARGING_PT_ID"));
                appointment.setAppointmentDate(resultSet.getDate("APPT_DATE"));
                appointment.setApptStartTime(resultSet.getTimestamp("APPT_START_TIME"));
                appointment.setApptEndTime(resultSet.getTimestamp("APPT_END_TIME"));
                appointment.setApptStatus(resultSet.getString("APPT_STATUS"));
                appointment.setSelectedChargingType(resultSet.getString("SELECTED_CHARGING_TYPE"));
                appointment.setTotalFare(resultSet.getLong("TOTAL_FARE"));
                appointment.setAddress(resultSet.getString("address"));

                appointmentList.add(appointment);


                response.setErrorMessage(null);

            }

            response.setResponseData(appointmentList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

         */
        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }
    public ResponseObject signupUser(Map<String, Object> userDetails) {

        final String METHOD_NAME = "signupUser";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        //response.setErrorMessage("No such email ID found");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        try {
            //Checking if any entry already exists for the given email id/phone number/driving license
            //Create the prepared statement object
            pstmt = connection.prepareStatement("select user_id from user_details where email_address= ? or phone_number = ? or driving_license = ? ");

            pstmt.setString(1,(String) userDetails.get("email_id"));
            pstmt.setLong(2, Long.parseLong((String)userDetails.get("phone_number")));
            pstmt.setString(3,(String) userDetails.get("driving_license"));

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            int count=0;
            while(resultSet.next())
                count++;

            LOGGER.info("Result size: "+count);

            if(count>0)
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("Entry exists for given Mail ID/Phone number/Driving License");
            }
            else {

                String inputUserType=(String) userDetails.get("user_type");
                boolean validUserType=false;
                for (Constants.UserTypes value : Constants.UserTypes.values()) {
                    if(!validUserType)
                        validUserType=inputUserType.equalsIgnoreCase(value.name());
                }

                if(!validUserType)
                {
                    response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                    response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                    response.setErrorMessage("Invalid user type");
                }
                else {
                    //Inserting new record for user details

                    //Create the prepared statement object
                    pstmt2 = connection.prepareStatement(" insert into user_details (user_id,user_type,first_name,last_name," +
                            "email_address,phone_number,enc_password,driving_license) values " +
                            "((select max(user_id) from user_details) + 1,?,?,?,?,?,?,?) ");

                    pstmt2.setString(1,(String) userDetails.get("user_type"));
                    pstmt2.setString(2, (String) userDetails.get("first_name"));
                    pstmt2.setString(3,(String) userDetails.get("last_name"));
                    pstmt2.setString(4,(String) userDetails.get("email_id"));
                    pstmt2.setLong(5, Long.parseLong((String)userDetails.get("phone_number")));
                    pstmt2.setString(6,(String) userDetails.get("password"));
                    pstmt2.setString(7,(String) userDetails.get("driving_license"));

                    //Execute the insert query
                    int insertResult = pstmt2.executeUpdate();
                    LOGGER.info("User details inserted count:"+insertResult);
                    if(insertResult>0)
                    {
                        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                        response.setErrorMessage("Successfully inserted new User details");
                    }
                    else {
                        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                        response.setErrorMessage("Error in Inserting new User details");
                    }

                }
            }


        } catch (SQLException e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Error in Inserting new User details");
            throw new RuntimeException(e);
        } finally {
            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }
}
