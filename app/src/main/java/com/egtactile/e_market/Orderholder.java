package com.egtactile.e_market;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Orderholder extends RecyclerView.ViewHolder{
    TextView showuser;

    public Orderholder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        showuser = itemView.findViewById(R.id.show_username);
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
