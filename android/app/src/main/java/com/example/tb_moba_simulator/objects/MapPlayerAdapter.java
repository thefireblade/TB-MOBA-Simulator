/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.GameManager;
import com.example.tb_moba_simulator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter class that will show any Characters in the location the user is in. Documentation for this adapter is the same for the other adapters (Search for AttackDefenseAdapter).
 */
public class MapPlayerAdapter extends RecyclerView.Adapter<MapPlayerAdapter.MapPlayerHolderClass>  {
    List<Character> players;
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
    public MapPlayerAdapter(List<Character> players) {
        this.players = players;
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
        final Character player = players.get(position);
        holder.name.setText(player.getName());
        switch(player.getType()){
            case sword: holder.img.setImageResource(R.drawable.sword); break;
            case shield: holder.img.setImageResource(R.drawable.shield); break;
            default: holder.img.setImageResource(R.drawable.archer);
        }
        String stats = "";
        stats += "Health:\t" + player.getCurrHP();
        stats += "\nEnergy:\t" + player.getCurrEnergy();
        stats += "\nLevel:\t" + player.getLevel();
        holder.stats.setText(stats);
        holder.team.setText(player.getTeam().toString());
    }

    @Override
    public int getItemCount() {
        return this.players.size();
    }

}
