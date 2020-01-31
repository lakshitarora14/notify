package com.server.notification.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data

@Entity
public class UserFcm {
    @Id
    private String userId;
    private String FacebookApp;
    private String QuoraApp;
    private String QuizApp;
    private String FacebookWeb;
    private String QuoraWeb;

}
