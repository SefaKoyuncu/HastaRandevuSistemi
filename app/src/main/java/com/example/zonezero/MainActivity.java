package com.example.zonezero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CheckBox checkBoxFemale, checkBoxMale;
    private RecyclerView recyclerView;
    private EditText editTextSearchDoctor;
    private DoctorsAdapter adapter;
    private TextView textViewNotFoundDoctor;
    private CardView cardViewRecyclerView,cardView;
    private ImageView imageViewNotFoundDoctor;
    String url = "https://www.mobillium.com/android/doctors.json";
    private ArrayList<Doctors> doctorsAllArrayList;
    private ArrayList<Doctors> doctorsArrayListMale;
    private ArrayList<Doctors> doctorsArrayListFemale;
    private ConstraintLayout clnouser;

    private ArrayList<Doctors> filterL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        checkBoxFemale = findViewById(R.id.checkBoxFemale);
        checkBoxMale = findViewById(R.id.checkBoxMale);
        recyclerView = findViewById(R.id.recyclerView);
        editTextSearchDoctor = findViewById(R.id.editTextSearchDoctor);
        textViewNotFoundDoctor = findViewById(R.id.textViewNotFoundDoctor);
        imageViewNotFoundDoctor = findViewById(R.id.imageViewNotFoundDoctor);
        cardViewRecyclerView = findViewById(R.id.cardViewRecyclerView);
        cardView = findViewById(R.id.cardView);
        clnouser = findViewById(R.id.clnouser);

        toolbar.setTitle("");

        doctorsAllArrayList = new ArrayList<>();
        doctorsArrayListMale = new ArrayList<>();
        doctorsArrayListFemale = new ArrayList<>();

        getData();
        buildRecyclerView(doctorsAllArrayList);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        checkBoxMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (TextUtils.isEmpty(editTextSearchDoctor.getText().toString())){
                    if (b==true){
                        checkBoxFemale.setChecked(false);
                        buildRecyclerView(doctorsArrayListMale);
                    }
                    else if(b==false && checkBoxFemale.isChecked()==false){
                        buildRecyclerView(doctorsAllArrayList);
                    }
                }
                else{
                    if (b==true){
                        filterL.clear();
                        checkBoxFemale.setChecked(false);
                        filter(editTextSearchDoctor.getText().toString(), doctorsArrayListMale);
                    }
                    else if(b==false && checkBoxFemale.isChecked()==false){
                        filterL.clear();
                        filter(editTextSearchDoctor.getText().toString(), doctorsAllArrayList);
                    }

                }

            }
        });
        checkBoxFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (TextUtils.isEmpty(editTextSearchDoctor.getText().toString())){
                    if (b==true){
                        checkBoxMale.setChecked(false);
                        Log.e("female", String.valueOf(doctorsArrayListFemale.size()));
                        buildRecyclerView(doctorsArrayListFemale);
                    }
                    else if(b==false && checkBoxMale.isChecked()==false){
                        Log.e("female", String.valueOf(doctorsAllArrayList.size()));
                        buildRecyclerView(doctorsAllArrayList);
                    }
                }
                else{
                    if (b==true){
                        filterL.clear();
                        checkBoxMale.setChecked(false);
                        filter(editTextSearchDoctor.getText().toString(), doctorsArrayListFemale);
                    }
                    else if(b==false && checkBoxMale.isChecked()==false){
                        filterL.clear();
                        filter(editTextSearchDoctor.getText().toString(), doctorsAllArrayList);
                    }
                }
            }
        });

        editTextSearchDoctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterL.clear();

                if (s.toString().isEmpty()){
                    cardViewRecyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if (checkBoxFemale.isChecked()==true){
                        buildRecyclerView(doctorsArrayListFemale);
                    }
                    if (checkBoxMale.isChecked()==true){
                        buildRecyclerView(doctorsArrayListMale);
                    }
                    if (checkBoxFemale.isChecked()==false && checkBoxMale.isChecked()==false){
                        buildRecyclerView(doctorsAllArrayList);
                    }

                }
                else if(checkBoxFemale.isChecked()==true){
                    filter(s.toString(), doctorsArrayListFemale);
                }
                else if(checkBoxMale.isChecked()==true){
                    filter(s.toString(), doctorsArrayListMale);
                }
                else{
                    filter(s.toString(), doctorsAllArrayList);
                }

            }
        });


    }

    public void getData(){

        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray doctorsListFromJSON = jsonObject.getJSONArray("doctors");

                    for (int i = 0; i<doctorsListFromJSON.length(); i++){
                        JSONObject doctor = doctorsListFromJSON.getJSONObject(i);
                        String full_name = doctor.getString("full_name");
                        String user_status = doctor.getString("user_status");
                        String gender = doctor.getString("gender");

                        JSONObject image = doctor.getJSONObject("image");
                        String urlImage = image.getString("url");

                        if (gender.equals("male") && !doctorsArrayListMale.contains(doctor)){
                            doctorsArrayListMale.add(new Doctors(full_name,user_status,gender,urlImage));
                        }
                        if (gender.equals("female") && !doctorsArrayListFemale.contains(doctor)){
                            doctorsArrayListFemale.add(new Doctors(full_name,user_status,gender,urlImage));
                        }

                        doctorsAllArrayList.add(new Doctors(full_name,user_status,gender,urlImage));
                        buildRecyclerView(doctorsAllArrayList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(istek);
    }

    public void buildRecyclerView(ArrayList<Doctors> doctorsListe){

        adapter = new DoctorsAdapter(doctorsListe, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private void filter(String text, ArrayList<Doctors> sendedList) {

        for (Doctors item : sendedList) {

            if (  checkBoxFemale.isChecked()==true && item.getGender().equals("female") && item.getFull_name().toLowerCase().contains(text.toLowerCase())) {
                    filterL.add(item);
            }

            if (  checkBoxMale.isChecked()==true && item.getGender().equals("male") && item.getFull_name().toLowerCase().contains(text.toLowerCase())) {
                    filterL.add(item);
            }

            if (checkBoxMale.isChecked()==false && checkBoxFemale.isChecked()==false && item.getFull_name().toLowerCase().contains(text.toLowerCase())){
                    filterL.add(item);
            }
        }

        if (filterL.size()==0){
            cardViewRecyclerView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            imageViewNotFoundDoctor.setVisibility(View.VISIBLE);
            clnouser.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            textViewNotFoundDoctor.setVisibility(View.VISIBLE);
        }

        else{
            imageViewNotFoundDoctor.setVisibility(View.INVISIBLE);
            clnouser.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.INVISIBLE);
            textViewNotFoundDoctor.setVisibility(View.INVISIBLE);
            cardViewRecyclerView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new DoctorsAdapter(filterL, this));
            adapter.notifyDataSetChanged();
        }


    }



}