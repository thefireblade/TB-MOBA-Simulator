package com.example.tb_moba_simulator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class InGameActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "TBMOBA_12213";
    private ImageView charIcon;
    private TextView player_info, player_stats;
    private TextView prompt;
    private RecyclerView decisionTable;
    private Button backButton;
    private Button bagButton, mapButton, logButton, menuButton;
    private Button shopButton, restButton, moveButton, attackButton, spellButton;
    private Stack<String> recyclerStack;
    private Switch sortSwitch;
    private final String SHOP = "What to buy?", MOVE = "Where would you like to go?",
            ATTACK = "Select a target to attack", SPELL = "SPELL", DEFAULT_PROMPT = "What will you do?";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        initImageView();
        initRecyclerView();
        initTextView();
        recyclerStack = new Stack<>();
        initPlayerInfo();
        initPlayerStats();
        initButtons();
        initGameButtons();
        createNotificationChannel();
        startNotification();
    }
    private void initTextView(){
        prompt = findViewById(R.id.in_game_prompt);
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
        prompt.setText(DEFAULT_PROMPT);
    }
    private Context getSelf() {
        return this;
    }
    private void initGameButtons(){
        shopButton = findViewById(R.id.in_game_shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerStack.push(SHOP);
                prompt.setText(SHOP);
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
                GameManager.checkWin(getSelf());
            }
        });
        moveButton = findViewById(R.id.in_game_move);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerStack.push(MOVE);
                prompt.setText(MOVE);
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
                prompt.setText(ATTACK);
            }
        });
        spellButton = findViewById(R.id.in_game_spells);
        sortSwitch = findViewById(R.id.in_game_sort_switch);
        Collections.sort(GameManager.game.getShop(), new Comparator<Item>() {
            @Override
            public int compare(Item u1, Item u2) {
                return ((Integer) u2.getCost()).compareTo((Integer) u1.getCost());
            }
        });
        sortSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortSwitch.isChecked()) {
                    Collections.sort(GameManager.game.getShop(), new Comparator<Item>() {
                        @Override
                        public int compare(Item u1, Item u2) {
                            return ((Integer) u1.getCost()).compareTo((Integer) u2.getCost());
                        }
                    });
                    sortSwitch.setText("Sort Cost Asc");
                    showShop();
                } else {
                    Collections.sort(GameManager.game.getShop(), new Comparator<Item>() {
                        @Override
                        public int compare(Item u1, Item u2) {
                            return ((Integer) u2.getCost()).compareTo((Integer) u1.getCost());
                        }
                    });
                    sortSwitch.setText("Sort Cost Desc");
                    showShop();
                }
            }
        });
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
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void startNotification(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notify = preferences.getBoolean("notify", false);
        if(notify){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.splashlogo_tbmoba)
                    .setContentTitle("TBMOBA")
                    .setContentText("Ongoing Game")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setOngoing(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // notificationId is a unique int for each notification that you must define
            int notificationId = 101420;
            notificationManager.notify(notificationId, builder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(101420);
        }
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
        sortSwitch.setVisibility(View.GONE);
    }
    private void showShop(){
        sortSwitch.setVisibility(View.VISIBLE);
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
        startNotification();
        super.onResume();
    }
}
