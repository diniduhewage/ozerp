package com.onezero.ozerp.enterprise.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class ApiKeyGeneratorUtil {

    public String generateApiKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String segment = RandomStringUtils.randomAlphanumeric(4).toLowerCase();
            sb.append(segment);
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
}
