/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tb_moba_simulator.GameManager;
import com.example.tb_moba_simulator.GameMapActivity;
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds all of the items of the player
 */
public class BagListAdapter extends RecyclerView.Adapter<BagListAdapter.BagHolderClass>  {
    List<Item> items;
    static class BagHolderClass extends RecyclerView.ViewHolder{
        public TextView bagItemName, bagItemStats;
        public Button sell;
        public Button use;
        public ImageView img;
        public BagHolderClass(@NonNull View itemView) {
            super(itemView);
            this.bagItemName = itemView.findViewById(R.id.bag_item_name);
            this.bagItemStats = itemView.findViewById(R.id.bag_item_stats);
            this.sell = itemView.findViewById(R.id.bag_item_sell);
            this.use = itemView.findViewById(R.id.bag_item_use);
            this.img = itemView.findViewById(R.id.bag_item_image);
        }
    }

    /**
     * Constructor of the bag adapter
     * @param items the items that the player holds
     */
    public BagListAdapter(List<Item> items) {
        this.items = items;
    }

    /**
     * This is the default holder implementation
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BagHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bag_item, parent, false);
        BagHolderClass shc = new BagHolderClass(v);
        return shc;
    }

    /**
     * This class binds the elements of a table cell to the contents of an Item
     * @param holder Holder class
     * @param position position of table cell
     */
    @Override
    public void onBindViewHolder(@NonNull BagHolderClass holder, final int position) {
        final Item item = items.get(position);
        holder.bagItemName.setText(item.getName());
        holder.img.setImageResource(R.drawable.map);
        String itemStats = "";
        itemStats += "Power:\t" + item.getPower();
        itemStats += "\nCost:\t" + item.getCost();
        itemStats += "\nBoost:\t" + item.getBoostType().toString();
        holder.bagItemStats.setText(itemStats);
        holder.use.setEnabled(false);
        if(item.isConsumable()) {
            holder.use.setEnabled(true);
            holder.use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean didConsume = GameManager.game.getCurrentPlayer().consumeItem(item);
                    if(didConsume) {
                        notifyDataSetChanged();
                    }
                }
            });
        }
        holder.sell.setText("Sell for " +  (int)(item.getCost() / 2));
        holder.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean didSell = GameManager.game.getCurrentPlayer().sellItem(item);
                if(didSell) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Default implementation for getting the item count
     * @return
     */
    @Override
    public int getItemCount() {
        return this.items.size();
    }

}
