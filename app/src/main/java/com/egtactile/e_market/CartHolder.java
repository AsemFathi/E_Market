package com.egtactile.e_market;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView Name , Price , Quantity , Des;


    public CartHolder(@NonNull View itemView  , RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageViewCart);
        Name = itemView.findViewById(R.id.ItemNameCart);
        Price = itemView.findViewById(R.id.ItemPriceCart);
        Quantity = itemView.findViewById(R.id.ItemQuantityCart);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterface != null)
                {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                    {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });


    }
}
