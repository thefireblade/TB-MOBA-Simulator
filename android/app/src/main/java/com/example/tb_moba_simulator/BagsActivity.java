/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.BagListAdapter;

/**
 * The activity that is connected to the in-game bag interface
 */
public class BagsActivity extends AppCompatActivity {
    private RecyclerView bagItemsView;
    private Button backGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_bag_pop);
        makeSelfPopUp();
        initRecyclerView();
        initButtons();
    }

    /**
     * Prepares all of the buttons in the bag interface
     */
    private void initButtons(){
        backGame = findViewById(R.id.game_bag_back);
        backGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Prepares the list of Items in the bag
     */
    private void initRecyclerView(){
        bagItemsView = findViewById(R.id.game_bag_recycler);
        BagListAdapter adapter = new BagListAdapter(GameManager.game.getCurrentPlayer().getItems());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        bagItemsView.setLayoutManager(manager);
        bagItemsView.setAdapter(adapter);
    }

    /**
     * Makes the bag interface a popup
     */
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }
}
