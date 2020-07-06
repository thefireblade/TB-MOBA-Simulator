/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.GameManager;
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.transform.Templates;

public class GameMoveAdapter extends RecyclerView.Adapter<GameMoveAdapter.GameModeHolderClass>  {
    List<Location> locations;
    private InGameActivity context;
    static class GameModeHolderClass extends RecyclerView.ViewHolder{
        public Button gameMode;
        public GameModeHolderClass(@NonNull View itemView) {
            super(itemView);
            this.gameMode = itemView.findViewById(R.id.game_mode_item_name);
        }
    }
    public GameMoveAdapter(List<Location> locations, InGameActivity context) {
        this.locations = locations;
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
        holder.gameMode.setText(locations.get(position).getName());
        holder.gameMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final ProgressDialog dialog = ProgressDialog.show( context, "",
//                        "Simulating Turn. Please wait...", true);
                Character player = GameManager.game.getCurrentPlayer();
                GameManager.game.locatePlayer(player).getPlayers().remove(player);
                locations.get(position).getPlayers().add(player);
                GameManager.game.simulateTurn();
                context.popRecyclerStack();
                context.updatePlayerInfo();
                context.updatePlayerStats();
                GameManager.checkWin(context);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        dialog.dismiss();
//                    }
//                }, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.locations.size();
    }
}
