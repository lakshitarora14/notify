package com.server.notification.service;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.*;
import com.server.notification.client.LoginClient;
import com.server.notification.dto.NotificationDTO;
import com.server.notification.dto.SendDto;
import com.server.notification.entity.UserFcm;
import com.server.notification.firebase.FCMService;
import com.server.notification.repository.FcmRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PushNotificationServiceImpl implements PushNotificationService{

    @Autowired
    private FcmRepo userFcmService;

    @Autowired
    private  LoginClient loginClient;




    private static Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
    private FCMService fcmService;
    public PushNotificationServiceImpl(FCMService fcmService){this.fcmService = fcmService;}

    public void sendCustom(SendDto sendDto) throws FirebaseMessagingException {
        List<String> uidList = sendDto.getUidList();
        String channel = sendDto.getPlatform();

        System.out.println("Inside SendCuston" + uidList.size());
        System.out.println("Inside SendCuston"+channel);
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUserId(sendDto.getUidList());
        notificationDTO.setChannel(sendDto.getPlatform());
        List<String> registrationTokens = loginClient.getFcmToken(notificationDTO);
        System.out.println("=================No of registration tokens==============" + registrationTokens.size());

//        List<String> registrationTokens = new ArrayList<>();
//        registrationTokens.add("etNB0C3y1go:APA91bHR7HWmw0QuWDl7r0-NCXNfXWl4-TV6WNEAK_LP4iXiluEUUVjHdONxpV5zxRK3p3cFWRJVNnc-zez9Wryl7FYVrnUijy7NqYo82FuIuEEyhgFC5rjmJGZ5kRJUmh0JWw1OlQei");

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(new Notification(sendDto.getTitle(), sendDto.getMessage()))
                .putData("lakshit",sendDto.getJsonData())
                .build();
//        BatchResponse response = null;
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

        ApiFutures.addCallback(FirebaseMessaging
                .getInstance()
                .sendMulticastAsync(message), new ApiFutureCallback<BatchResponse>() {
            @Override
            public void onFailure(Throwable throwable) {

                logger.error("Error occured while sending the notification");
            }

            @Override
            public void onSuccess(BatchResponse batchResponse) {

                logger.info("Sent notification to all devices");
            }
        }, MoreExecutors.newDirectExecutorService());

        System.out.println(response.getSuccessCount() + " messages were sent successfully");

    }


//
}
