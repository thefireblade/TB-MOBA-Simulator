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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.BagListAdapter;
import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.MapMobAdapter;
import com.example.tb_moba_simulator.objects.MapPlayerAdapter;

/**
 * The activity that controls the display of the in-game map
 */
public class GameMapActivity extends AppCompatActivity {
    private RecyclerView playerTables, mobTable;
    private Button backGame;
    private TextView mapNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game_map);
        makeSelfPopUp();
        initButtons();
        initTextView();
        initRecyclers();
    }

    /**
     * Initializes all text views
     */
    private void initTextView (){
        mapNameText = findViewById(R.id.game_map_name);
        Character player = GameManager.game.getCurrentPlayer();
        mapNameText.setText(GameManager.game.locatePlayer(player).getName().toUpperCase());
    }

    /**
     * Initializes all Buttons
     */
    private void initButtons(){
        backGame = findViewById(R.id.game_map_back);
        backGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Makes the activity a pop up
     */
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }

    /**
     * Readies all of the tables
     */
    private void initRecyclers(){
        playerTables = findViewById(R.id.game_map_players);
        Character player = GameManager.game.getCurrentPlayer();
        MapPlayerAdapter adapter = new MapPlayerAdapter(GameManager.game.locatePlayer(player).getPlayers());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        playerTables.setLayoutManager(manager);
        playerTables.setAdapter(adapter);
        mobTable = findViewById(R.id.game_map_mobs);;
        MapMobAdapter mobAdapter = new MapMobAdapter(GameManager.game.locatePlayer(player).getEntities());
        RecyclerView.LayoutManager mobManager = new LinearLayoutManager(this);
        mobTable.setLayoutManager(mobManager);
        mobTable.setAdapter(mobAdapter);
    }
}