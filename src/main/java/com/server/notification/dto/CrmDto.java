package com.server.notification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrmDto {
    private String userEmail;
    private String content;

}
