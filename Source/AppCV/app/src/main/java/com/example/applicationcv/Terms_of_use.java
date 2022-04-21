package com.example.applicationcv;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import com.example.applicationcv.MyAdapter;
import com.example.applicationcv.ListItem;

public class Terms_of_use extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItem;
    int images[] = {R.drawable.astrazaneca, R.drawable.sinovac, R.drawable.pfizer};


    private DrawerLayout mDrawerLayout;
    private TextView mToolbar;
    //SessionManager mSession;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } //To avoid closing the app on back Pressed Button once the drawer display



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        }

        setContentView(R.layout.terms_of_use);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = (TextView)findViewById(R.id.toolbar);

        //use ds tool bar as action bar
//        setSupportActionBar(mToolbar);

        //Navigation Drawer Menu -- Handle the open and close and selection
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItem = new ArrayList<>();
        ListItem item1 = new ListItem("AstraZeneca", getString(R.string.astrazeneca));
        ListItem item2 = new ListItem("Sinovac-Coronavac", getString(R.string.sinovac));
        ListItem item3 = new ListItem("Pfizer-BioNTech", getString(R.string.pfizer));

        //Insert into List
        listItem.add(item1);
        listItem.add(item2);
        listItem.add(item3);

        adapter = new MyAdapter(this, listItem, images);
        recyclerView.setAdapter(adapter);
    }



    public void callNow(View view) {
        try{
            String phone_number = "19009095";
            Intent call_intent = new Intent(Intent.ACTION_DIAL);
            call_intent.setData(Uri.parse("tel:" + phone_number));
            startActivity(call_intent);
        }catch(Exception e){
        }
    }
}