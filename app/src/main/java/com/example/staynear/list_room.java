package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staynear.model.Room;
import com.example.staynear.model.RoomsListAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.UUID;

public class list_room extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView welcomeText;
    Toolbar toolbar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String currID, currName;

    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        inicializarFirebase();

        /*ListView listView = findViewById(R.id.listView);

        Room first = new Room(UUID.randomUUID().toString(),"Casa chida",30.00f,"GLD","1 baño","1","drawable://" + R.drawable.room1,0.5f,0.5f);
        Room second = new Room(UUID.randomUUID().toString(),"Depa fresco",15.00f,"QRO","1 cama","2","drawable://" + R.drawable.room2,0.5f,0.5f);
        Room third = new Room(UUID.randomUUID().toString(),"Caja de carton",10.00f,"CDMX","60x60","3","drawable://" + R.drawable.room3,0.5f,0.5f);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(first);
        rooms.add(second);
        rooms.add(third);

        RoomsListAdapter adapter = new RoomsListAdapter(this, R.layout.activity_rooms_list,rooms);*/
        // falta solucionar problema list view
        //listView.setAdapter(adapter);

        // Hocks
        drawerLayout = findViewById(R.id.drawe_layout);
        navigationView = findViewById(R.id.nav_view);
        welcomeText  = findViewById(R.id.tvTitle);
        toolbar = findViewById(R.id.toolbar);

        // Tool bar
        setSupportActionBar(toolbar);

        // Navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Main Menu layout
        currID = ""+ FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("user").child(currID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                welcomeText.setText("Welcome " + dataSnapshot.child("nombre").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Toast.makeText(this, "Selecciono el item: " + menuItem.toString(), Toast.LENGTH_LONG).show();
        if (menuItem.toString().equals("Maps")) {
            Intent mapsIntent = new Intent(this, Mapa.class);
            startActivity(mapsIntent);
            return true;
        } else if (menuItem.toString().equals("Rooms")) {
            Intent roomsIntent = new Intent(this, RoomsList.class);
            //roomsIntent.putExtra("id","228ae052-6cab-4551-9116-acf9b423e860");
            startActivity(roomsIntent);
            return true;
        } else if (menuItem.toString().equals("Profile")) {
            Intent profileIntent = new Intent(this, Profile.class);
            startActivity(profileIntent);
            return true;
        }else if (menuItem.toString().equals("Settings")){
            Intent settingsIntent = new Intent(this, uploadRoom.class);
            startActivity(settingsIntent);
            return true;
        }
        return false;
    }

    public void goToUploadRoom(View v){
        Intent settingsIntent = new Intent(this, uploadRoom.class);
        startActivity(settingsIntent);
    }

    public void openHistory(View v){
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        startActivity(historyIntent);
    }

    public void openRating(View v){
        Intent historyIntent = new Intent(this, Rating.class);
        startActivity(historyIntent);
    }
}
