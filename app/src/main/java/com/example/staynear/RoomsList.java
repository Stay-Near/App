package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.staynear.model.Room;
import com.example.staynear.model.RoomsListAdapter;

import java.util.ArrayList;
import java.util.UUID;

public class RoomsList extends AppCompatActivity {
    // https://www.youtube.com/watch?v=cKUxiqNB5y0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);

        ListView lv = findViewById(R.id.listView);

        Room first = new Room(UUID.randomUUID().toString(),"Casa chida",30.00f,"GLD","1 baño","1","drawable://" + R.drawable.room1);
        Room second = new Room(UUID.randomUUID().toString(),"Depa fresco",15.00f,"QRO","1 cama","2","drawable://" + R.drawable.room2);
        Room third = new Room(UUID.randomUUID().toString(),"Caja de carton",10.00f,"CDMX","60x60","3","drawable://" + R.drawable.room3);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(first);
        rooms.add(second);
        rooms.add(third);

        RoomsListAdapter adapter = new RoomsListAdapter(this, R.layout.adapter_view_layout,rooms);
        lv.setAdapter(adapter);
    }
}
