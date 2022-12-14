package com.egtactile.e_market;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyNewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView Name , Price , Num , Des;

    public MyNewHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        Name = itemView.findViewById(R.id.ItemName);
        Price = itemView.findViewById(R.id.ItemPrice);

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
