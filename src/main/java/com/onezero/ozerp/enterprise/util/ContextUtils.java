package com.onezero.ozerp.enterprise.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.onezero.ozerp.appbase.error.exception.NotFoundException;


@Service
public class ContextUtils {
    private static final Logger LOG = LogManager.getLogger(ContextUtils.class);

    public String getLoggedInUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        this.verifyContext(context);
        return ((UserDetails) context.getAuthentication().getPrincipal()).getUsername();
    }


    private void verifyContext(SecurityContext context) {

        if (context == null) {
            throw new NotFoundException("Could not retrieve the security context.");
        }
        if (context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            throw new NotFoundException("User is not logged");
        }
        if (context.getAuthentication().getPrincipal() == null) {
            throw new NotFoundException("Could not retrieve principal from the security context.");
        }

    }
}
