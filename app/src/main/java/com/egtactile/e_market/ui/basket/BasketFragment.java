package com.egtactile.e_market.ui.basket;

import static android.content.ContentValues.TAG;

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

import com.egtactile.e_market.R;
import com.egtactile.e_market.databinding.FragmentBasketBinding;
import com.egtactile.e_market.items;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketFragment extends Fragment {

    private FragmentBasketBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    String urldisplay;
    EditText searchText;
    Button searchView_btn,voice_search,qr_search;
    String searchInput;
    Map<String , List<String>> data = new HashMap<>();
    List<String> info = new ArrayList<>();
    List<items> itemsList;
String email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsList = new ArrayList<items>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Cart");
        storageReference = FirebaseStorage.getInstance().getReference();
        email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");
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


                       }
                   }

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}