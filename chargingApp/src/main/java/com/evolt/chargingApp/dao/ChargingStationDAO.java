package com.evolt.chargingApp.dao;

import com.evolt.chargingApp.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public ResponseObject getTimeslotDetails(Map<String, Object> input) {

        final String METHOD_NAME = "getTimeslotDetails";
        LOGGER.info("Entering " + CLASS_NAME + ":" + METHOD_NAME);

        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
        response.setErrorMessage("Error in getting Charging appointment details");

        Connection connection = commonDAO.createEvuserConnection();

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("select appointment_id,charging_pt_id,user_id,appt_date,appt_start_time,appt_end_time," +
                    "appt_status,duration_in_mins,selected_charging_type,total_fare from charging_appointments where " +
                    "charging_pt_id= ? and appt_date=TO_DATE( ? ) and appt_status in ( ? ,? ) ");


            String chargingPointIdStr=(String)input.get("charging_point_id");
            Long chargingPointId=Long.parseLong(chargingPointIdStr);

            pstmt.setLong(1,chargingPointId);
            pstmt.setString(2,(String)input.get("appointment_date"));
            pstmt.setString(3,Constants.ChargingAppointmentStatus.Reserved.name());
            pstmt.setString(4,Constants.ChargingAppointmentStatus.Ongoing.name());

            //Execute the select query
            ResultSet resultSet = pstmt.executeQuery();
            List<ChargingAppointment> chargingAppointments=new ArrayList<>();
            while (resultSet.next()) {
                ChargingAppointment appointment=new ChargingAppointment();

                appointment.setAppointmentId(resultSet.getLong("appointment_id"));
                appointment.setChargingPointId(resultSet.getLong("charging_pt_id"));
                appointment.setUserId(resultSet.getLong("user_id"));
                appointment.setAppointmentDate(resultSet.getDate("appt_date"));
                appointment.setApptStartTime(resultSet.getTimestamp("appt_start_time"));
                appointment.setApptEndTime(resultSet.getTimestamp("appt_end_time"));
                appointment.setApptStatus(resultSet.getString("appt_status"));
                appointment.setDurationInMins(resultSet.getLong("duration_in_mins"));
                appointment.setSelectedChargingType(resultSet.getString("selected_charging_type"));
                appointment.setTotalFare(resultSet.getLong("total_fare"));

                chargingAppointments.add(appointment);
            }

            List<AppointmentSlot> apptSlots=formTimeSlotsForDay((String)input.get("appointment_date"),chargingAppointments);
            response.setResponseData(apptSlots);
            response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
            response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);
            response.setErrorMessage("Successfully retrieved Charging appointment details");

        } catch (Exception e) {
            LOGGER.error("Error in getting Charging appointment details:"+e.getMessage());
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Error in getting Charging appointment details");
            //throw new RuntimeException(e);
        }

        finally {
            commonDAO.closeEvuserConnection(connection);
        }

        LOGGER.info("Exiting from " + CLASS_NAME + ":" + METHOD_NAME);
        return response;
    }

    private List<AppointmentSlot> formTimeSlotsForDay(String dateString,List<ChargingAppointment> chargingAppointments)
    {

        List<AppointmentSlot> timeSlots=new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

        try {

            java.util.Date dateObject = sdf.parse(dateString);
            Timestamp todayTimeStamp = new Timestamp(dateObject.getTime());
            //LOGGER.info("Today's Timestamp:"+todayTimeStamp);

            Long oneSecond=new Long(1000);
            Long halfHourInMillis=new Long(30*60*oneSecond);
            Timestamp tomorrowTimeStamp=new Timestamp(todayTimeStamp.getTime()+(halfHourInMillis*48));
            //LOGGER.info("Tomorrow's Timestamp:" + tomorrowTimeStamp);

            Timestamp currentTimeStamp=todayTimeStamp;
            int slotNumber=1;
            while(currentTimeStamp.before(tomorrowTimeStamp))
            {
                AppointmentSlot apptSlot=new AppointmentSlot();
                //LOGGER.info("Slot number:"+slotNumber);

                Timestamp startTimeStamp=new Timestamp(currentTimeStamp.getTime());
                //LOGGER.info("Start time:"+startTimeStamp);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(startTimeStamp.getTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                String slotStartTime = sdf2.format(calendar.getTime());
                //LOGGER.info("Formatted start time in HH:mm:"+slotStartTime);
                apptSlot.setStartTime(slotStartTime);

                currentTimeStamp.setTime(currentTimeStamp.getTime()+halfHourInMillis);
                Timestamp endTimeStamp=new Timestamp(currentTimeStamp.getTime());
                //LOGGER.info("End time:"+endTimeStamp);
                calendar.setTimeInMillis(endTimeStamp.getTime());
                String slotEndTime = sdf2.format(calendar.getTime());
                //LOGGER.info("Formatted end time in HH:mm:"+slotEndTime);
                apptSlot.setEndTime(slotEndTime);

                apptSlot.setSlotStatus(Constants.ChargingAppointmentStatus.Available.name());

                for(ChargingAppointment appt:chargingAppointments)
                {
                    if((startTimeStamp.after(appt.getApptStartTime()) || startTimeStamp.equals(appt.getApptStartTime()))
                            && (endTimeStamp.before(appt.getApptEndTime()) || endTimeStamp.equals(appt.getApptEndTime())))
                    {
                       /* LOGGER.info("startTimeStamp:"+startTimeStamp);
                        LOGGER.info("appt.getApptStartTime():"+appt.getApptStartTime());
                        LOGGER.info("endTimeStamp:"+endTimeStamp);
                        LOGGER.info("appt.getApptEndTime():"+appt.getApptEndTime());
                        */
                        apptSlot.setSlotStatus(appt.getApptStatus());
                    }
                }
               // LOGGER.info("Appt:"+apptSlot);
                timeSlots.add(apptSlot);
                slotNumber++;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return timeSlots;
    }

}
