package com.egtactile.e_market;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackHolder extends RecyclerView.ViewHolder {
    TextView Feedback , Username;
    RatingBar ratingBar;
    public FeedbackHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        Feedback = itemView.findViewById(R.id.Feedback);
        ratingBar = itemView.findViewById(R.id.RatingBar);
        Username = itemView.findViewById(R.id.FeedbackUserName);
        ratingBar.setIsIndicator(true);
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
