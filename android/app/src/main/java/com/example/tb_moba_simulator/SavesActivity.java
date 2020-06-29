package com.example.tb_moba_simulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tb_moba_simulator.objects.SaveListAdapter;
import com.example.tb_moba_simulator.objects.SaveObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SavesActivity extends AppCompatActivity {
    private RecyclerView saves;
    private Button backToMenu;
    private ArrayList<SaveObject> saveObjects;
    private SaveListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
        initList();
        initRecyclerView();
        initButtons();
    }
    private void initList(){
        saveObjects = new ArrayList<SaveObject>();
        //Test
        saveObjects.add(createBlankSave());
        saveObjects.add(createBlankSave());
        saveObjects.add(createBlankSave());

    }
    private void initButtons(){
        backToMenu = findViewById(R.id.saves_back);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(SavesActivity.this, MenuActivity.class);
                startActivity(menu);
            }
        });
    }
    private void initRecyclerView(){
        saves = findViewById(R.id.saves_recyclerview);
        adapter = new SaveListAdapter(saveObjects, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        saves.setLayoutManager(manager);
        saves.setAdapter(adapter);
    }
    private SaveObject createBlankSave(){
        return new SaveObject(new ArrayList<HashMap>(),new ArrayList<HashMap>(), "", "TEST",
                new HashMap(),1,new ArrayList<HashMap>(),new ArrayList<HashMap>());
    }
}
