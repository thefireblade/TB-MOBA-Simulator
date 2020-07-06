/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.BagListAdapter;
import com.example.tb_moba_simulator.objects.Game;

public class GameMenuActivity extends AppCompatActivity {
    private Button backGame, saveGame, exitToMenu, settings, loadGame;
    private EditText saveText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game_menu);
        makeSelfPopUp();
        initButtons();
        initEditText();
    }
    private void initEditText (){
        saveText = findViewById(R.id.game_menu_edit_text);
        saveText.setText(GameManager.game.getGameName());
    }
    private void initButtons(){
        backGame = findViewById(R.id.game_menu_back);
        backGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exitToMenu = findViewById(R.id.game_menu_exit);
        exitToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameMenuActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        saveGame = findViewById(R.id.game_menu_save);
        saveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.game.setGameName(saveText.getText().toString());
                GameManager.game.saveGame();
                finish();
            }
        });
        loadGame = findViewById(R.id.game_menu_load);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameMenuActivity.this, SavesActivity.class);
                startActivity(intent);
            }
        });
        settings = findViewById(R.id.game_menu_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(GameMenuActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });
    }
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }
}