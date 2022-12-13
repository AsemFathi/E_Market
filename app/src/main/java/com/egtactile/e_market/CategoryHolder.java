package com.egtactile.e_market;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryHolder extends RecyclerView.ViewHolder{
    TextView CategoryName ;
    public CategoryHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        CategoryName  = itemView.findViewById(R.id.CategoryName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface!=null)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });

    }
}
