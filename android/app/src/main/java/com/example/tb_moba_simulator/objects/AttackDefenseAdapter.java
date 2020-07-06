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
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.List;

/**
 * Class for managing the attack table in-game
 */
public class AttackDefenseAdapter extends RecyclerView.Adapter<AttackDefenseAdapter.MapPlayerHolderClass>  {
    List<Mob> defenses;
    InGameActivity context;

    /**
     * Sub-Class that manages handling the components of a table item
     */
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

    /**
     * Constructor for AttackDefenseAdapter
     * @param defenses a List of defensive structures to be stored in the table cell
     * @param context The in-game context used for updating the in-game view
     */
    public AttackDefenseAdapter(List<Mob> defenses, InGameActivity context) {
        this.defenses = defenses;
        this.context = context;
    }

    /**
     * Creates a View Holder using the layout of attack_player.xml
     * @param parent View Group
     * @param viewType View Type
     * @return Holder class
     */
    @NonNull
    @Override
    public MapPlayerHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attack_player, parent, false);
        MapPlayerHolderClass shc = new MapPlayerHolderClass(v);
        return shc;
    }

    /**
     * Binds the defensive mob to the table cell
     * @param holder Holder class
     * @param position Position of the table cell
     */
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
                GameManager.game.basicAttack(GameManager.game.getCurrentPlayer(), defense, null);
                GameManager.checkWin(context);
                GameManager.game.simulateTurn();
                context.popRecyclerStack();
                context.updatePlayerStats();
                context.updatePlayerInfo();
                GameManager.checkWin(context);
            }
        });
    }

    /**
     *  Returns the size of the defenses table
     * @return size of defenses
     */
    @Override
    public int getItemCount() {
        return this.defenses.size();
    }

}
