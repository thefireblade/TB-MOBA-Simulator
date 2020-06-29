package com.example.tb_moba_simulator.objects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.R;

import java.util.ArrayList;

public class GameModeAdapter extends RecyclerView.Adapter<GameModeAdapter.GameModeHolderClass>  {
    ArrayList<String> gameModes;
    private Context context;
    static class GameModeHolderClass extends RecyclerView.ViewHolder{
        public Button gameMode;
        public GameModeHolderClass(@NonNull View itemView) {
            super(itemView);
            this.gameMode = itemView.findViewById(R.id.game_mode_title);
        }
    }
    public GameModeAdapter(ArrayList<String> gameModes, Context context) {
        this.gameModes = gameModes;
        this.context = context;
    }
    @NonNull
    @Override
    public GameModeHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item, parent, false);
        GameModeHolderClass gmh = new GameModeHolderClass(v);
        return gmh;
    }

    @Override
    public void onBindViewHolder(@NonNull GameModeHolderClass holder, final int position) {
        final String gameMode = gameModes.get(position);
        holder.gameMode.setText(gameMode);
        holder.gameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.gameModes.size();
    }
}
