package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adminadapter extends RecyclerView.Adapter<Adminholder> implements RecyclerViewInterface {
    Context context;
    Map<String , String> data;
    DatabaseReference databaseReference1;
    List<items> itemsList;
    int quantity;
    private final RecyclerViewInterface recyclerViewInterface;
    public Adminadapter(android.content.Context context, List<items> itemsList,
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
    public Adminholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new Adminholder (LayoutInflater.from(context).inflate(R.layout.admin_recyclerview , parent , false) , recyclerViewInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull Adminholder holder, int position) {
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
        Picasso.get()
                .load(itemsList.get(position).getImage())
                .into(holder.imageView);
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

   /* public void FirebaseUpdate (int pos , String num) {

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

    }*/

    public void DeleteItem(String Name , String Num) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");
        DatabaseReference databaseReference2 = FirebaseDatabase
                .getInstance().getReference().child("Products");

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