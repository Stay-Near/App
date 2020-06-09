package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.staynear.model.Appointment;
import com.example.staynear.model.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentDescription extends AppCompatActivity {

    private TextView title, user, description, date;
    private CalendarView calendar;
    String titulo, currentUser, fecha, idRoom, idApp;
    String roomID, appoin;

    DatabaseReference roomReference, appointReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_description);

        currentUser = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();

        user = findViewById(R.id.user);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        user.setText(currentUser);

        Intent intent = getIntent();
        idRoom = intent.getStringExtra("roomID");
        idApp = intent.getStringExtra("idAppointment");

        appointReference = FirebaseDatabase.getInstance().getReference();

        appointReference.child("appointment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final Appointment cita = snapshot.getValue(Appointment.class);
                    appoin = cita.getId();
                    FirebaseDatabase.getInstance().getReference().child("room").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            for(DataSnapshot ss : dS.getChildren()){
                                Room room = ss.getValue(Room.class);
                                if(room.getId().equals(cita.getRoomID()) && cita.getClientID().equals(currentUser)){
                                    //Log.e("Pass", "Room " + room.getId() + " in appointment " + cita.getId() + " rented room " + cita.getRoomID() + " by " + cita.getClientID() + " currently log in as " + currentUser);
                                    titulo = room.getTitle();
                                    String des = room.getDescription();
                                    fecha = cita.getDate();

                                    title.setText(titulo);
                                    description.setText(des);
                                    //date.setText(fecha);
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

    public void openRating(View v){
        Intent historyIntent = new Intent(this, Rating.class);
        startActivity(historyIntent);
    }
}
