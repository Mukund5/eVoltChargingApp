package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.dto.*;
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
public class ChargingStationDAO {

    private static final Logger LOGGER = LogManager.getLogger(ChargingStationDAO.class);
    private static final String CLASS_NAME = "ChargingStationDAO";
    @Autowired
    CommonDAO commonDAO;

    public ResponseObject getChargingStations(Map<String, Object> input) {

        final String METHOD_NAME = "getChargingStations";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Charging stations");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("select charging_station_id,pincode,address,district,state,geo_location,status,station_name from charging_station where pincode= ? and status = ? ");


            String pincodeStr=(String)input.get("pincode");
            Long pincode=Long.parseLong(pincodeStr);

            pstmt.setLong(1,pincode);
            pstmt.setString(2,Constants.ChargingStationStatus.Active.name());

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            List<ChargingStation> chargingStationsList=new ArrayList<>();
            while (resultSet.next()) {
                ChargingStation station=new ChargingStation();

                station.setChargingStationId(resultSet.getLong("charging_station_id"));
                station.setPincode(resultSet.getLong("pincode"));
                station.setAddress(resultSet.getString("address"));
                station.setDistrict(resultSet.getString("district"));
                station.setState(resultSet.getString("state"));
                station.setGeo_location(resultSet.getString("geo_location"));
                station.setStatus(resultSet.getString("status"));
                station.setStationName(resultSet.getString("station_name"));
                chargingStationsList.add(station);
            }

            response.setResponseData(chargingStationsList);
            if(!chargingStationsList.isEmpty())
            {
                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                response.setErrorMessage("Successfully retrieved Charging station details");
            }
            else
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("No Charging stations found for the given pincode");
            }


        } catch (Exception e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Error in getting Charging station details");
            //throw new RuntimeException(e);
        }

        finally {

            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    public ResponseObject getChargingPortDetails(Map<String, Object> input) {

        final String METHOD_NAME = "getChargingPortDetails";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Charging Port details");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("select status,charging_pt_id,charging_station_id,rate_normal_charging,supports_fast_charging," +
                    "rate_fast_charging,supports_super_fast_charging,rate_super_fast_charging from charging_points where status= ? and charging_station_id= ? ");


            String stationIdStr=(String)input.get("station_id");
            Long stationId=Long.parseLong(stationIdStr);

            pstmt.setString(1,Constants.ChargingPortStatus.Active.name());
            pstmt.setLong(2,stationId);

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            List<ChargingPort> chargingPortsList=new ArrayList<>();
            while (resultSet.next()) {
                ChargingPort port=new ChargingPort();

                port.setChargingPointId(resultSet.getLong("charging_pt_id"));
                port.setChargingStationId(resultSet.getLong("charging_station_id"));
                port.setStatus(resultSet.getString("status"));
                port.setRateNormalCharging(resultSet.getLong("rate_normal_charging"));
                port.setRateFastCharging(resultSet.getLong("rate_fast_charging"));
                port.setSupportsFastCharging(resultSet.getString("supports_fast_charging"));
                port.setRateSuperFastCharging(resultSet.getLong("rate_super_fast_charging"));
                port.setSupportsSuperFastCharging(resultSet.getString("supports_super_fast_charging"));
                chargingPortsList.add(port);
            }

            response.setResponseData(chargingPortsList);
            if(!chargingPortsList.isEmpty())
            {
                response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
                response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
                response.setErrorMessage("Successfully retrieved Charging port details");
            }
            else
            {
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage("No Charging ports found for the given Charging station");
            }


        } catch (Exception e) {
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Error in getting Charging port details");
            //throw new RuntimeException(e);
        }

        finally {
            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

}
