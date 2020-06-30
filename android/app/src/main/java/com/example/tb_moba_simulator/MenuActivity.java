package com.example.tb_moba_simulator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        currentUser = FirebaseManager.mAuth.getCurrentUser();
        if (currentUser == null) {
            signOut();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initButtons();
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
}
