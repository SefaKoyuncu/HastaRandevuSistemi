package com.kogo.hastarandevusistemi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    private ArrayList<Doctors> doctorsArrayList;
    private Context mContext;

    public DoctorsAdapter(ArrayList<Doctors> doctorsArrayList, Context mContext) {
        this.doctorsArrayList = doctorsArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Doctors doctor = doctorsArrayList.get(position);

        holder.textViewDoctorName.setText(doctor.getFull_name());
        String imageUrl = doctor.getUrlImage();

        Picasso.with(mContext).load(imageUrl).into(holder.imageViewDoctorPicture);
        holder.cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DoctorDetails.class);
                intent.putExtra("doctorImageUrl", doctor.getUrlImage());
                intent.putExtra("doctorName", doctor.getFull_name());
                intent.putExtra("doctorStatus", doctor.getUser_status());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewDoctorName;
        private ImageView imageViewDoctorPicture, imageViewRight;
        private ConstraintLayout cs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cs = itemView.findViewById(R.id.cs);
            textViewDoctorName = itemView.findViewById(R.id.textViewDoctorName);
            imageViewDoctorPicture = itemView.findViewById(R.id.imageViewDoctorPicture);
            imageViewRight = itemView.findViewById(R.id.imageViewRight);
        }
    }

}
