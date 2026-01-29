package com.delivery.deliveryapi.service;

import com.delivery.deliveryapi.dto.FirebaseDocumentDTO;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    
    // No inicializar en el constructor, usar método lazy
    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
    
    public void addTestDocument() {
        try {
            FirebaseDocumentDTO document = new FirebaseDocumentDTO("1", "Test Name", "Test Description");
            CollectionReference collection = getFirestore().collection("test");
            
            WriteResult result = collection.document(document.getId())
                    .set(document)
                    .get();
            
            System.out.println("Documento agregado correctamente a Firebase en: " + result.getUpdateTime());
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error al agregar documento a Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
