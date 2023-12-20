package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.dto.Vehicle;
import com.evolt.chargingApp.dto.VehicleDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleDetailsDAO {

    private static final Logger LOGGER = LogManager.getLogger(VehicleDetailsDAO.class);
    private static final String CLASS_NAME = "VehicleDetailsDAO";
    @Autowired
    CommonDAO commonDAO;

    public ResponseObject getBrandNamesList(Map<String, Object> input) {

        final String METHOD_NAME = "getBrandNamesList";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Brand names");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            StringBuilder pstmtSb=new StringBuilder("select distinct brand_name from vehicle_master ");


                if (input.containsKey("vehicle_type") &&
                        input.get("vehicle_type") instanceof String
                                && StringUtils.isNotBlank((String) input.get("vehicle_type")))
                {
                    pstmtSb.append(getQueryClause((String) input.get("vehicle_type")));
                }

            //Create the prepared statement object
            pstmt = connection.prepareStatement(pstmtSb.toString());

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            List<String> brandsList=new ArrayList<>();
            while (resultSet.next()) {
                brandsList.add(resultSet.getString("brand_name"));
            }


            response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
            response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
            response.setErrorMessage("Successfully retrieved Brand names");
            response.setResponseData(brandsList);

        } catch (SQLException e) {
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Brand names");
            throw new RuntimeException(e);
        }

         finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    private String getQueryClause(String vehicleType)
    {
        String resultQueryClause= StringUtils.EMPTY;

        if(vehicleType.equalsIgnoreCase("bike"))
            resultQueryClause=" where seating_capacity=2 ";
        else if(vehicleType.equalsIgnoreCase("car"))
            resultQueryClause=" where seating_capacity>2 ";

        return resultQueryClause;
    }


    public ResponseObject getModelNamesList(Map<String, Object> input) {

        final String METHOD_NAME = "getModelNamesList";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Model names");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            StringBuilder pstmtSb=new StringBuilder("select vehicle_id,brand_name,model_name,model_number,battery_capacity,driving_range,seating_capacity,supports_fast_charging,supports_super_fast_charging from vehicle_master ");

            String selectedBrand=(String) input.get("brand_name");

            String appendClause=StringUtils.EMPTY;

            if (input.containsKey("vehicle_type") &&
                    input.get("vehicle_type") instanceof String
                    && StringUtils.isNotBlank((String) input.get("vehicle_type")))
            {
                appendClause=getQueryClause((String) input.get("vehicle_type"));
                pstmtSb.append(appendClause);
            }

            if(StringUtils.isNotBlank(appendClause))
                pstmtSb.append(" and brand_name= ? ");
            else
                pstmtSb.append(" where brand_name= ? ");

            //Create the prepared statement object
            pstmt = connection.prepareStatement(pstmtSb.toString());
            pstmt.setString(1,selectedBrand);

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            List<Vehicle> vehiclesList=new ArrayList<>();
            while (resultSet.next()) {
                Vehicle vehicle=new Vehicle();
                vehicle.setVehicleId(resultSet.getLong("vehicle_id"));
                vehicle.setBrandName(resultSet.getString("brand_name"));
                vehicle.setModelName(resultSet.getString("model_name"));
                vehicle.setModelNumber(resultSet.getString("model_number"));
                vehicle.setBatteryCapacity(resultSet.getLong("battery_capacity"));
                vehicle.setDrivingRange(resultSet.getLong("driving_range"));
                vehicle.setSeatingCapacity(resultSet.getLong("seating_capacity"));
                vehicle.setSupportsFastCharging(resultSet.getString("supports_fast_charging"));
                vehicle.setSupportsSuperFastCharging(resultSet.getString("supports_super_fast_charging"));
                vehiclesList.add(vehicle);
            }

            response.setResponseData(vehiclesList);
            if(!vehiclesList.isEmpty())
            {
                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                response.setErrorMessage("Successfully retrieved Model names");
            }
            else
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("No models found for the given brand name and vehicle type combination");
            }


        } catch (SQLException e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Error in getting Model names");
            throw new RuntimeException(e);
        }

        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    public ResponseObject registerNewVehicle(Map<String, Object> input) {

        final String METHOD_NAME = "registerNewVehicle";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in registering new vehicle");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null,pstmt2=null;
        try {


            //Create the prepared statement object
            pstmt2 = connection.prepareStatement(" select vehicle_detail_id from vehicle_details where registration_number= ? ");
            pstmt2.setString(1,(String) input.get("registration_number"));

            //Execute the select query
            ResultSet resultSet2 = pstmt2.executeQuery();
            int countPresent=0;
            while (resultSet2.next())
                countPresent++;

            if(countPresent>0)
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("The given registration number is already present");

            }
            else {
                pstmt =connection.prepareStatement(" insert into vehicle_details(vehicle_detail_id,vehicle_id,user_id,registration_number," +
                        "created_date,last_modified_date) values ((select max(vehicle_detail_id) from vehicle_details) + 1,?,?,?,systimestamp,systimestamp) ");

                String vehicleIdStr=(String) input.get("vehicle_id");
                Long vehicleId=Long.parseLong(vehicleIdStr);

                pstmt.setLong(1,vehicleId);

                String userIdStr=(String) input.get("user_id");
                Long userId=Long.parseLong(userIdStr);

                pstmt.setLong(2,userId);

                pstmt.setString(3,(String) input.get("registration_number"));


                //Execute the insert query
                int insertResult = pstmt.executeUpdate();
                LOGGER.info("Vehicle details inserted count:"+insertResult);
                if(insertResult>0)
                {
                    response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                    response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                    response.setErrorMessage("Successfully registered new Vehicle details");
                }
                else {
                    response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                    response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                    response.setErrorMessage("Error in registering new vehicle");
                }
            }

        } catch (Exception e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Exception in registering new vehicle:"+e.getMessage());
            //throw new RuntimeException(e);
        }

        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    public ResponseObject listVehicles(Map<String, Object> input) {

        final String METHOD_NAME = "listVehicles";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in fetching vehicle details");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt2=null;
        try {


            //Create the prepared statement object
            pstmt2 = connection.prepareStatement(" select det.vehicle_detail_id,det.registration_number,mas.vehicle_id," +
                    "mas.brand_name, mas.model_name, mas.model_number , mas.battery_capacity,mas.supports_fast_charging," +
                    "mas.supports_super_fast_charging,mas.seating_capacity,mas.driving_range from vehicle_master mas,vehicle_details det " +
                    "where det.user_id= ? and mas.vehicle_id=det.vehicle_id ");
            pstmt2.setString(1,(String) input.get("user_id"));

            //Execute the select query
            ResultSet resultSet2 = pstmt2.executeQuery();

            List<VehicleDetails> vehicles=new ArrayList<>();
            while (resultSet2.next())
            {
                VehicleDetails vehicleDetails=new VehicleDetails();
                vehicleDetails.setRegistrationNumber(resultSet2.getString("registration_number"));
                vehicleDetails.setVehicleDetailId(resultSet2.getLong("vehicle_detail_id"));

                Vehicle vehicle=new Vehicle();
                vehicle.setVehicleId(resultSet2.getLong("vehicle_id"));
                vehicle.setBrandName(resultSet2.getString("brand_name"));
                vehicle.setModelName(resultSet2.getString("model_name"));
                vehicle.setModelNumber(resultSet2.getString("model_number"));
                vehicle.setBatteryCapacity(resultSet2.getLong("battery_capacity"));
                vehicle.setSupportsFastCharging(resultSet2.getString("supports_fast_charging"));
                vehicle.setSupportsSuperFastCharging(resultSet2.getString("supports_super_fast_charging"));
                vehicle.setSeatingCapacity(resultSet2.getLong("seating_capacity"));
                vehicle.setDrivingRange(resultSet2.getLong("driving_range"));

                vehicleDetails.setVehicle(vehicle);

                vehicles.add(vehicleDetails);
            }

            response.setResponseData(vehicles);

            if(vehicles.isEmpty())
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("No vehicles found for the given user");

            }
            else {
                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                response.setErrorMessage("Successfully retrieved Vehicle details for this user");
            }

        } catch (Exception e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Exception in getting vehicle details for given user:"+e.getMessage());
            //throw new RuntimeException(e);
        }

        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

}
