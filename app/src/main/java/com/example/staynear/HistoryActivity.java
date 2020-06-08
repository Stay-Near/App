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
import com.google.firebase.auth.FirebaseAuth;
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
    private DatabaseReference appointmentsRef;
    private HistoryAdapter adapter;
    // private DatabaseReference ref;
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final String currentUser = ""+ FirebaseAuth.getInstance().getCurrentUser().getUid();

        //List view
        ListView lv = findViewById(R.id.listViewHistory);
        lv.setClickable(true);
        adapter = new HistoryAdapter(this, R.layout.history_adapter_view,rooms,dates);
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

        appointmentsRef= FirebaseDatabase.getInstance().getReference();

        appointmentsRef.child("appointment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final Appointment cita = snapshot.getValue(Appointment.class);
                    FirebaseDatabase.getInstance().getReference().child("room").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            for(DataSnapshot ss : dS.getChildren()){
                                Room room = ss.getValue(Room.class);
                                if(room.getId().equals(cita.getRoomID()) && cita.getClientID().equals(currentUser)){
                                    Log.e("Pass", "Room " + room.getId() + " in appointment " + cita.getId() + " rented room " + cita.getRoomID() + " by " + cita.getClientID() + " currently log in as " + currentUser);
                                    rooms.add(room);
                                    dates.add(cita.getDate());
                                    adapter.notifyDataSetChanged();
                                    Log.e("Rooms", rooms.toString());
                                    Log.e("Dates", dates.toString());
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Canceled","Se cancela todo en interno");
                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Canceled","Se cancela todo en externo");
            }
        });

    }

    private void openRoom(){
        Intent roomIntent = new Intent(this, DescriptionRomm.class);
        roomIntent.putExtra("id",roomID);
        startActivity(roomIntent);
    }
}
