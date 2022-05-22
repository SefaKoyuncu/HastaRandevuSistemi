package com.example.zonezero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DoctorDetails extends AppCompatActivity {

    private ImageView imageViewDoctorImage;
    private TextView textViewDoctorNameAndSurname, textViewDoctorStatus, textViewGet;
    private ConstraintLayout cLayoutDoctorDetails;
    private CardView cardViewDoctorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        cLayoutDoctorDetails = findViewById(R.id.cLayoutDoctorDetails);
        imageViewDoctorImage = findViewById(R.id.imageViewDoctorImage);
        textViewDoctorNameAndSurname = findViewById(R.id.textViewDoctorNameAndSurname);
        textViewDoctorStatus = findViewById(R.id.textViewDoctorStatus);
        textViewGet = findViewById(R.id.textViewGet);
        cardViewDoctorDetails = findViewById(R.id.cardViewDoctorDetails);

        String doctorImageUrl = getIntent().getStringExtra("doctorImageUrl");
        String doctorName = getIntent().getStringExtra("doctorName");
        String doctorStatus = getIntent().getStringExtra("doctorStatus");



        Picasso.with(this).load(doctorImageUrl).into(imageViewDoctorImage);
        textViewDoctorNameAndSurname.setText(doctorName);

        if (doctorStatus.equals("free")){
            textViewGet.setText("Premium Paket Al");
        }
        else{
            textViewGet.setText("Randevu Al");
            textViewDoctorStatus.setText("Premium");

        }

        cardViewDoctorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doctorStatus.equals("free")){
                    startActivity(new Intent(getApplicationContext(),PaymentScreen.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(),AppointmentScreen.class));
                }
            }
        });

    }
}