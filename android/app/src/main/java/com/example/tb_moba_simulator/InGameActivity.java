package com.example.tb_moba_simulator;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.Game;
import com.example.tb_moba_simulator.objects.Item;
import com.example.tb_moba_simulator.objects.SaveListAdapter;
import com.example.tb_moba_simulator.objects.ShopListAdapter;

import java.util.ArrayList;
import java.util.Stack;

public class InGameActivity extends AppCompatActivity {
    private ImageView charIcon;
    private TextView player_info, player_stats;
    private TextView turnsText;
    private RecyclerView decisionTable;
    private Button backButton;
    private Button bagButton, mapButton, logButton, menuButton;
    private Button shopButton, restButton, moveButton, attackButton, spellButton;
    private Stack<String> recyclerStack;
    private final String SHOP = "SHOP", MOVE = "MOVE", ATTACK = "ATTACK", SPELL = "SPELL";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        initImageView();
        initRecyclerView();
        recyclerStack = new Stack<>();
        initPlayerInfo();
        initPlayerStats();
        initButtons();
        initGameButtons();
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
    public void updatePlayerInfo(){
        Character player = GameManager.game.getCurrentPlayer();
        int health  = player.getCurrHP(), attack = player.getCurrAtk(), energy = player.getCurrEnergy(), exp = player.getExp();
        String text = "";
        text += "Health:\t" + health + "/" + player.getMaxHealth();
        text += "\nEnergy:\t" + energy + "/" + player.getMaxEnergy();
        text += "\nAttack:\t" + attack;
        text += "\nLevel:\t\t" + player.getLevel();
        text += "\nEXP:\t\t\t\t" + exp;
        player_info.setText(text);
    }
    public void initPlayerStats(){
        player_stats = findViewById(R.id.in_game_stats);
        updatePlayerStats();
    }
    public void updatePlayerStats(){
        Character player = GameManager.game.getCurrentPlayer();
        String text = "";
        text += "Wealth:\t" + player.getWealth();
        text += "\nKills:\t\t" + player.getKills();
        text += "\nDeaths:\t" + player.getDeaths();
        text += "\nTurn #:\t" + GameManager.game.getTurnCount();
        player_stats.setText(text);
    }
    private void initRecyclerView(){
        decisionTable = findViewById(R.id.in_game_recycler);
    }
    private void initButtons(){
        bagButton = findViewById(R.id.in_game_bag);
        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InGameActivity.this, BagsActivity.class);
                startActivity(intent);
            }
        });
        mapButton = findViewById(R.id.in_game_map);
        logButton = findViewById(R.id.in_game_log);
        menuButton = findViewById(R.id.in_game_menu);
        backButton = findViewById(R.id.in_game_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popRecyclerStack();
            }
        });
    }
    private void popRecyclerStack(){
        if(recyclerStack.size() > 0) {
            String popped = recyclerStack.pop();
            if(recyclerStack.size() == 0) {
                hideRecycler();
                showGameButtons();
            } else {
//                switch (recyclerStack.peek()){
//
//                }
            }
        }
    }
    private void initGameButtons(){
        shopButton = findViewById(R.id.in_game_shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerStack.push(SHOP);
                hideGameButtons();
                showShop();
            }
        });
        restButton = findViewById(R.id.in_game_rest);
        moveButton = findViewById(R.id.in_game_move);
        attackButton = findViewById(R.id.in_game_attack);
        spellButton = findViewById(R.id.in_game_spells);
        hideGameButtons();
        showGameButtons();
        hideRecycler();
    }
    private void hideGameButtons(){
        shopButton.setVisibility(View.GONE);
        restButton.setVisibility(View.GONE);
        moveButton.setVisibility(View.GONE);
        attackButton.setVisibility(View.GONE);
        spellButton.setVisibility(View.GONE);
    }
    private void showGameButtons(){
        if(GameManager.game.isPlayerAtBase()) {
            shopButton.setVisibility(View.VISIBLE);
            restButton.setVisibility(View.VISIBLE);
        }
        moveButton.setVisibility(View.VISIBLE);
        attackButton.setVisibility(View.VISIBLE);
        spellButton.setVisibility(View.VISIBLE);
    }
    private void hideRecycler(){
        decisionTable.setVisibility(View.GONE);
    }
    private void showShop(){
        ShopListAdapter adapter = new ShopListAdapter(GameManager.game.getShop(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        decisionTable.setLayoutManager(manager);
        decisionTable.setAdapter(adapter);
        decisionTable.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed() { }
    @Override
    public void onResume(){
        updatePlayerStats();
        updatePlayerInfo();
        super.onResume();
    }
}
