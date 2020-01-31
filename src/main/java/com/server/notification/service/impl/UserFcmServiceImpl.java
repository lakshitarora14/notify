package com.server.notification.service.impl;

import com.server.notification.entity.UserFcm;
import com.server.notification.repository.FcmRepo;
import com.server.notification.service.UserFcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFcmServiceImpl implements UserFcmService {

    @Autowired
    FcmRepo fcmRepo;

    @Override
    public List<UserFcm> findAllByUserId(List<String> uidList) {
        return fcmRepo.findAllByUserIdIn(uidList);
    }
}
