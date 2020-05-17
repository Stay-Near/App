package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.staynear.model.Room;
import com.example.staynear.model.RoomsListAdapter;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.UUID;

public class list_room extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);

        ListView listView = findViewById(R.id.listView);

        Room first = new Room(UUID.randomUUID().toString(),"Casa chida",30.00f,"GLD","1 baño","1","drawable://" + R.drawable.room1);
        Room second = new Room(UUID.randomUUID().toString(),"Depa fresco",15.00f,"QRO","1 cama","2","drawable://" + R.drawable.room2);
        Room third = new Room(UUID.randomUUID().toString(),"Caja de carton",10.00f,"CDMX","60x60","3","drawable://" + R.drawable.room3);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(first);
        rooms.add(second);
        rooms.add(third);

        RoomsListAdapter adapter = new RoomsListAdapter(this, R.layout.activity_rooms_list,rooms);
        // falta solucionar problema list view
        //listView.setAdapter(adapter);

        // Hocks
        drawerLayout = findViewById(R.id.drawe_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        // Tool bar
        setSupportActionBar(toolbar);

        // Navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

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
        return true;
    }
}
