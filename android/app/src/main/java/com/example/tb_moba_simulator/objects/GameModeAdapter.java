package com.example.tb_moba_simulator.objects;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.GameManager;
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.MenuActivity;
import com.example.tb_moba_simulator.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class GameModeAdapter extends RecyclerView.Adapter<GameModeAdapter.GameModeHolderClass>  {
    ArrayList<Map<String, Object>> gameModes;
    private Context context;
    static class GameModeHolderClass extends RecyclerView.ViewHolder{
        public Button gameMode;
        public GameModeHolderClass(@NonNull View itemView) {
            super(itemView);
            this.gameMode = itemView.findViewById(R.id.game_mode_item_name);
        }
    }
    public GameModeAdapter(ArrayList<Map<String, Object>> gameModes, Context context) {
        this.gameModes = gameModes;
        this.context = context;
    }
    @NonNull
    @Override
    public GameModeHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_mode_item, parent, false);
        GameModeHolderClass gmh = new GameModeHolderClass(v);
        return gmh;
    }

    @Override
    public void onBindViewHolder(@NonNull GameModeHolderClass holder, final int position) {
        holder.gameMode.setText((String)gameModes.get(position).get("name"));
        holder.gameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InGameActivity.class);
                Map<String, Object> currentLand = gameModes.get(position);
                GameManager.initGameMode(currentLand);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.gameModes.size();
    }
    public void addGameMode(Map<String, Object> gameMode) {
        this.gameModes.add(gameMode);
        notifyDataSetChanged();
    }
}
