package com.onenzero.ozerp.appbase.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
@Component
public class AccessManagerUtil {
	private static final Logger LOG = LogManager.getLogger(AccessManagerUtil.class);

	
	public static boolean isNullOrWhiteSpace(String input) {
		if (input == null || input.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	

	
	 private static String readAuditFile() throws IOException {
			String[] existDetails = { "" };
		 String fileName = System.getProperty("user.dir") + "/src/main/resources/audit";
		   Stream<String> stream = Files.lines(Paths.get(fileName));
			 stream.forEach(line -> {
				 existDetails[0] += line + "\n";
			 });
	     return existDetails[0];
	   }



}
