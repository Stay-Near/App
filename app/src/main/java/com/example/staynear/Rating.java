package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Rating extends AppCompatActivity {

    TextView titlerate, resultrate;
    Button btfeedback;
    ImageView happy;
    RatingBar rateStars;
    String answerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        titlerate = findViewById(R.id.titlerate);
        resultrate = findViewById(R.id.resultrate);

        btfeedback = findViewById(R.id.btfeedback);

        happy = findViewById(R.id.happy);

        rateStars = findViewById(R.id.rateStars);

        // Condition
        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                answerValue = String.valueOf((int) (rateStars.getRating()));
                if (answerValue.equals("1")) {
                    happy.setImageResource(R.drawable.triste);
                    resultrate.setText("BUUUU");
                }
                else if (answerValue.equals("2")) {
                    happy.setImageResource(R.drawable.triste);
                    resultrate.setText("Triste");
                }
                else if (answerValue.equals("3")) {
                    happy.setImageResource(R.drawable.normal);
                    resultrate.setText("Mas o Menos");
                }
                else if (answerValue.equals("4")) {
                    happy.setImageResource(R.drawable.normal);
                    resultrate.setText("Se esta poniendo bueno");
                }
                else if (answerValue.equals("5")) {
                    happy.setImageResource(R.drawable.feliz);
                    resultrate.setText("WUWUWUW");
                }
                else {
                    Toast.makeText(Rating.this, "No Point", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
