package com.example.tb_moba_simulator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class MenuActivity extends AppCompatActivity {
    private Button newGame, settings, loadSave, signOut;
    private FirebaseUser currentUser;
    private static String TAG = "MenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSettings();
        currentUser = FirebaseManager.mAuth.getCurrentUser();
        if (currentUser == null) {
            signOut();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initButtons();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(101420);
    }
    private void initButtons() {
        newGame = findViewById(R.id.menu_start);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameModes = new Intent(MenuActivity.this, GameModeSelectActivity.class);
                startActivity(gameModes);
            }
        });
        settings = findViewById(R.id.menu_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });
        loadSave = findViewById(R.id.menu_load);
        loadSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saves = new Intent(MenuActivity.this, SavesActivity.class);
                startActivity(saves);
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
        loadMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loadMenu);
    }
    public static Map<String, Object> JSONStringToMap(String jsonString) {
        HashMap<String, Object> yourHashMap = new Gson().fromJson(jsonString.toString(), HashMap.class);
        return yourHashMap;
    }

    public String readFile(String path) throws IOException {
        String filePath = new File("").getAbsolutePath();
        filePath.concat(path);
        Scanner scanner = new Scanner( new File(filePath) );
        String text = scanner.useDelimiter("\\A").next();
        System.out.println(text);
        scanner.close();
        return text;
    }
    @Override
    public void onBackPressed() { }
    public Context getSelf() {
        return this;
    }
    public void loadSettings() {
        if(GameManager.settings) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean darkMode = preferences.getBoolean("darkMode", false);
            if(darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } else {
            String email = FirebaseManager.mAuth.getCurrentUser().getEmail();
            FirebaseManager.db.collection("user").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getSelf());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("darkMode", document.getBoolean("darkMode"));
                            editor.putBoolean("notify", document.getBoolean("notify"));
                            GameManager.settings = true;
                            editor.apply();
                            if(document.getBoolean("darkMode")) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            } else {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
                        } else {
                            System.out.println("Could not find the settings doc for the user");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
}
