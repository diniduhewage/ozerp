package com.onenzero.ozerp.appbase.util;

import com.onenzero.ozerp.appbase.dto.response.MessageDTO;
import com.onenzero.ozerp.appbase.dto.response.ResponseDTO;
import com.onenzero.ozerp.appbase.error.exception.InvalidInputExceptionJson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.io.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;


@Component
public class SaasUtil {
	
	private static Logger log = LoggerFactory.getLogger(SaasUtil.class);
    
	public static boolean isFileExist(String fileNameAndLocation) {
        boolean isFileExist = false;
        try {
            File file = new File(fileNameAndLocation);
            if (file.exists()) {
                isFileExist = true;
            }
        } catch(Exception e) {
            log.warn("File does not exist {}", e.getMessage());
            isFileExist = false;
        }

        return isFileExist;
    }
	
	public static Long timeStampGenerator() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
	
	/**
     * Prepare MessageDTO
     * 
     * @param message
     * @param code
     * @return
     */
    public static MessageDTO prepareMessageDTO(String message, int code) {

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(String. valueOf(code));
        messageDTO.setMessage(message);

        return messageDTO;
    }

    public static String urnGenerator() {
        Calendar cal = Calendar.getInstance();
        String val = String.valueOf(cal.getTimeInMillis());
        val += "-";
        cal.getTime();
        val += UUID.randomUUID().toString().replaceAll("-", "");
        return val;
    }
    public static long now() {
        return DateTime.now(DateTimeZone.UTC).getMillis();
    }
    
    public static long setExecutionToExactTime(String frequencyType) {
        Calendar now = Calendar.getInstance();
        now.setTimeZone(DateTimeZone.UTC.toTimeZone()); 
        if (frequencyType.equals("HOUR")) {
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
        } else if (frequencyType.equals("MINUTE")) {
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
        } else {
            now.set(Calendar.MILLISECOND, 0);
        }

        return now.getTimeInMillis();
    }


    // Validate start date and  end  dates  are in correct range
    public static boolean isValidStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate, ResponseDTO response) {

        if (DateTimeUtil.isValidStartDateAndEndDate(startDate, endDate)) {
            return true;
        }
        response.setHttpCode(response.getHttpStatus() != null ? response.getHttpStatus().toString()
                                                                        : HttpStatus.BAD_REQUEST.toString());
        // 5000- Error message from the backend system
        response.setMessage(new MessageDTO("Start date is greater than end date", "5000"));
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return false;
    }
    
    public static String convertStreamToString(InputStream is) throws InvalidInputExceptionJson {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw (new InvalidInputExceptionJson(e.getMessage()));
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw (new InvalidInputExceptionJson(e.getMessage()));
            }
        }
        return sb.toString();
    }

}