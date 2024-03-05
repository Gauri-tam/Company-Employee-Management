package com.Management.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    //"recipient":"shaivi@weetechsolution.com"
    //"msgBody":"Hello , Dear, this is message for security check . "
    //"subject":"CodersArea : Confirmation"
}
