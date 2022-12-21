package com.egtactile.e_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<Orderholder> implements RecyclerViewInterface{
    Context context;
    List<String> userss;
    private final RecyclerViewInterface recyclerViewInterface;

    public OrderAdapter(Context con,List<String> users,RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = con;
        this.userss=users;
    }

    @NonNull
    @Override
    public Orderholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Orderholder(LayoutInflater.from(context).inflate(R.layout.user_orderinfo, parent , false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Orderholder holder, int position) {
        holder.showuser.setText(userss.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return userss.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
