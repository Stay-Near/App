package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.staynear.model.Room;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Mapa extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        final LatLng guadalajara = new LatLng(20.6737777,-103.4054536);
        roomsRef= FirebaseDatabase.getInstance().getReference();
        roomsRef.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room cuarto = snapshot.getValue(Room.class);
                    //Toast.makeText(Mapa.this, cuarto.getTitle(),Toast.LENGTH_SHORT).show();
                    Log.e("Datos:",""+cuarto);
                    LatLng position = new LatLng(cuarto.getLat(),cuarto.getLng());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(cuarto.getTitle()));
                    marker.setTag(cuarto.getId());


                }
                mMap.setOnMarkerClickListener(Mapa.this);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guadalajara,13));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Add a marker in Sydney and move the camera

        /*LatLng cesar = new LatLng(20.7258657,-103.4499003);
        LatLng richie = new LatLng(20.6674648,-103.4063517);
        LatLng juanillo = new LatLng(20.7462259,-103.4390022);*/

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent toDescription = new Intent(Mapa.this,DescriptionRomm.class);
        Toast.makeText(this, marker.getTag().toString(),Toast.LENGTH_SHORT).show();
        Log.e("Id: ",marker.getTag().toString());
        toDescription.putExtra("id",marker.getTag().toString());
        startActivity(toDescription);

        return false;
    }
}