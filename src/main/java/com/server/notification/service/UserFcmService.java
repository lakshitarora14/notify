package com.server.notification.service;

import com.server.notification.entity.UserFcm;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.List;
import java.util.Optional;

public interface UserFcmService {

    List<UserFcm> findAllByUserId(List<String> uidList);
}
