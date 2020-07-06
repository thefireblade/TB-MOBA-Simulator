/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.GameModeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class GameModeSelectActivity extends AppCompatActivity {
    private ArrayList<Map<String, Object>> gameModes;
    private GameModeAdapter adapter;
    private RecyclerView selectGameModes;
    private Spinner spinner;
    private ArrayList<String> spinnerOptions;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode_pop);
        makeSelfPopUp();
        initList();
        initRecyclerView();
        initSpinner();
    }
    private void initList(){
        gameModes = GameManager.lands;
    }
    private void initRecyclerView(){
        selectGameModes = findViewById(R.id.game_mode_recycler);
        adapter = new GameModeAdapter(gameModes, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        selectGameModes.setLayoutManager(manager);
        selectGameModes.setAdapter(adapter);
    }
    private void makeSelfPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));
    }
    private void initSpinner() {
        spinner = findViewById(R.id.game_mode_spinner);
        spinnerOptions = new ArrayList<>();
        for(Character.CharacterClass type: Character.CharacterClass.values()) {
            spinnerOptions.add(type.toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spinnerOptions.get(position);
                GameManager.selectedType = Character.CharacterClass.valueOf(selected);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }
}
