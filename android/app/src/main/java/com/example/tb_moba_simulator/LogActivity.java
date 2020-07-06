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

/**
 * Activity to display the in-game logs
 */
public class LogActivity extends AppCompatActivity {
    private TextView logText;
    private Button backGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_log_pop);
        makeSelfPopUp();
        initButtons();
        initTextView();
    }

    /**
     * initialize all of the buttons in the log
     */
    private void initButtons(){
        backGame = findViewById(R.id.game_log_back);
        backGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * sets up the log textview
     */
    public void initTextView(){
        logText = findViewById(R.id.game_log_log);
        logText.setText(GameManager.game.getLog());
        logText.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Makes the log a pop up
     */
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }
}
