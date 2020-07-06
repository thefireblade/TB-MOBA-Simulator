package com.example.tb_moba_simulator.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.List;

public class AttackDefenseAdapter extends RecyclerView.Adapter<AttackDefenseAdapter.MapPlayerHolderClass>  {
    List<Mob> defenses;
    InGameActivity context;
    static class MapPlayerHolderClass extends RecyclerView.ViewHolder{
        public TextView name, stats;
        public ImageView img;
        public Button attack;
        public MapPlayerHolderClass(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.attack_player_name);
            this.stats = itemView.findViewById(R.id.attack_player_stats);
            this.attack = itemView.findViewById(R.id.attack_player_attack);
            this.img = itemView.findViewById(R.id.attack_player_img);
        }
    }
    public AttackDefenseAdapter(List<Mob> defenses, InGameActivity context) {
        this.defenses = defenses;
        this.context = context;
    }
    @NonNull
    @Override
    public MapPlayerHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attack_player, parent, false);
        MapPlayerHolderClass shc = new MapPlayerHolderClass(v);
        return shc;
    }

    @Override
    public void onBindViewHolder(@NonNull MapPlayerHolderClass holder, final int position) {
        final Mob defense = defenses.get(position);
        holder.name.setText(defense.getName());
        holder.img.setImageResource(R.drawable.castle);
        String stats = "";
        stats += "Health:\t" + defense.getHealth();
        holder.stats.setText(stats);
        holder.attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.defenses.size();
    }

}
