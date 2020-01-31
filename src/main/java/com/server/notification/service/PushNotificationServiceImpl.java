package com.server.notification.service;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.server.notification.dto.SendDto;
import com.server.notification.entity.UserFcm;
import com.server.notification.firebase.FCMService;
import com.server.notification.repository.FcmRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PushNotificationServiceImpl implements PushNotificationService{

    @Autowired
    private static FcmRepo userFcmService;





    private static Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
    private FCMService fcmService;
    public PushNotificationServiceImpl(FCMService fcmService){this.fcmService = fcmService;}

    public static void sendCustom(SendDto sendDto) {
        List<String> uidList = sendDto.getUidList();



        List<String> registrationTokens = Arrays.asList();
        if(sendDto.getPlatform() == "FacebookApp")
        {
            List<UserFcm> userFcms=userFcmService.findAllByUserIdIn(uidList);
            for(int i=0;i<userFcms.size();i++)
            {
                registrationTokens.add(userFcms.get(i).getFacebookApp());
            }
        }
        else if(sendDto.getPlatform() == "FacebookWeb")
        {
            List<UserFcm> userFcms=userFcmService.findAllByUserIdIn(uidList);
            for(int i=0;i<userFcms.size();i++)
            {
                registrationTokens.add(userFcms.get(i).getFacebookWeb());
            }
        }
        else if(sendDto.getPlatform() == "QuoraApp")
        {
            List<UserFcm> userFcms=userFcmService.findAllByUserIdIn(uidList);
            for(int i=0;i<userFcms.size();i++)
            {
                registrationTokens.add(userFcms.get(i).getQuoraApp());
            }
        }
        else if(sendDto.getPlatform() == "QuoraWeb")
        {
            List<UserFcm> userFcms=userFcmService.findAllByUserIdIn(uidList);
            for(int i=0;i<userFcms.size();i++)
            {
                registrationTokens.add(userFcms.get(i).getQuoraWeb());
            }
        }
        else if(sendDto.getPlatform() == "QuizApp")
        {
            List<UserFcm> userFcms=userFcmService.findAllByUserIdIn(uidList);
            for(int i=0;i<userFcms.size();i++)
            {
                registrationTokens.add(userFcms.get(i).getQuizApp());
            }
        }

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(new Notification(sendDto.getTitle(), sendDto.getMessage()))
                .build();
        BatchResponse response = null;
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

    }


//
}
