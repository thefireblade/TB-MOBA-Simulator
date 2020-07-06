package com.example.tb_moba_simulator.objects;

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
import java.util.List;

public class MapMobAdapter extends RecyclerView.Adapter<MapMobAdapter.MapPlayerHolderClass>  {
    List<Mob> mobs;
    static class MapPlayerHolderClass extends RecyclerView.ViewHolder{
        public TextView name, stats;
        public TextView team;
        public Button use;
        public ImageView img;
        public MapPlayerHolderClass(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.map_player_name);
            this.stats = itemView.findViewById(R.id.map_player_stats);
            this.team = itemView.findViewById(R.id.map_player_team);
            this.img = itemView.findViewById(R.id.map_player_img);
        }
    }
    public MapMobAdapter(List<Mob> mobs) {
        this.mobs = mobs;
    }
    @NonNull
    @Override
    public MapPlayerHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_player, parent, false);
        MapPlayerHolderClass shc = new MapPlayerHolderClass(v);
        return shc;
    }

    @Override
    public void onBindViewHolder(@NonNull MapPlayerHolderClass holder, final int position) {
        final Mob mob = mobs.get(position);
        holder.name.setText(mob.getName());
        holder.img.setImageResource(R.drawable.map);
        String stats = "";
        stats += "Health:\t" + mob.getHealth();
        stats += "\nAttack:\t" + mob.getAttack();
        holder.stats.setText(stats);
        holder.team.setText(mob.getTeam().toString());
    }

    @Override
    public int getItemCount() {
        return this.mobs.size();
    }

}
