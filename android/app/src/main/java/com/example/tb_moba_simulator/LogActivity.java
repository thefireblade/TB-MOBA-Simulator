/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.BagListAdapter;

public class LogActivity extends AppCompatActivity {
    private TextView logText;
    private Button backGame;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_log_pop);
        makeSelfPopUp();
        initButtons();
        initTextView();
    }
    private void initButtons(){
        backGame = findViewById(R.id.game_log_back);
        backGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void initTextView(){
        logText = findViewById(R.id.game_log_log);
        logText.setText(GameManager.game.getLog());
        logText.setMovementMethod(new ScrollingMovementMethod());
    }
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }
}
