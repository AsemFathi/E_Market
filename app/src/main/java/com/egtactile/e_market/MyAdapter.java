package com.egtactile.e_market;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyNewHolder> implements RecyclerViewInterface{
    Context context;
    List<items> itemsList;

    private final RecyclerViewInterface recyclerViewInterface;
    public MyAdapter(Context context, List<items> itemsList,
                     RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.itemsList = itemsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyNewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNewHolder(LayoutInflater.from(context).inflate(R.layout.item_view , parent ,false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewHolder holder, int position) {
        holder.Name.setText(itemsList.get(position).getName());
        holder.Price.setText(itemsList.get(position).getPrice());

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
