package com.server.notification.service;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
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
    private static FcmRepo userFcmService;

    @Autowired
    private static LoginClient loginClient;




    private static Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
    private FCMService fcmService;
    public PushNotificationServiceImpl(FCMService fcmService){this.fcmService = fcmService;}

    public static void sendCustom(SendDto sendDto) {
        List<String> uidList = sendDto.getUidList();


    String channel = sendDto.getPlatform();
//        List<String> registrationTokens = new ArrayList<>();
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUserId(sendDto.getUidList());
        notificationDTO.setChannel(sendDto.getPlatform());
        List<String> registrationTokens = loginClient.getFcmToken(notificationDTO);
        registrationTokens.add("fHthvKtivAw:APA91bHY5tMWQ9Zg1MZB2JJ5C42q_OcM3j2ikYLlqHt9xT_QyL40QVday3rkQOJLUpLr3OdAiTvhQfp0TeEcDuBe5xcgK1wubghi3cqT7ryBCQA0BG5JrfwLk13_DwCbnaATHsngOS09");
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
