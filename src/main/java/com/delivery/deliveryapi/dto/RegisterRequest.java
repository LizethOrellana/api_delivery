package com.delivery.deliveryapi.dto;

import com.delivery.deliveryapi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 40)
    private String username;  // Nombre de usuario

    @NotBlank
    @Size(min = 6)
    private String password;  // Contraseña en texto plano

    @NotNull
    private Role role;  // Rol del usuario

    private String fcmToken;  // Token del dispositivo móvil
}
