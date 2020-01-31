package com.server.notification.controller;

import com.server.notification.dto.CrmDto;
import com.server.notification.service.PushNotificationServiceImpl;
import com.server.notification.service.UserFcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController("/notification")
public class NotificationController {

    @Autowired
    PushNotificationServiceImpl pushNotificationServiceImpl;

    @Autowired
    UserFcmService userFcmService;





//    @PostMapping("/send")
//    public String sendEmail(@RequestBody CrmDto crmDto){
//
//        pushNotificationServiceImpl.sendEmail(crmDto);
//        return "Email Sent";
//    }


}
