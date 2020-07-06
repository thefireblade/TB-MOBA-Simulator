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

import com.example.tb_moba_simulator.objects.AttackDefenseAdapter;
import com.example.tb_moba_simulator.objects.AttackPlayerAdapter;
import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.Defense;
import com.example.tb_moba_simulator.objects.Game;
import com.example.tb_moba_simulator.objects.GameMoveAdapter;
import com.example.tb_moba_simulator.objects.Item;
import com.example.tb_moba_simulator.objects.Location;
import com.example.tb_moba_simulator.objects.Mob;
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
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InGameActivity.this, GameMapActivity.class);
                startActivity(intent);
            }
        });
        logButton = findViewById(R.id.in_game_log);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InGameActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
        menuButton = findViewById(R.id.in_game_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InGameActivity.this, GameMenuActivity.class);
                startActivity(intent);
            }
        });
        backButton = findViewById(R.id.in_game_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popRecyclerStack();
            }
        });
    }
    public void popRecyclerStack(){
        if(recyclerStack.size() > 0) {
            String popped = recyclerStack.pop();
        }
        if(recyclerStack.size() == 0) {
            hideRecycler();
            showGameButtons();
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
        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Character player = GameManager.game.getCurrentPlayer();
                player.rest();
                updatePlayerInfo();
                updatePlayerStats();
                GameManager.game.simulateTurn();
            }
        });
        moveButton = findViewById(R.id.in_game_move);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerStack.push(MOVE);
                hideGameButtons();
                showMoveLocations();
            }
        });
        attackButton = findViewById(R.id.in_game_attack);
        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAttackOptions();
                hideGameButtons();
                recyclerStack.push(ATTACK);
            }
        });
        spellButton = findViewById(R.id.in_game_spells);
        hideGameButtons();
        showGameButtons();
        hideRecycler();
    }
    public void hideGameButtons(){
        shopButton.setVisibility(View.GONE);
        restButton.setVisibility(View.GONE);
        moveButton.setVisibility(View.GONE);
        attackButton.setVisibility(View.GONE);
        spellButton.setVisibility(View.GONE);
    }
    public void showGameButtons(){
        if(GameManager.game.isPlayerAtBase()) {
            shopButton.setVisibility(View.VISIBLE);
            restButton.setVisibility(View.VISIBLE);
        }
        moveButton.setVisibility(View.VISIBLE);
        Character player = GameManager.game.getCurrentPlayer();
        Location playerLocation = GameManager.game.locatePlayer(player);
//        if(GameManager.game.locationHasEnemyPlayer(playerLocation, player.getTeam())) { // Future implementation will allow for concurrency
            attackButton.setVisibility(View.VISIBLE);
//        } else {
//            attackButton.setVisibility(View.GONE);
//        }
        spellButton.setVisibility(View.GONE);
//        spellButton.setVisibility(View.VISIBLE); // Spells are deprecated because I don't have enough time to implement it
    }
    public void hideRecycler(){
        decisionTable.setVisibility(View.GONE);
    }
    private void showShop(){
        ShopListAdapter adapter = new ShopListAdapter(GameManager.game.getShop(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        decisionTable.setLayoutManager(manager);
        decisionTable.setAdapter(adapter);
        decisionTable.setVisibility(View.VISIBLE);
    }
    public void showMoveLocations(){
        Character player = GameManager.game.getCurrentPlayer();
        GameMoveAdapter adapter = new GameMoveAdapter(GameManager.game.locatePlayer(player).getConnects(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        decisionTable.setLayoutManager(manager);
        decisionTable.setAdapter(adapter);
        decisionTable.setVisibility(View.VISIBLE);
    }

    public void showAttackOptions(){
        Character player = GameManager.game.getCurrentPlayer();
        Location playerLocation = GameManager.game.locatePlayer(player);
        if(GameManager.game.locationHasEnemyPlayer(playerLocation, player.getTeam())){
            ArrayList<Character> enemies = new ArrayList<>();
            ArrayList<Character> players = playerLocation.getPlayers();
            for(Character p: players) {
                if(!p.getTeam().equals(player.getTeam())) {
                    enemies.add(p);
                }
            }
            AttackPlayerAdapter adapter = new AttackPlayerAdapter(enemies, this);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            decisionTable.setLayoutManager(manager);
            decisionTable.setAdapter(adapter);
            decisionTable.setVisibility(View.VISIBLE);
        } else {
            ArrayList<Mob> defenses = playerLocation.getEntities();
            ArrayList<Mob> attackable = new ArrayList<>();
            for(Mob mob: defenses) {
                if(!mob.getTeam().equals(player.getTeam())) {
                    attackable.add((Mob) mob);
                }
            }
            AttackDefenseAdapter adapter = new AttackDefenseAdapter(attackable, this);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            decisionTable.setLayoutManager(manager);
            decisionTable.setAdapter(adapter);
            decisionTable.setVisibility(View.VISIBLE);
        }
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
