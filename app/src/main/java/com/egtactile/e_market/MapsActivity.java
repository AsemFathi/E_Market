package com.egtactile.e_market;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egtactile.e_market.ui.home.HomeFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.egtactile.e_market.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.DescriptorProtos;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    ActionBar actionbar;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SupportMapFragment mapFragment;
    TextInputEditText addressText;
    TextInputEditText countrynameText;
    myLocationListener locationlistener ;
    LocationManager loc_manager;
    FirebaseUser user;
    String urldisplay;
    StorageReference storageReference;
    String email;
    static int Total_Price ;
    static List<items> itemsList=new ArrayList<items>();
    Button Confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityMapsBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

         ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
         Manifest.permission.ACCESS_FINE_LOCATION},PackageManager.PERMISSION_GRANTED);

        addressText = (TextInputEditText) findViewById(R.id.addresstxt);
        countrynameText = (TextInputEditText) findViewById(R.id.countryname);
        user= FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");

        Confirm = findViewById(R.id.Confirm_Shopping);

        locationlistener = new myLocationListener(MapsActivity.this) ;
        loc_manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            loc_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,locationlistener);
        }
        catch (SecurityException ex){
            Toast.makeText(MapsActivity.this, "YOU ARE NOT ALLOWED TO ACCESS THE CURRENT LOCATION", Toast.LENGTH_SHORT).show();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("Sold").child(email);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("Cart");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if (dataSnapshot.getKey().equals(email))
                            {
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
                                    DatabaseReference newData = reference.child(ProductName);
                                    newData.child("Category").setValue(ProductType);
                                    newData.child("Quantity").setValue(ProNum);
                                    newData.child("Price").setValue(ProPrice);
                                    newData.child("Picture").setValue(ProImageUrl);
                                    newData.child("Name").setValue(ProductName);
                                    newData.child("Description").setValue(des);
                                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                    newData.child("Date").setValue(date);
                                    Total_Price += Integer.parseInt(ProPrice) * Integer.parseInt(ProNum);
                                    itemsList.add(new items(ProPrice, ProductName, des, ProImageUrl, ProductType, ProNum));
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child(email).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(MapsActivity.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(MapsActivity.this , FeedbackActivity.class);
                startActivity(intent);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(30.04441960,31.235711600),20));
        mMap.clear();
        Geocoder coder=new Geocoder(MapsActivity.this);
        List<Address> addressList;
        Location Loc=null;
        try{
            Loc =loc_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch (SecurityException ex){
            Toast.makeText(getApplicationContext(), "YOU ARE NOT ALLOWED TO ACCESS THE CURRENT LOCATION", Toast.LENGTH_LONG).show();
        }
        if(Loc!=null) {
            LatLng myposition = new LatLng(Loc.getLatitude(), Loc.getLongitude());
            try {
                addressList = coder.getFromLocation(myposition.latitude, myposition.longitude, 1);
                if (!addressList.isEmpty()) {
                    String address = "";
                    for (int k = 0; k <= addressList.get(0).getMaxAddressLineIndex(); k++) {
                        address += addressList.get(0).getAddressLine(k) + ",";
                    }
                    mMap.addMarker(new MarkerOptions().position(myposition).title("My Location")).setDraggable(true);
                    addressText.setText(address);
                    countrynameText.setText(addressList.get(0).getCountryName());
                }
            } catch (IOException ex) {
                mMap.addMarker(new MarkerOptions().position(myposition).title("My Location"));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 20));
        }
        else{
            Toast.makeText(getApplicationContext(), "Wait untill your position eliminated", Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                Geocoder coder=new Geocoder(MapsActivity.this);
                List<Address> addressList;
                Location newLoc=null;
                try{
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty()){
                        String address="";
                        for(int k=0;k<=addressList.get(0).getMaxAddressLineIndex();k++){
                            address+=addressList.get(0).getAddressLine(k)+",";
                        }
                        addressText.setText(address);
                    }else{
                        //code
                        Toast.makeText(getApplicationContext(), "No address for this location", Toast.LENGTH_SHORT).show();
                        addressText.getText().clear();
                        countrynameText.setText(addressList.get(0).getCountryName());
                    }
                }catch(IOException ex){
                    //code
                    Toast.makeText(getApplicationContext(), "Canot get the address, check your network ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
            }
        });
    }
    /* private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }*/
    /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
            else {
                makeText(this,"Required Permission",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
    /*private void getCurrentLocation() {
       if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED)
       {
           fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
               @Override
               public void onSuccess(Location location) {
                   if(location!=null) {
                       Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                       List<Address> addresses = null;
                       try {
                           addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                           currentLocation.setLatitude(addresses.get(0).getLatitude());
                           currentLocation.setLatitude(addresses.get(0).getLongitude());

                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }
                   else {
                       askPermission();
                   }
               }
           });
       }

    }
    private void askPermission() {
        ActivityCompat.requestPermissions(MapsActivity.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode==REQUEST_CODE) {
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   getCurrentLocation();
               }
               else {
                   Toast.makeText(this,"Required Permission",Toast.LENGTH_SHORT).show();
               }
       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}