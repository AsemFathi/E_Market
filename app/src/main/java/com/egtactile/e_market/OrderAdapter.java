package com.egtactile.e_market;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<Orderholder> implements RecyclerViewInterface{
    Context context;
    List<String> userss;
    List<items> itemsList;
    String Date;
    DatabaseReference dataref = FirebaseDatabase
            .getInstance().getReference().child("Sold");
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    String urldisplay;
    private final RecyclerViewInterface recyclerViewInterface;

    public OrderAdapter(Context con,List<String> users,String date,RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = con;
        this.userss=users;
        this.Date = date;
    }

    @NonNull
    @Override
    public Orderholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Orderholder(LayoutInflater.from(context).inflate(R.layout.user_orderinfo, parent , false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Orderholder holder, int position) {
        holder.showuser.setText(userss.get(position).toString());

        dataref.child(userss.get(position).toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList=new ArrayList<>();
                for(DataSnapshot ordersdata: snapshot.getChildren()){
                    String datestring = ordersdata.child("Date").getValue().toString();
                    String productName = ordersdata.getKey();
                    String productCat = ordersdata.child("Category").getValue().toString();
                    String productDesc = ordersdata.child("Description").getValue().toString();
                    String productPrice = ordersdata.child("Price").getValue().toString();
                    String productQuantity = ordersdata.child("Quantity").getValue().toString();
                    String ProImageUrl = ordersdata.child("Picture").getValue().toString();
                    storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urldisplay = uri.toString();
                        }
                    });
                    if(Date.equals("no")){
                        itemsList.add(new items(productPrice, productName, productDesc, ProImageUrl, productCat, productQuantity));
                    }
                    else {
                        if (Date.equals(datestring)){
                            itemsList.add(new items(productPrice, productName, productDesc, ProImageUrl, productCat, productQuantity));
                        }
                    }
                }
                holder.orderProdAdminAdapter = new productorder_adapter(context,itemsList,recyclerViewInterface);
                holder.products_recycler.setLayoutManager(new LinearLayoutManager(context));
                holder.products_recycler.setAdapter((holder.orderProdAdminAdapter));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return userss.size();
    }

    @Override
    public void onItemClick(int pos) {
    }
}
