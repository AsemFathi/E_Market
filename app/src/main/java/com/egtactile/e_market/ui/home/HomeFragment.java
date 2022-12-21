package com.egtactile.e_market.ui.home;

import static android.content.ContentValues.TAG;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.egtactile.e_market.MyAdapter;
import com.egtactile.e_market.ProductDetails;
import com.egtactile.e_market.R;
import com.egtactile.e_market.RecyclerViewInterface;
import com.egtactile.e_market.databinding.FragmentHomeBinding;
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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements RecyclerViewInterface{

    private FragmentHomeBinding binding;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        itemsList = new ArrayList<items>();
        recyclerView = root.findViewById(R.id.recyclerView);

        searchText = root.findViewById(R.id.searchdata);
        searchView_btn = root.findViewById(R.id.search_btn);
        voice_search = root.findViewById(R.id.img_voice);
        qr_search = root.findViewById(R.id.img_scan);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));

       // email = this.getArguments().getString("email");
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Products");
        storageReference = FirebaseStorage.getInstance().getReference();
        //launch search by voice
        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData()!=null&&result.getResultCode() == getActivity().RESULT_OK) {
                            Intent data =result.getData();
                            if(data!=null)
                            {
                                //extract data
                                String Psearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                                searchText.setText(Psearch);
                            }

                        } else{
                            Toast.makeText(getActivity(),"Please, Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
                itemsList = new ArrayList<items>();
                for (DataSnapshot datax : snapshot.getChildren()) {
                    String ProductName = datax.getKey();
                    Log.i(TAG, "onDataChange: Name" + ProductName);
                    String ProductType = datax.child("Category").getValue().toString();
                    //String ProductName = datax.getKey();
                    String ProPrice = datax.child("Price").getValue().toString();
                    String ProNum = datax.child("Num").getValue().toString();
                    String ProImageUrl = datax.child("Picture").getValue().toString();
                    String des = datax.child("Description").getValue().toString();
                    Log.i(TAG, "onDataChange: Description" + des);
                    storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urldisplay = uri.toString();
                        }
                    });
                    info.add(ProPrice.toLowerCase());
                    info.add(ProNum.toLowerCase());
                    info.add(des.toLowerCase());
                    info.add(ProImageUrl.toLowerCase());

                    data.put(ProductName.toLowerCase(), info);
                    itemsList.add(new items(ProPrice, ProductName, des, ProImageUrl, ProductType, ProNum));
                    Log.i(TAG, "onDataChange: URL" + urldisplay);


                }
                recyclerView.setAdapter(new MyAdapter(getActivity(), itemsList, HomeFragment.this));
                //Navigation.findNavController(navView).navigate(R.id.navigation_home);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failll", Toast.LENGTH_SHORT).show();
            }

        });

        //search in data from firebase
        qr_search.setOnClickListener(v ->
        {
            scanCode();
        });
        voice_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Tell me the ptoduct you want!");
                activityResultLaunch.launch(intent);
            }
        });
        searchView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = searchText.getText().toString();
                Map<String , List<String>> searchData = new HashMap<>();
                List<String> searchList = new ArrayList<>();

                List<items> searchItemsList = new ArrayList<items>();
                DatabaseReference databaseReference1 = FirebaseDatabase
                        .getInstance().getReference().child("Products");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot datax: snapshot.getChildren())
                        {
                            String ProductName = datax.getKey();
                            String ProductType = datax.child("Category").getValue().toString();
                            if (ProductName.toLowerCase().contains(searchInput.toLowerCase()) ||
                                    ProductType.toLowerCase().contains(searchInput.toLowerCase()) ) {
                                String ProPrice = datax.child("Price").getValue().toString();
                                String ProNum = datax.child("Num").getValue().toString();
                                String ProImageUrl = datax.child("Picture").getValue().toString();
                                String des = datax.child("Description").getValue().toString();


                                Log.i(TAG, "onDataChange: Description" + des);
                                storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        urldisplay = uri.toString();
                                    }
                                });
                                searchList.add(ProPrice);
                                searchList.add(ProNum);
                                searchList.add(des);
                                searchList.add(ProImageUrl);
                                searchList.add(ProductType);
                                searchData.put(ProductName , searchList);
                                searchItemsList.add(new items(ProPrice , ProductName , des , ProImageUrl , ProductType , ProNum));
                                Log.i(TAG, "onDataChange: URL" + urldisplay);
                            }
                        }
                        itemsList = searchItemsList;
                        recyclerView.setAdapter(new MyAdapter(getActivity() , searchItemsList ,HomeFragment.this));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
               /* Log.i(TAG, "onClick 1: Done");
                //onStart();
                //Map<String , List<String>> searchData = new HashMap<>();
                String searchtext = searchText.getText().toString();
                itemsList.clear();
                List<String> information = new ArrayList<>();
                //Search by product Name
                if(data.containsKey(searchtext.toLowerCase()))
                {
                    // index 0 Price , index 1 Num , index 2 Image
                    information = data.get(searchtext.toLowerCase());
                    itemsList.add(new items(information.get(0) , searchtext
                            , information.get(2)) );


                }
                recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext() , itemsList));
*/
            }
        });
        return root;
    }
    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    //launch search by voice
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents()!=null)
        {
            searchText.setText(result.getContents());

        }
    });
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // open the item details when click on it
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


/*
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<items> options =
                new FirebaseRecyclerOptions.Builder<items>()
                        .setQuery(ref.orderByChild("Category").startAt(searchInput) , items.class)
                        .build();
        Log.i(TAG, "onStart: Done");
        FirebaseRecyclerAdapter<items ,MyNewHolder> adapter =
                new FirebaseRecyclerAdapter<items, MyNewHolder>(options){
                    @Override
                    protected void onBindViewHolder(MyNewHolder holder, int position,items model) {
                        Log.i(TAG, "onBindViewHolder 1: Done");
                        holder.Price.setText(model.getPrice());
                        holder.Name.setText(model.getName());
                        Log.i(TAG, "onBindViewHolder 2: Done");

                        Picasso.get()
                                .load(model.getImage())
                                .into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "onClick 2 : Done  ");
                                Intent intent = new Intent(Home.this , ProductDetails.class);
                                intent.putExtra("name" , model.getName());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MyNewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        Log.i(TAG, "onCreateViewHolder: Done");

                        final View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.fragment_home , parent , false);
                        MyNewHolder myNewHolder = new MyNewHolder(view);
                        Log.i(TAG, "onCreateViewHolder: Done 2");
                        return  myNewHolder;
                    }
                };
        Log.i(TAG, "onStart 3 :Doneeeeeeee ");
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "onStart: 4 : 4444444");
        adapter.startListening();


    }*/

}