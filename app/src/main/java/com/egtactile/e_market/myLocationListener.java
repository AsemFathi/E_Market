package com.egtactile.e_market;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.provider.Settings;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class myLocationListener implements LocationListener {

    private Context activitycontext;
    public myLocationListener(Context cont){
        activitycontext =cont;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(activitycontext,location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(activitycontext,"GPS enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(activitycontext,"GPS disabled",Toast.LENGTH_LONG).show();
         AlertDialog alertDialog = new AlertDialog.Builder(activitycontext)
                 .setTitle("GPS Permission")
                 .setMessage("GPS is required for this app to work. Please enable GPS")
                 .setPositiveButton("Yes",((dialog, which) -> {
                     Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                     activitycontext.startActivity(intent);
                 }))
                 .setCancelable(false)
                 .show();
    }

}
