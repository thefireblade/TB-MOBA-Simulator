/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.content.Context;
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
import com.example.tb_moba_simulator.InGameActivity;
import com.example.tb_moba_simulator.R;

import java.util.ArrayList;

/**
 * The adapter used for the shop in-game. Documentation for this adapter is the same for the other adapters (Search for AttackDefenseAdapter).
 */
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopHolderClass>  {
    ArrayList<Item> items;
    private InGameActivity context;
    static class ShopHolderClass extends RecyclerView.ViewHolder{
        public TextView shopItemName, shopItemStats;
        public Button purchase;
        public CheckBox consumable;
        public ImageView img;
        public ShopHolderClass(@NonNull View itemView) {
            super(itemView);
            this.shopItemName = itemView.findViewById(R.id.shop_item_name);
            this.shopItemStats = itemView.findViewById(R.id.shop_item_stats);
            this.purchase = itemView.findViewById(R.id.shop_item_purchase);
            this.consumable = itemView.findViewById(R.id.shop_item_consumable);
            this.img = itemView.findViewById(R.id.shop_item_image);
        }
    }
    public ShopListAdapter(ArrayList<Item> items, InGameActivity context) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ShopHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        ShopHolderClass shc = new ShopHolderClass(v);
        return shc;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolderClass holder, final int position) {
        final Item item = items.get(position);
        holder.shopItemName.setText(item.getName());
        holder.img.setImageResource(R.drawable.map);
        String itemStats = "";
        itemStats += "Power:\t" + item.getPower();
        itemStats += "\nCost:\t" + item.getCost();
        itemStats += "\nBoost:\t" + item.getBoostType().toString();
        holder.shopItemStats.setText(itemStats);
        holder.consumable.setChecked(item.isConsumable());
        holder.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean didPurchase = GameManager.game.getCurrentPlayer().purchaseItem(item);
                context.updatePlayerInfo();
                context.updatePlayerStats();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    private void deleteItem(int position){
        this.items.remove(position);
        notifyDataSetChanged();
    }

}
