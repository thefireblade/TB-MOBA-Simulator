/**
 * Jason Huang
 * 110779373
 */
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

/**
 * Basic adapter that just manages the many game modes to be implemented later
 */
public class GameModeAdapter extends RecyclerView.Adapter<GameModeAdapter.GameModeHolderClass>  {
    ArrayList<Map<String, Object>> gameModes;
    private Context context;

    /**
     * Basic implementation of a Holder class
     */
    static class GameModeHolderClass extends RecyclerView.ViewHolder{
        public Button gameMode;

        /**
         * Basic implementation of a constructor for the holder class
         * @param itemView
         */
        public GameModeHolderClass(@NonNull View itemView) {
            super(itemView);
            this.gameMode = itemView.findViewById(R.id.game_mode_item_name);
        }
    }

    /**
     * Basic constructor for the adapter
     * @param gameModes
     * @param context
     */
    public GameModeAdapter(ArrayList<Map<String, Object>> gameModes, Context context) {
        this.gameModes = gameModes;
        this.context = context;
    }

    /**
     * Basic implementation of an adapter method
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public GameModeHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_mode_item, parent, false);
        GameModeHolderClass gmh = new GameModeHolderClass(v);
        return gmh;
    }

    /**
     * Basic implementation. Takes the name of the gamemode (From a map taken from the cloud) and starts the game
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull GameModeHolderClass holder, final int position) {
        holder.gameMode.setText((String)gameModes.get(position).get("name"));
        holder.gameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InGameActivity.class);
                Map<String, Object> currentLand = gameModes.get(position);
                GameManager.initGameMode(currentLand);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Basic implementation
     * @return
     */
    @Override
    public int getItemCount() {
        return this.gameModes.size();
    }

    /**
     * Add a new gamemode to the adapter. To be implemented with more gamemodes and map creation
     * @param gameMode
     */
    public void addGameMode(Map<String, Object> gameMode) {
        this.gameModes.add(gameMode);
        notifyDataSetChanged();
    }
}
