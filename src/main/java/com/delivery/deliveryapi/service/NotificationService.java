package com.delivery.deliveryapi.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendNotification(String fcmToken, String title, String body) {
        try {
            Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .build();
            
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación enviada exitosamente: " + response);
            
        } catch (Exception e) {
            System.err.println("Error al enviar notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void sendNotificationWithData(String fcmToken, String title, String body, 
                                         String dataKey, String dataValue) {
        try {
            Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .putData(dataKey, dataValue)
                .build();
            
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación con datos enviada: " + response);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
