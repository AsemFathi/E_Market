package com.egtactile.e_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class productorder_adapter extends RecyclerView.Adapter<productorder_holder> implements RecyclerViewInterface{
    Context context;
    List<items> products;
    private final RecyclerViewInterface recyclerViewInterface;

    public productorder_adapter(Context con,List<items> productitems,
                                RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = con;
        this.products=productitems;
    }

    @NonNull
    @Override
    public productorder_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new productorder_holder(LayoutInflater.from(context).inflate(R.layout.item_products_order, parent , false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull productorder_holder holder, int position) {
        holder.productName.setText(products.get(position).getName().toString());
        holder.productCat.setText(products.get(position).getCategory().toString());
        holder.productPrice.setText(products.get(position).getPrice().toString());
        holder.productQuantity.setText((products.get(position).getNum().toString()));
        Picasso.get()
                .load(products.get(position).getImage())
                .into(holder.productPic);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
