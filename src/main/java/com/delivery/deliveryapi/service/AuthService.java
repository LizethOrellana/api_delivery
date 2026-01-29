package com.delivery.deliveryapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.deliveryapi.dto.AuthResponse;
import com.delivery.deliveryapi.dto.LoginRequest;
import com.delivery.deliveryapi.dto.RegisterRequest;
import com.delivery.deliveryapi.entity.User;
import com.delivery.deliveryapi.repository.UserRepository;
import com.delivery.deliveryapi.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;
    private final NotificationService notificationService;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwt,
            NotificationService notificationService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
        this.notificationService = notificationService;
    }

    // Método de registro de usuario
    public AuthResponse register(RegisterRequest req) {
        // Verificar si el nombre de usuario ya existe
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Verificar que la contraseña no esté vacía
        if (req.getPassword() == null || req.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña no puede estar vacía");
        }

        // Crear un nuevo usuario a partir del DTO
        User user = new User();
        user.setUsername(req.getUsername());

        // Encriptar la contraseña antes de guardarla en la base de datos
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));  // Guardamos el hash de la contraseña
        user.setRole(req.getRole());
        user.setFcmToken(req.getFcmToken());

        // Guardar el usuario en la base de datos
        user = userRepo.save(user);

        // Generar el token JWT para el usuario registrado
        String token = jwt.generateToken(user.getUsername(), user.getRole().name(), user.getId());

        // Enviar notificación de bienvenida si se tiene un FCM token
        if (user.getFcmToken() != null) {
            notificationService.sendNotification(
                    user.getFcmToken(),
                    "¡Bienvenido! 🎉",
                    "Tu cuenta ha sido creada exitosamente, " + user.getUsername()
            );
        }

        // Devolver la respuesta con el token, ID, nombre de usuario y rol
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getRole());
    }

    // Método de login
    public AuthResponse login(LoginRequest req) {
        // Autenticar al usuario usando las credenciales proporcionadas
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        // Buscar el usuario en la base de datos
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el FCM token si se proporciona en el request
        if (req.getFcmToken() != null && !req.getFcmToken().equals(user.getFcmToken())) {
            user.setFcmToken(req.getFcmToken());
            userRepo.save(user);
        }

        // Generar el token JWT para el inicio de sesión
        String token = jwt.generateToken(user.getUsername(), user.getRole().name(), user.getId());

        // Enviar notificación de inicio de sesión si se tiene un FCM token
        if (user.getFcmToken() != null) {
            notificationService.sendNotification(
                    user.getFcmToken(),
                    "Inicio de sesión exitoso ✅",
                    "Bienvenido de nuevo, " + user.getUsername()
            );
        }

        // Devolver la respuesta con el token, ID, nombre de usuario y rol
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getRole());
    }
}
