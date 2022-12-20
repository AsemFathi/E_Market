package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.egtactile.e_market.ui.basket.BasketFragment;
import com.egtactile.e_market.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> implements RecyclerViewInterface {
    Context context;
    Map<String , String> data;
    DatabaseReference databaseReference1;
    List<items> itemsList;
    int quantity;
    private final RecyclerViewInterface recyclerViewInterface;
    public ProductAdapter(android.content.Context context, List<items> itemsList,
            RecyclerViewInterface recyclerViewInterface ) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.itemsList = itemsList;

    }


    @Override
    public void onItemClick(int pos) {

    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ProductHolder (LayoutInflater.from(context).inflate(R.layout.product_cart_details , parent , false) , recyclerViewInterface) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        //--------------------------------------
        databaseReference1 = FirebaseDatabase
                .getInstance().getReference().child("Products");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datax: snapshot.getChildren())
                {
                    String ProductName = datax.getKey();
                    if (ProductName.toLowerCase().contains(itemsList.get(position).getName().toLowerCase())) {
                        holder.num = datax.child("Num").getValue().toString();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //--------------------------------------
        Log.i(TAG, "onBindViewHolder: Data "+data);
        holder.Name.setText("Name: "+itemsList.get(position).getName());
        quantity = Integer.parseInt(itemsList.get(position).getNum());
        holder.Price.setText("Price: "+ itemsList.get(position).getPrice());
        holder.Description.setText("Description: " + itemsList.get(position).getDescription());
        holder.Quantity.setText(itemsList.get(position).getNum());
        Picasso.get()
                .load(itemsList.get(position).getImage())
                .into(holder.imageView);
        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = quantity;
                if (Integer.parseInt(holder.Quantity.getText().toString()) > Integer.parseInt(holder.num))
                {
                    holder.Quantity.setText(holder.num);

                }
                else
                {
                    quantity++;
                    holder.Quantity.setText(String.valueOf(quantity));
                    number = Integer.parseInt(holder.num);
                    number--;
                }
                itemsList.get(position).setNum(String.valueOf(quantity));
                notifyItemChanged(position);

                String num = String.valueOf(number);
                FirebaseUpdate(position , num);

            }
        });
        holder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = quantity;
                if(Integer.parseInt(holder.Quantity.getText().toString()) <= 0)
                {
                    holder.Quantity.setText("0");
                }
                else
                {
                    quantity--;
                    holder.Quantity.setText(String.valueOf(quantity));
                    number = Integer.parseInt(holder.num);
                    number++;
                }
                notifyItemChanged(position);
                itemsList.get(position).setNum(String.valueOf(quantity));
                String num = String.valueOf(number);
                FirebaseUpdate(position , num);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteItem(itemsList.get(position).getName() , itemsList.get(position).getNum());
            }
        });

    }
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void FirebaseUpdate (int pos , String num) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart")
                .child(email).child(itemsList.get(pos).getName());

        HashMap map = new HashMap<>();
        map.put("Category" , itemsList.get(pos).getCategory());
        map.put("Description" , itemsList.get(pos).getDescription());
        map.put("Name" , itemsList.get(pos).getName());
        map.put("Num" , num);
        map.put("Picture" , itemsList.get(pos).getImage());
        map.put("Price" , itemsList.get(pos).getPrice());
        map.put("Quantity" , itemsList.get(pos).getNum());

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                    Log.i(TAG, "onComplete: Updaaaaaaaaaaaaaaaaaaaated");
            }
        });

    }

    public void DeleteItem(String Name , String Num) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");
        DatabaseReference databaseReference2 = FirebaseDatabase
                .getInstance().getReference().child("Cart").child(email);

       /* databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int  num = Integer.parseInt(snapshot.child(Name).child("Num").getValue().toString());
                int x = Integer.parseInt(Num);
                int s = num + x;
                databaseReference1.child(Name).child("Num").setValue(s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/

        databaseReference2.child(Name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(context, "Product is deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}