package com.delivery.deliveryapi.controller;

import com.delivery.deliveryapi.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseTestController {

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/test-firebase")
    public String testFirebase() {
        try {
            firebaseService.addTestDocument();
            return "Documento agregado a Firebase correctamente";
        } catch (Exception e) {
            return "Error al agregar documento a Firebase: " + e.getMessage();
        }
    }
}
