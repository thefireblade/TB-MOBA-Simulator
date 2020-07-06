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
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.List;

public class AttackPlayerAdapter extends RecyclerView.Adapter<AttackPlayerAdapter.MapPlayerHolderClass>  {
    List<Character> players;
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
    public AttackPlayerAdapter(List<Character> players, InGameActivity context) {
        this.players = players;
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
        holder.attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.game.basicAttack(GameManager.game.getCurrentPlayer(), null, player);
                GameManager.checkWin(context);
                GameManager.game.simulateTurn();
                context.popRecyclerStack();
                context.updatePlayerStats();
                context.updatePlayerInfo();
                GameManager.checkWin(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.players.size();
    }

}
