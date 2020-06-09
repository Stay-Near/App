package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
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
    ArrayList<Room> temp = new ArrayList<>();
    ListView lv;
    String roomID;
    private DatabaseReference roomsRef;
    private RoomsListAdapter adapter;
    Spinner states_filter;
    SeekBar price_filter;
    String state_selected;
    double max_price;
   // private DatabaseReference ref;
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);



        //List view
        lv = findViewById(R.id.listView);
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
                    //cuarto.setPhoto("drawable://" + R.drawable.room1);
                    Log.e("Cuarto obtenido: ", cuarto.toString());
                    rooms.add(cuarto);
                    adapter.notifyDataSetChanged();
                }
                temp = rooms;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Spinner
        states_filter = findViewById(R.id.states_filter);

        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.states_filter, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states_filter.setAdapter(spinner_adapter);
        states_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state_selected = states_filter.getSelectedItem().toString();
                Log.e("State", state_selected);
                filter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("nada","nada");
            }
        });

        // Seekbar
        price_filter = findViewById(R.id.price_seekbar);
        max_price = 25 * price_filter.getProgress();
        Log.e("Starting values", "Seekbar: " + price_filter.getProgress() + " max price: " + max_price);
        price_filter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                max_price = 25 * price_filter.getProgress();
                Log.e("Progress",""+price_filter.getProgress() + " max price: " + max_price);
                filter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void openRoom(){
        Intent roomIntent = new Intent(this, DescriptionRomm.class);
        roomIntent.putExtra("id",roomID);
        startActivity(roomIntent);
    }

    private void filter(){
        Log.e("Temp", temp.toString());
        ArrayList<Room> matches = new ArrayList<>();
        ArrayList<Room> non_matches = new ArrayList<>();

        boolean within_range;
        for(Room room: rooms){
            within_range = Double.compare(room.getPrice(),max_price) < 0 || Double.compare(room.getPrice(),max_price) == 0;
            if(!state_selected.equals("All")){
                if(room.getLocation().equals(state_selected) && within_range){
                    Log.e("Price", max_price + " <= " + room.getPrice() + " = " + within_range );
                    matches.add(room);
                }
                else{
                    non_matches.add(room);
                }
            }else {
                    rooms = temp;
                    Log.e("Compare", room.getPrice() + " <= " + max_price + " ? " + within_range);
                    if (within_range) {
                        matches.add(room);
                    } else {
                        non_matches.add(room);
                    }

                }
        }
        rooms.removeAll(non_matches);
        adapter.notifyDataSetChanged();
        Log.e("Matched", rooms.toString());
    }


}
