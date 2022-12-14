package com.egtactile.e_market.ui.categories;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egtactile.e_market.Category;
import com.egtactile.e_market.CategoryAdapter;
import com.egtactile.e_market.CategoryItems;
import com.egtactile.e_market.MyAdapter;
import com.egtactile.e_market.ProductDetails;
import com.egtactile.e_market.R;
import com.egtactile.e_market.RecyclerViewInterface;
import com.egtactile.e_market.databinding.FragmentBasketBinding;
import com.egtactile.e_market.databinding.FragmentCategoriesBinding;
import com.egtactile.e_market.databinding.FragmentHomeBinding;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoriesFragment extends Fragment implements RecyclerViewInterface {

    private FragmentCategoriesBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    List<Category> categoryList= new ArrayList<Category>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoriesViewModel categoriesViewModel =
                new ViewModelProvider(this).get(CategoriesViewModel.class);

        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Products");
        storageReference = FirebaseStorage.getInstance().getReference();

        //get data from firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               /* Map<String , Object> map = (Map) snapshot.getValue();  // get type
                for (Map.Entry<String,Object > pair : map.entrySet()) {
                    //System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", pair.getKey(), pair.getValue()));
                    //itemsList.add(new items("10" , pair.getKey(), R.drawable.main_app));
                    Map<String , Object> map1 = (Map) snapshot.child(pair.getKey()).getValue(); // get name
                    Log.i(TAG,"Type "+ pair.getKey() + "  " +pair.getValue());
                    for (Map.Entry<String , Object> pair1 : map1.entrySet())
                    {
                        Log.i(TAG,"Name "+ pair1.getKey() + "  " +pair1.getValue());
                        Map<String , Object> map2 = (Map) snapshot.child(pair.getKey()).child(pair1.getKey()).getValue();
                        int i =0;
                        for (Map.Entry<String , Object> pair2 : map2.entrySet())
                        {
                           // Map<String , Object> map2 = (Map)snapshot.child(pair.getKey()).child(pair1.getKey()).child(pair2.getKey()).getValue();

                            Log.i(TAG,"Price "+ pair2.getKey() + "  " +pair2.getValue());
                            if (i == 0)
                                itemsList.add(new items(pair2.getValue().toString() , pair1.getKey(), R.drawable.main_app));
                            //
                            i++;

                        }

                    }

                }

                */
                for(DataSnapshot datax: snapshot.getChildren())
                {
                    boolean flag = false;

                    String ProductType = datax.child("Category").getValue().toString();
                    for (int i = 0 ; i < categoryList.size() ; i++)
                    {
                         if (ProductType.equals( categoryList.get(i).getCategoryName()))
                             flag = true;
                    }
                    if (!flag)
                        categoryList.add(new Category(ProductType));

                }
                recyclerView.setAdapter(new CategoryAdapter(getActivity() ,categoryList , CategoriesFragment.this ));
                //Navigation.findNavController(navView).navigate(R.id.navigation_home);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failll", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getActivity() , CategoryItems.class);
        intent.putExtra("Category" , categoryList.get(pos).getCategoryName());
        startActivity(intent);
    }
}