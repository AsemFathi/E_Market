package com.egtactile.e_market.ui.basket;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egtactile.e_market.CartAdapter;
import com.egtactile.e_market.MyAdapter;
import com.egtactile.e_market.ProductCartDetails;
import com.egtactile.e_market.ProductDetails;
import com.egtactile.e_market.R;
import com.egtactile.e_market.RecyclerViewInterface;
import com.egtactile.e_market.databinding.FragmentBasketBinding;
import com.egtactile.e_market.items;
import com.egtactile.e_market.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BasketFragment extends Fragment implements RecyclerViewInterface {

    private FragmentBasketBinding binding;

    Button Next;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    String urldisplay;
    Map<String , String> data ;
    List<String> info = new ArrayList<>();
    static List<items> itemsList=new ArrayList<items>();
    String email;
    TextView TotalPrice;
    static int Total_Price ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TotalPrice = root.findViewById(R.id.total_price);
        itemsList = new ArrayList<items>();
        recyclerView = root.findViewById(R.id.CartList);
        data = new HashMap<>();
        Next = root.findViewById(R.id.Next_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Cart");
        storageReference = FirebaseStorage.getInstance().getReference();
        email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");
        Total_Price = 0;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren())
               {

                   Log.i(TAG, "onDataChange: Child "+ dataSnapshot.getChildren().toString());
                   if (dataSnapshot.getKey().equals(email))
                   {
                       Log.i(TAG, "onDataChange: Email : ");
                       for (DataSnapshot datax : dataSnapshot.getChildren()) {
                           Log.i(TAG, "onDataChange: Product : " + datax.getKey().toString());
                           String ProductName = datax.getKey();
                           String ProductType = datax.child("Category").getValue().toString();
                           String ProPrice = datax.child("Price").getValue().toString();
                           String ProNum = datax.child("Quantity").getValue().toString();
                           String ProImageUrl = datax.child("Picture").getValue().toString();
                           String des = datax.child("Description").getValue().toString();
                           String ProNumber = datax.child("Num").getValue().toString();
                           Log.i(TAG, "onDataChange: Description" + des);
                           storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   urldisplay = uri.toString();
                               }
                           });


                           Total_Price += Integer.parseInt(ProPrice) * Integer.parseInt(ProNum);
                           data.put(ProductName.toLowerCase(), ProNumber);
                           itemsList.add(new items(ProPrice, ProductName, des, ProImageUrl, ProductType, ProNum));

                       }
                   }

               }
               recyclerView.setAdapter(new CartAdapter(getActivity(), itemsList, BasketFragment.this));
               TotalPrice.setText("Total Price : "+ String.valueOf(Total_Price));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , ProductCartDetails.class);
                intent.putExtra("Price" , String.valueOf(Total_Price));
                startActivity(intent);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity() , ProductDetails.class);
        intent.putExtra("Name" , itemsList.get(pos).getName());
        intent.putExtra("Type" , itemsList.get(pos).getCategory());
        intent.putExtra("Price" , itemsList.get(pos).getPrice());
        intent.putExtra("Num" , itemsList.get(pos).getNum());
        intent.putExtra("Description" , itemsList.get(pos).getDescription());
        intent.putExtra("image" , itemsList.get(pos).getImage());
        startActivity(intent);
    }

    public List<items> getItemsList()
    {
        return itemsList;
    }
    public Map<String , String> getData()
    {

        Log.i(TAG, "getData: Data "+data);
        return data;
    }
    public int getTotal_Price()
    {
        return Total_Price;
    }

}