package com.egtactile.e_market;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyNewHolder> {
    Context context;
    List<items> itemsList;

    public MyAdapter(Context context, List<items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MyNewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNewHolder(LayoutInflater.from(context).inflate(R.layout.item_view , parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewHolder holder, int position) {
        holder.Name.setText(itemsList.get(position).getName());
        holder.Price.setText(itemsList.get(position).getPrice());
        //holder.imageView.setImageBitmap(itemsList.get(position).getImage());
        //holder.imageView.setImageResource(itemsList.get(position).getImage());
        Picasso.get()
                .load(itemsList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
