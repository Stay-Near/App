package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.staynear.model.Room;
import com.example.staynear.model.RoomsListAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class RoomsList extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ArrayList<Room> rooms = new ArrayList<>();
    String roomID;
    private DatabaseReference roomsRef;
    private RoomsListAdapter adapter;
   // private DatabaseReference ref;
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);

        //List view
        ListView lv = findViewById(R.id.listView);
        lv.setClickable(true);
        adapter = new RoomsListAdapter(this, R.layout.adapter_view_layout,rooms);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomID =  rooms.get(position).getId();
                Log.i("Click", "Elemento " + position + " id " + id + " room ID " + roomID);
                openRoom();
                //Log.i("Click", "Elemento " + position);
            }
        });


        roomsRef= FirebaseDatabase.getInstance().getReference();
        roomsRef.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room cuarto = snapshot.getValue(Room.class);
                    cuarto.setPrice(cuarto.getPrice());
                    //cuarto.setPhoto("drawable://" + R.drawable.room1);
                    Log.e("Cuarto obtenido: ", cuarto.toString());
                    rooms.add(cuarto);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openRoom(){
        Intent roomIntent = new Intent(this, DescriptionRomm.class);
        roomIntent.putExtra("id",roomID);
        startActivity(roomIntent);
    }
}
