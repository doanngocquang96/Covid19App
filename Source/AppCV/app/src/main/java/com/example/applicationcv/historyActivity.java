package com.example.applicationcv;

import static com.example.applicationcv.MainActivity2.getStateFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class historyActivity extends AppCompatActivity {

    private  RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  ArrayList<TrackerHealthy> listItem = new ArrayList<>();

    private int state;
    private TextView titleTracker;
    private String spKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history_activity);


        titleTracker = (TextView)findViewById(R.id.textViewTitle);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        state = getStateFragment();
        if(state == 0){
            spKey = "shared preferences private";
            titleTracker.setText("Theo dõi sức khỏe Cá nhân");
        } else {
            spKey = "shared preferences relate";
            titleTracker.setText("Theo dõi sức khỏe Người thân");
        }

        getArrayList();

        adapter = new TrackerHealthAdapter(this, listItem);
        recyclerView.setAdapter(adapter);

    }


    public void getArrayList(){
        SharedPreferences sharedPreferences = getSharedPreferences(spKey, MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("historyTracker", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<TrackerHealthy>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        listItem = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (listItem == null) {
            // if the array list is empty
            // creating a new array list.
            listItem = new ArrayList<>();
        }
    }
}
