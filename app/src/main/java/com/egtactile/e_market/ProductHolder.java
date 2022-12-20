package com.egtactile.e_market;

import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egtactile.e_market.ui.basket.BasketFragment;

import io.grpc.Context;

public class ProductHolder extends RecyclerView.ViewHolder {
    TextView Name , Price , Description , Quantity;
    ImageView imageViewAdd , imageViewRemove , imageView , Delete;
    String num;
    Context context;
    public ProductHolder(@NonNull View itemView ,RecyclerViewInterface recyclerViewInterface) {
        super(itemView);


        Name = itemView.findViewById(R.id.ItemNameCartDetails);
        Price = itemView.findViewById(R.id.ItemPriceCartDetails);
        Description = itemView.findViewById(R.id.ItemDescriptionCartDetails);
        Quantity = itemView.findViewById(R.id.tv_quantity);
        Delete = itemView.findViewById(R.id.delete_img);
        imageViewAdd = itemView.findViewById(R.id.im_add);
        imageViewRemove = itemView.findViewById(R.id.im_reduce);
        imageView = itemView.findViewById(R.id.imageViewCartDetails);
        //num = "";
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
