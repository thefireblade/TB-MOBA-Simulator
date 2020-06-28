package com.example.tb_moba_simulator.objects;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.SaveHolderClass>  {
    ArrayList<SaveObject> saves;
    static class SaveHolderClass extends RecyclerView.ViewHolder{
        public SaveHolderClass(@NonNull View itemView) {
            super(itemView);
        }
    }
    public SaveListAdapter(ArrayList<SaveObject> saves) {
        this.saves = saves;
    }
    @NonNull
    @Override
    public SaveHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SaveHolderClass holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
