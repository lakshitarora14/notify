package com.server.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.notification.dto.CrmDto;
import com.server.notification.dto.SendDto;
import org.springframework.messaging.MessagingException;

public interface PushNotificationService {

   void sendCustom(SendDto sendDto) throws FirebaseMessagingException;

}
