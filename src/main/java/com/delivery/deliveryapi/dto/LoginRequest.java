package com.delivery.deliveryapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    private String fcmToken;  // Token del dispositivo móvil (opcional)
}
