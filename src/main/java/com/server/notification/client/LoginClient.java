package com.server.notification.client;

import com.server.notification.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "LoginClient",url = "http://172.16.20.121:8080/fcmController")

public interface LoginClient {
    @PostMapping("/getFcmToken")
    public List<String> getFcmToken(@RequestBody NotificationDTO notificationDTO);



}
