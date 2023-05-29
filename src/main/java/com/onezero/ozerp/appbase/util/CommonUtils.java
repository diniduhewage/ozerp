package com.onezero.ozerp.appbase.util;

import com.onezero.ozerp.appbase.enums.ResultStatus;
import com.onezero.ozerp.appbase.error.exception.InvalidInputExceptionJson;
import com.onezero.ozerp.appbase.dto.response.MessageDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Component
public class CommonUtils {

    private static Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public static boolean isFileExist(String fileNameAndLocation) {
        boolean isFileExist = false;
        try {
            File file = new File(fileNameAndLocation);
            if (file.exists()) {
                isFileExist = true;
            }
        } catch (Exception e) {
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
        messageDTO.setCode(String.valueOf(code));
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

    public static boolean isNullOrWhiteSpace(String string) {

        boolean retVal = false;

        if (string == null) {
            retVal = true;
        } else if (string.isEmpty()) {
            retVal = true;
        } else if (string.trim().equalsIgnoreCase("")) {
            retVal = true;
        } else if (string.length() == 0) {
            retVal = true;
        }

        return retVal;
    }

    public static Pageable createPageRequest(int page, int size, String sortBy) {
        Sort sort = null;
        if (sortBy != null && sortBy.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, "id");
        } else {
            sort = Sort.by(Sort.Direction.ASC, "id");
        }

        return PageRequest.of(page, size, sort);
    }


    public static ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }

    public static ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }

}