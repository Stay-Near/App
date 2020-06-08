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
import android.widget.Toolbar;

import com.example.staynear.model.Appointment;
import com.example.staynear.model.HistoryAdapter;
import com.example.staynear.model.Room;
import com.example.staynear.model.RoomsListAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class HistoryActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    String date;
    String roomID;
    Room cuarto;
    private DatabaseReference roomsRef;
    // private DatabaseReference ref;
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //List view
        ListView lv = findViewById(R.id.listViewHistory);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomID =  rooms.get(position).getId();
                Log.i("Click", "Elemento " + position + " id " + id + " room ID " + roomID);
                openRoom();
                //Log.i("Click", "Elemento " + position);
            }
        });

        Room first = new Room(UUID.randomUUID().toString(),"Casa que ya rentaste",30.00,"GLD","1 baño","1","drawable://" + R.drawable.room1,0.5f,0.5f);
        rooms.add(first);
        dates.add("11/07/2020");
        // Log.e("Añadido: ", first.toString());

        roomsRef= FirebaseDatabase.getInstance().getReference();

        roomsRef.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    cuarto = snapshot.getValue(Room.class);
                    /*FirebaseDatabase.getInstance().getReference().child("appointment").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ss : dataSnapshot.getChildren()){
                                Appointment cita = ss.getValue(Appointment.class);
                                if(cuarto.getId().equals(cita.getRoomID())){
                                    date = "si";
                                }
                                else{
                                    date = "no";
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                    Log.e("Cuarto obtenido: ", cuarto.toString());
                    rooms.add(cuarto);
                    dates.add("this");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.history_adapter_view,rooms,dates);
        //adapter.setDayScheduled("Nel");
        lv.setAdapter(adapter);
    }

    private void openRoom(){
        Intent roomIntent = new Intent(this, DescriptionRomm.class);
        roomIntent.putExtra("id",roomID);
        startActivity(roomIntent);
    }
}
