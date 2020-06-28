package com.example.tb_moba_simulator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    public static FirebaseAuth mAuth = null;
    public static FirebaseFirestore db = null;
    public static void initFirebaseAuth(){
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }
    public static void initFireStore(){
        if(db == null) {
            db = FirebaseFirestore.getInstance();
        }
    }
}
