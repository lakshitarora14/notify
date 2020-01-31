package com.server.notification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@ToString
public class SendDto {
    private String title;
    private String message;
    private String platform;
    private List<String> uidList;
//    private Map<String,String> data;
}