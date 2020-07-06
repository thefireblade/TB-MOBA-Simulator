/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tb_moba_simulator.objects.SaveListAdapter;
import com.example.tb_moba_simulator.objects.SaveObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The activity the controls the loading and deleting of save objects
 */
public class SavesActivity extends AppCompatActivity {
    private RecyclerView saves;
    private Button backToMenu;
    private ArrayList<SaveObject> saveObjects;
    private SaveListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
        saveObjects = new ArrayList<SaveObject>();
        initRecyclerView();
        initButtons();
        initList();
    }

    /**
     * Query all of the saves of a user
     */
    private void initList(){
        FirebaseManager.db.collection("saves")
                .whereEqualTo("user", FirebaseManager.mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                parseSaveData(document.getData(), document.getId());
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            System.out.println(task.getException());
                        }
                    }
                });
    }

    /**
     * Parses save data into a save object and add them to the table
     * @param data save data
     * @param docID the document ID of the save data
     */
    private void parseSaveData(Map<String,Object> data, String docID) {
        SaveObject save = new SaveObject(
                (ArrayList<Map<String,Object>>) data.get("defenses"),
                (ArrayList<Map<String,Object>>) data.get("mobs"),
                (String)data.get("landtype"),
                (String)data.get("name"),
                (HashMap<String,Object>)data.get("playerInfo"),
                (int)Double.parseDouble(data.get("turn").toString()),
                (ArrayList<Map<String,Object>>) data.get("team_0"),
                (ArrayList<Map<String,Object>>) data.get("team_1"),
                (String)data.get("date"),
                docID,
                (String)data.get("log")
        );
        adapter.addItem(save);
    }

    /**
     * Initializes all of the buttons
     */
    private void initButtons(){
        backToMenu = findViewById(R.id.saves_back);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Initialize the save table
     */
    private void initRecyclerView(){
        saves = findViewById(R.id.saves_recyclerview);
        adapter = new SaveListAdapter(saveObjects, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        saves.setLayoutManager(manager);
        saves.setAdapter(adapter);
    }
}
