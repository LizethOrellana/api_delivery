package com.delivery.deliveryapi.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Component;

@Component
public class FirebaseSecurityConfig {

    public FirebaseAuth getFirebaseAuth() throws FirebaseAuthException {
        return FirebaseAuth.getInstance();
    }
}
