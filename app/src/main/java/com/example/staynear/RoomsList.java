package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private DatabaseReference roomsRef;
   // private DatabaseReference ref;
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);

        ListView lv = findViewById(R.id.listView);

       Room first = new Room(UUID.randomUUID().toString(),"Casa chida",30.00f,"GLD","1 baño","1","drawable://" + R.drawable.room1,0.5f,0.5f);
        Log.e("Añadido: ", first.toString());
       // Room second = new Room(UUID.randomUUID().toString(),"Depa fresco",15.00f,"QRO","1 cama","2","drawable://" + R.drawable.room2,0.5f,0.5f);
       // Room third = new Room(UUID.randomUUID().toString(),"Caja de carton",10.00f,"CDMX","60x60","3","drawable://" + R.drawable.room3,0.5f,0.5f);


       rooms.add(first);
       // rooms.add(second);
       // rooms.add(third);

        //Query query = FirebaseDatabase.getInstance().getReference().child("room").orderByKey();
        //query.once("value")

        /*FirebaseDatabase.getInstance().getReference().child("room")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String id = snapshot.getValue(Room.class).getId();
                            String title = snapshot.getValue(Room.class).getTitle();
                            double price = snapshot.getValue(Room.class).getPrice();
                            String location = snapshot.getValue(Room.class).getLocation();
                            String description = snapshot.getValue(Room.class).getDescription();
                            String owner = snapshot.getValue(Room.class).getOwner();
                            String photo = "drawable://" + R.drawable.room1;
                            double lat = snapshot.getValue(Room.class).getLat();
                            double lng = snapshot.getValue(Room.class).getLng();
                           rooms.add(new Room(id,title,price,location,description,owner,photo,lat,lng));
                            //User user = snapshot.getValue(User.class);
                            //System.out.println(user.email);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/

        /*FirebaseDatabase.getInstance().getReference().child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room room = snapshot.getValue(Room.class);
                    Log.e("Datos:",""+room);
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        roomsRef= FirebaseDatabase.getInstance().getReference();
        roomsRef.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room cuarto = snapshot.getValue(Room.class);
                    cuarto.setPhoto("drawable://" + R.drawable.room1);
                    Log.e("Cuarto obtenido: ", cuarto.toString());
                    rooms.add(cuarto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RoomsListAdapter adapter = new RoomsListAdapter(this, R.layout.adapter_view_layout,rooms);
        lv.setAdapter(adapter);
    }
}
