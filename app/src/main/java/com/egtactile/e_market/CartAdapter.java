package com.egtactile.e_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> implements RecyclerViewInterface{
    Context context;
    List<items> itemsList;
    private final RecyclerViewInterface recyclerViewInterface;
    public CartAdapter(Context context, List<items> itemsList,
                     RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.itemsList = itemsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override

    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(LayoutInflater.from(context).inflate(R.layout.cartview , parent ,false) , recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.Name.setText(itemsList.get(position).getName());
        holder.Price.setText(itemsList.get(position).getPrice());
        holder.Quantity.setText(itemsList.get(position).getNum());
        Picasso.get()
                .load(itemsList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
