package com.onezero.ozerp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

}
