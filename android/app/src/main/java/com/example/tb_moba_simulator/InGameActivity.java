package com.example.tb_moba_simulator;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.Game;

import java.util.Stack;

public class InGameActivity extends AppCompatActivity {
    private ImageView charIcon;
    private TextView player_info, player_stats;
    private TextView turnsText;
    private RecyclerView decisionTable;
    private Button bagButton, mapButton, logButton, menuButton;
    private Stack<String> recyclerStack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        GameManager.game.loadMap();
        initImageView();
        recyclerStack = new Stack<>();
        initPlayerInfo();
    }
    private void initImageView(){
        charIcon = findViewById(R.id.in_game_character);
        switch (GameManager.selectedType) {
            case archer: charIcon.setImageResource(R.drawable.archer); break;
            case sword: charIcon.setImageResource(R.drawable.sword); break;
            case shield: charIcon.setImageResource(R.drawable.shield); break;
            default: charIcon.setImageResource(R.drawable.sword);
        }
    }
    private void initPlayerInfo(){
        player_info = findViewById(R.id.in_game_player);
        updatePlayerInfo();
    }
    private void updatePlayerInfo(){
        Character player = GameManager.game.getCurrentPlayer();
        int health  = player.getCurrHP(), attack = player.getCurrAtk(), energy = player.getCurrEnergy(), exp = player.getExp();
        String text = "";
        text += "Health:\t" + health + "/" + player.getMaxHealth();
        text += "\nEnergy:\t" + energy + "/" + player.getMaxEnergy();
        text += "\nAttack:\t" + attack;
        text += "\nLevel:\t" + player.getLevel();
        text += "\nEXP:\t" + exp;
        player_info.setText(text);
    }
    private void initPlayerStats(){
        player_stats = findViewById(R.id.in_game_stats);
    }
    private void updatePlayerStats(){
        Character player = GameManager.game.getCurrentPlayer();
        String text = "";
        text += "Wealth:\t" + player.getWealth();
        text += ""
    }
}
