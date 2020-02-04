package com.server.notification.service;

import com.server.notification.entity.UserFcm;

import java.util.List;

public interface UserFcmService {

    List<UserFcm> findAllByUserId(List<String> uidList);
}
