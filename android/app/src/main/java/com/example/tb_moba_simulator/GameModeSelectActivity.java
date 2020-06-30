package com.example.tb_moba_simulator;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.objects.GameModeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GameModeSelectActivity extends AppCompatActivity {
    private ArrayList<String> gameModes;
    private GameModeAdapter adapter;
    private RecyclerView selectGameModes;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode_pop);
        makeSelfPopUp();
        initList();
        initRecyclerView();
    }
    private void initList(){
        gameModes = new ArrayList<String>();
        FirebaseManager.db.collection("lands")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                adapter.addGameMode(document.getId());
                            }
                        } else {
                            System.out.println(task.getException());
                        }
                    }
                });

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
}
