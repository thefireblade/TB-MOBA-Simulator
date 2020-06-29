package com.example.tb_moba_simulator.objects;

import android.content.Context;
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

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.SaveHolderClass>  {
    ArrayList<SaveObject> saves;
    private Context context;
    static class SaveHolderClass extends RecyclerView.ViewHolder{
        public TextView saveName;
        public Button load, delete;
        public ImageView img;
        public SaveHolderClass(@NonNull View itemView) {
            super(itemView);
            this.saveName = itemView.findViewById(R.id.save_item_name);
            this.load = itemView.findViewById(R.id.save_item_load);
            this.delete = itemView.findViewById(R.id.save_item_delete);
            this.img = itemView.findViewById(R.id.save_item_image);
        }
    }
    public SaveListAdapter(ArrayList<SaveObject> saves, Context context) {
        this.saves = saves;
        this.context = context;
    }
    @NonNull
    @Override
    public SaveHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item, parent, false);
        SaveHolderClass shc = new SaveHolderClass(v);
        return shc;
    }

    @Override
    public void onBindViewHolder(@NonNull SaveHolderClass holder, final int position) {
        final SaveObject currSave = saves.get(position);
        holder.saveName.setText(currSave.getName());
        holder.img.setImageResource(R.drawable.save);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currSave.delete();
                deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.saves.size();
    }
    private void deleteItem(int position){
        this.saves.remove(position);
        notifyDataSetChanged();
    }

}
