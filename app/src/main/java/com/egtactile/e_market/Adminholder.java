package com.egtactile.e_market;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.grpc.Context;

public class Adminholder extends RecyclerView.ViewHolder {
    TextView Name , Price , Description;
    ImageView  imageView , Delete;
    String num;
    Context context;
    public Adminholder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        Name = itemView.findViewById(R.id.ItemNameproductDetails);
        Price = itemView.findViewById(R.id.ItemPriceproductDetails);
        Description = itemView.findViewById(R.id.ItemDescriptionproductDetails);
        Delete = itemView.findViewById(R.id.deleteimg);
        imageView = itemView.findViewById(R.id.imageViewproductDetails);
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
