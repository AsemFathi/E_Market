package com.egtactile.e_market;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyNewHolder> {
    Context context;
    List<items> itemsList;
   /* public interface OnClickListener{
        void onClick(int Position);
    }
    private OnClickListener mListener;
    public void OnItemClick(OnClickListener onClickListener){

        mListener = onClickListener;
    }
*/
    public MyAdapter(Context context, List<items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MyNewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNewHolder(LayoutInflater.from(context).inflate(R.layout.item_view , parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewHolder holder, int position) {
        holder.Name.setText(itemsList.get(position).getName());
        holder.Price.setText(itemsList.get(position).getPrice());



        /*
        * holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!= null)
                {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        mListener.onClick(position);

                }
                coffeeModels.get(position).quantity++;

                notifyItemChanged(position);
            }
        });
        holder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!= null)
                {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        mListener.onClick(position);

                }
                coffeeModels.get(position).quantity--;
                if (coffeeModels.get(position).getQuantity()<0)
                {
                    coffeeModels.get(position).quantity=0;
                }
                notifyDataSetChanged();
            }
        });*/
        Picasso.get()
                .load(itemsList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
