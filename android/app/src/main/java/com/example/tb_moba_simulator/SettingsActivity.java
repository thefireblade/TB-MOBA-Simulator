package com.example.tb_moba_simulator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.Game;
import com.example.tb_moba_simulator.objects.SaveListAdapter;
import com.example.tb_moba_simulator.objects.SaveObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private Button backToMenu;
    private Switch darkMode;
    private CheckBox pushNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initButtons();
        initControls();
        initPreferences();
    }
    private void initButtons(){
        backToMenu = findViewById(R.id.settings_back);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initControls(){
        darkMode = findViewById(R.id.settings_theme_mode);
        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSettings();
            }
        });
        pushNotifications = findViewById(R.id.settings_notification);
        pushNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSettings();
            }
        });
    }
    private void initPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean enabled = preferences.getBoolean("darkMode", false);
        darkMode.setChecked(enabled);
        if(enabled) {
            darkMode.setText("Dark Mode");
        } else {
            darkMode.setText("Light Mode");
        }
        boolean notify = preferences.getBoolean("notify", true);
        pushNotifications.setChecked(notify);
    }
    private void setSettings(){
        final String userEmail = FirebaseManager.mAuth.getCurrentUser().getEmail();
        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put("darkMode", darkMode.isChecked());
        settings.put("notify", pushNotifications.isChecked());
        FirebaseManager.db.collection("users").document(userEmail)
                .set(settings)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Settings for " + userEmail + " has been successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("darkMode", darkMode.isChecked());
        editor.putBoolean("notify", pushNotifications.isChecked());
        GameManager.settings = true;
        editor.apply();
        if(darkMode.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
