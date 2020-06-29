package com.example.tb_moba_simulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuActivity extends AppCompatActivity {
    private Button newGame, settings, loadSave, signOut;
    private FirebaseUser currentUser;
    private static String TAG = "MenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentUser = FirebaseManager.mAuth.getCurrentUser();
        if (currentUser == null) {
            signOut();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    private void initButtons() {
        newGame = findViewById(R.id.menu_start);
        settings = findViewById(R.id.menu_settings);
        loadSave = findViewById(R.id.menu_load);
        loadSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseManager.db.collection("saves")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
            }
        });
        signOut = findViewById(R.id.menu_logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    private void signOut(){
        if (currentUser != null) {
            FirebaseManager.mAuth.signOut();
        }
        Intent loadMenu = new Intent(MenuActivity.this, SplashActivity.class);
        startActivity(loadMenu);
    }
}
