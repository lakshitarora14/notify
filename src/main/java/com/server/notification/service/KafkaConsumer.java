package com.server.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.notification.dto.CrmDto;
import com.server.notification.dto.SendDto;
import com.server.notification.repository.FcmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer  {

    @Autowired
    FcmRepo fcmRepo;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "notif1")
    public SendDto data(String NotifData) throws IOException, FirebaseMessagingException{
        ObjectMapper objectMapper = new ObjectMapper();
        SendDto sendDto;;
        sendDto = objectMapper.readValue(NotifData,SendDto.class);
        System.out.println(sendDto.toString());
        pushNotificationService.sendCustom(sendDto);
        System.out.println(sendDto);
        return sendDto;
    }
    @KafkaListener(topics = "mail",groupId = "group_id",containerFactory = "kafkaListenerContainerFactory")
    public CrmDto mail(String maildata) throws IOException, FirebaseMessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CrmDto crmDto = new CrmDto();
        crmDto = objectMapper.readValue(maildata,CrmDto.class);
        System.out.println(crmDto);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(crmDto.getUserEmail());
        msg.setSubject("Reminder");
        msg.setText(crmDto.getContent());
        javaMailSender.send(msg);

        System.out.println(crmDto);
        return crmDto;
    }
}