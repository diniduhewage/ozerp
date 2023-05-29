package com.onezero.ozerp.appbase.event;

import com.onezero.ozerp.appbase.dto.UserDTO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserDTO userDTO;
    private String applicationUrl;

    public RegistrationCompleteEvent(UserDTO userDTO, String applicationUrl) {
        super(userDTO);
        this.userDTO = userDTO;
        this.applicationUrl = applicationUrl;
    }

}