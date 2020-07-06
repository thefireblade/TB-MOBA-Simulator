/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Class that contains Firebase's constants.
 */
public class FirebaseManager {
    public static FirebaseAuth mAuth = null;
    public static FirebaseFirestore db = null;
    public static boolean dbInitialized = false, authInitialized = false;

    /**
     * Initializes the firebase authentication
     */
    public static void initFirebaseAuth(){
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            authInitialized = true;
        }
    }

    /**
     * Initializes the firestore database
     */
    public static void initFireStore(){
        if(db == null) {
            db = FirebaseFirestore.getInstance();
            dbInitialized = true;
        }
    }
}
