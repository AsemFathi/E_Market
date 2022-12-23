package com.egtactile.e_market;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class productorder_holder extends RecyclerView.ViewHolder{
    ImageView productPic;
    TextView productName,productCat,productPrice,productQuantity;
    public productorder_holder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        productPic = itemView.findViewById(R.id.img_prod_item);
        productName= itemView.findViewById(R.id.txt_prod_name);
        productCat= itemView.findViewById(R.id.txt_prod_cat);
        productPrice= itemView.findViewById(R.id.txt_prod_price);
        productQuantity= itemView.findViewById(R.id.txt_prod_count);

    }
}
