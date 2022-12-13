package com.egtactile.e_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> implements RecyclerViewInterface {
   Context context;
   List<Category> categoryList;
   private final RecyclerViewInterface recyclerViewInterface;

    public CategoryAdapter(android.content.Context context, List<Category> categoryList,
                           RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.categoryList = categoryList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.categoryview , parent ,false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.CategoryName.setText(categoryList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
