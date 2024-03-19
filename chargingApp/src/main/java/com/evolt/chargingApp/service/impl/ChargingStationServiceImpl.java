package com.evolt.chargingApp.service.impl;

import com.evolt.chargingApp.dao.ChargingStationDAO;
import com.evolt.chargingApp.dto.ChargingPort;
import com.evolt.chargingApp.dto.Constants;
import com.evolt.chargingApp.dto.ResponseObject;
import com.evolt.chargingApp.service.ChargingStationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChargingStationServiceImpl implements ChargingStationService {


    private static final Logger LOGGER = LogManager.getLogger(ChargingStationServiceImpl.class);
    private static final String CLASS_NAME = "ChargingStationServiceImpl";

    @Autowired
    ChargingStationDAO chargingStationDAO;

    @Override
    public ResponseObject getChargingStations(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingStations";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("pincode") ||
                (!(input.get("pincode") instanceof String)
                        || StringUtils.isBlank((String) input.get("pincode")))
                || !isValidPincode((String)input.get("pincode"))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Pincode");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Pincode");
        }
        else
            response=chargingStationDAO.getChargingStations(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    private boolean isValidPincode(String inputPincode)
    {
        Pattern pincodePattern = Pattern.compile(Constants.PINCODE_PATTERN);
        Matcher matcher = pincodePattern.matcher(inputPincode);
        return matcher.matches();
    }

    @Override
    public ResponseObject getChargingPortDetails(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingPortDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("station_id") ||
                (!(input.get("station_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("station_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging station ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging station ID");
        }
        else
            response=chargingStationDAO.getChargingPortDetails(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject getChargingApptDetails(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "getChargingApptDetails";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("charging_point_id") ||
                (!(input.get("charging_point_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("charging_point_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging point ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging point ID");
        }
        else if (!input.containsKey("appointment_date") ||
                (!(input.get("appointment_date") instanceof String)
                        || StringUtils.isBlank((String) input.get("appointment_date")))
                || !GenericValidator.isDate((String) input.get("appointment_date"), "dd-MMM-yyyy", true)
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Appointment date");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Appointment date");
        }
        else
            response=chargingStationDAO.getTimeslotDetails(input);

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    @Override
    public ResponseObject bookChargingAppt(Map<String,Object> input)
    {

        long startTime=System.currentTimeMillis();
        final String METHOD_NAME = "bookChargingAppt";
        LOGGER.info("Entered " + CLASS_NAME + ":" + METHOD_NAME);
        ResponseObject response = new ResponseObject();
        response.setResponseCode(Constants.SUCCESS_RESPONSE_CODE);
        response.setResponseStatus(Constants.SUCCESS_RESPONSE_MESSAGE);

        if (!input.containsKey("charging_point_id") ||
                (!(input.get("charging_point_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("charging_point_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging point ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging point ID");
        }
        else if (!input.containsKey("appointment_date") ||
                (!(input.get("appointment_date") instanceof String)
                        || StringUtils.isBlank((String) input.get("appointment_date")))
                || !GenericValidator.isDate((String) input.get("appointment_date"), "dd-MMM-yyyy", true)
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Appointment date");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Appointment date");
        }
        else if (!input.containsKey("user_id") ||
                (!(input.get("user_id") instanceof String)
                        || StringUtils.isBlank((String) input.get("user_id")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty User ID");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty User ID");
        }
        else if (!input.containsKey("appointment_start_time") ||
                (!(input.get("appointment_start_time") instanceof String)
                        || StringUtils.isBlank((String) input.get("appointment_start_time")))
        ) {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Appointment start time");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Appointment start time");
        }
        else if (!input.containsKey("appointment_end_time") ||
                (!(input.get("appointment_end_time") instanceof String)
                        || StringUtils.isBlank((String) input.get("appointment_end_time"))))
        {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Appointment end time");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Appointment end time");
        }
        else if (!input.containsKey("selected_charging_type") ||
                (!(input.get("selected_charging_type") instanceof String)
                        || StringUtils.isBlank((String) input.get("selected_charging_type"))) || !isValidChargingType((String) input.get("selected_charging_type")))
        {
            LOGGER.info(METHOD_NAME + ": Invalid/Empty Charging Speed");
            response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
            response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
            response.setErrorMessage("Invalid/Empty Charging Speed");
        }
        else
        {
            Map<String,Object> dateValidationResponse=validateStartAndEndTime((String) input.get("appointment_start_time"),(String) input.get("appointment_end_time"));
            if((Boolean)dateValidationResponse.get("isValidStartEndTime"))
            {
                StringBuilder startTimeStringBuilder=new StringBuilder();
                startTimeStringBuilder.append((String) input.get("appointment_date"));
                startTimeStringBuilder.append(" ");
                startTimeStringBuilder.append((String) input.get("appointment_start_time"));
                //startTimeStringBuilder.append(":00");
                input.put("formatted_start_time1",startTimeStringBuilder.toString()+":01");
                input.put("formatted_start_time2",startTimeStringBuilder.toString()+":00");

                StringBuilder endTimeStringBuilder=new StringBuilder();
                endTimeStringBuilder.append((String) input.get("appointment_date"));
                endTimeStringBuilder.append(" ");
                //endTimeStringBuilder.append((String) input.get("appointment_end_time"));
                //endTimeStringBuilder.append(":00");

                Calendar calendar2 = Calendar.getInstance();

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                try {

                    Date endTimeDt = dateFormat.parse((String) input.get("appointment_end_time"));
                    endTimeDt.setTime(endTimeDt.getTime()-1000);

                    calendar2.setTime(endTimeDt);
                    calendar2.get(Calendar.HOUR_OF_DAY);
                    calendar2.get(Calendar.MINUTE);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                input.put("formatted_end_time1",endTimeStringBuilder.toString()+calendar2.get(Calendar.HOUR_OF_DAY)+":"+calendar2.get(Calendar.MINUTE)+":"+calendar2.get(Calendar.SECOND));
                input.put("formatted_end_time2",endTimeStringBuilder.toString()+(String) input.get("appointment_end_time")+":00");

                ChargingPort port=chargingStationDAO.getChargingRate(input);
                LOGGER.info("Rate: "+port);

                String selectedChargingType=(String) input.get("selected_charging_type");

                Double ratePerHour=new Double(0);
                Long durationInMins=new Long(0);
                Double taxRate=new Double(5);
                Double totalRateWithoutTax=new Double(0);
                Double totalRateWithTax=new Double(0);

                if(dateValidationResponse.containsKey("durationInMins"))
                {
                    durationInMins=(Long)dateValidationResponse.get("durationInMins");
                    if(selectedChargingType.equalsIgnoreCase(Constants.ChargingSpeeds.Normal.name()))
                    {
                        if(null!=port.getRateNormalCharging() && port.getRateNormalCharging()>0)
                        {
                            ratePerHour=port.getRateNormalCharging().doubleValue();
                        }
                        else
                        {
                            LOGGER.info("Normal charging not supported");
                        }
                    }
                    else if (selectedChargingType.equalsIgnoreCase(Constants.ChargingSpeeds.Fast.name())) {
                        if(null!=port.getRateFastCharging() && port.getRateFastCharging()>0)
                        {
                            ratePerHour=port.getRateFastCharging().doubleValue();
                        }
                        else
                        {
                            LOGGER.info("Fast charging not supported");
                        }
                    }
                    else if (selectedChargingType.equalsIgnoreCase(Constants.ChargingSpeeds.Superfast.name())) {
                        if(null!=port.getRateSuperFastCharging() && port.getRateSuperFastCharging()>0)
                        {
                            ratePerHour=port.getRateSuperFastCharging().doubleValue();
                        }
                        else
                        {
                            LOGGER.info("Superfast charging not supported");
                        }
                    }

                    if(Double.compare(ratePerHour, Double.valueOf(0.0)) > 0 &&
                            durationInMins>0){

                        LOGGER.info("Rate per hour: "+ratePerHour);
                        input.put("duration_in_mins",durationInMins);

                        int numOfHalfHrs=durationInMins.intValue()/30;
                        Double ratePerHalfHr=ratePerHour/2;

                        totalRateWithoutTax=numOfHalfHrs*ratePerHalfHr;
                        totalRateWithTax=totalRateWithoutTax+(totalRateWithoutTax*taxRate/100);

                        input.put("total_fare",totalRateWithTax);

                        response=chargingStationDAO.bookAppointment(input);
                    }
                    else {
                        LOGGER.info("Rate per hour and/or Duration in mins is not greater than zero");
                        response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                        response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                        response.setErrorMessage("Rate per hour and/or Duration in mins is not greater than zero");
                    }
                }
                else {
                    LOGGER.info("durationInMins is not present in dateValidationResponse");
                    response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                    response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                    response.setErrorMessage("durationInMins is not present in dateValidationResponse");
                }
            }
            else
            {
                String validationMsg=(String)dateValidationResponse.get("validationMessage");
                LOGGER.info(METHOD_NAME + ": "+validationMsg);
                response.setResponseCode(Constants.FAILURE_RESPONSE_CODE);
                response.setResponseStatus(Constants.FAILURE_RESPONSE_MESSAGE);
                response.setErrorMessage(validationMsg);
            }
        }

        LOGGER.info(METHOD_NAME+" -Time taken in milliseconds:"+(System.currentTimeMillis()-startTime));
        return response;
    }

    private boolean isValidChargingType(String inputChargingType)
    {
        boolean validChargingType=false;
        for (Constants.ChargingSpeeds value : Constants.ChargingSpeeds.values()) {
            if(!validChargingType)
                validChargingType=inputChargingType.equalsIgnoreCase(value.name());
        }
        return validChargingType;
    }

    private Map<String,Object> validateStartAndEndTime(String startTime,String endTime)
    {
        Map<String,Object> validationResponse=new HashMap<>();
        Boolean isValidStartEndTime=true;
        String validationMessage="Valid Dates";

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date startTimeDt = dateFormat.parse(startTime);
            calendar1.setTime(startTimeDt);

            Date endTimeDt = dateFormat.parse(endTime);
            calendar2.setTime(endTimeDt);

            if(endTimeDt.compareTo(startTimeDt) ==0)
            {
                isValidStartEndTime=false;
                validationMessage="Appointment Start time and Appointment End Time cannot be same";
            }
            else if(endTimeDt.compareTo(startTimeDt) <0)
            {
                isValidStartEndTime=false;
                validationMessage="Appointment End Time cannot be before Appointment Start Time";
            }
            else {
                /*
                Hours should be between 00 and 23
                Minutes should either be 00 or 30
                 */
                final String TIME_SLOT_PATTERN =
                        "([01]?[0-9]|2[0-3]):[0,3][0]";

                Pattern pattern = Pattern.compile(TIME_SLOT_PATTERN);

                Matcher startTimeMatcher = pattern.matcher(startTime);
                Matcher endTimeMatcher = pattern.matcher(endTime);

                if(!startTimeMatcher.matches())
                {
                    isValidStartEndTime=false;
                    validationMessage="Invalid Appointment Start Time";
                }
                else if(!endTimeMatcher.matches())
                {
                    isValidStartEndTime=false;
                    validationMessage="Invalid Appointment End Time";
                }
                else {
                    long diffInMillies = Math.abs(endTimeDt.getTime() - startTimeDt.getTime());
                    long durationInMins = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    validationResponse.put("durationInMins",durationInMins);
                    LOGGER.info("Appointment duration in minutes: "+durationInMins);
                }
            }

        } catch (ParseException e1) {
            isValidStartEndTime=false;
            validationMessage="ParseException in validateStartAndEndTime: "+e1.getMessage();
        }
        catch (Exception e2) {
            isValidStartEndTime=false;
            validationMessage="Exception in validateStartAndEndTime: "+e2.getMessage();
        }

        validationResponse.put("isValidStartEndTime",isValidStartEndTime);
        validationResponse.put("validationMessage",validationMessage);
        return validationResponse;
    }

}
