package com.example.Quang_HomePage.SucKhoe;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applicationcv.MainActivity2;
import com.example.applicationcv.R;
import com.example.applicationcv.TrackerHealthy;
import com.example.applicationcv.historyActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kotlinx.coroutines.internal.ExceptionsConstuctorKt;


public class CaNhan_Fragment extends Fragment {
    private Button btnGuiBoYTe;
    private CheckBox cbHo, cbSot, cbKhoTho, cbDauMet, cbTot;
    private static int sot, ho, khotho, daumet, sktot;

    private String desc = "", pre = "Tình trạng sức khỏe: ";

    private static ArrayList<TrackerHealthy> listItem = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);

        cbHo = (CheckBox)view.findViewById(R.id.cb_ho);
        cbSot = (CheckBox)view.findViewById(R.id.cb_sot);
        cbKhoTho = (CheckBox)view.findViewById(R.id.cb_khotho);
        cbDauMet = (CheckBox) view.findViewById(R.id.cb_daunguoi);
        cbTot = (CheckBox)view.findViewById(R.id.cb_suckhoetot);

        getArrayList();
        GetStatistical();

        btnGuiBoYTe = (Button) view.findViewById(R.id.btn_guiboyte);

        btnGuiBoYTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGuiBoYTe.setEnabled(false);
                desc = desc + pre;


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                Log.i("time Save", currentDateandTime);

                if(cbSot.isChecked() || cbHo.isChecked() || cbDauMet.isChecked() || cbKhoTho.isChecked() || cbTot.isChecked()){
                    if(cbHo.isChecked()) {desc = desc + "Ho; "; ho = ho + 1;};
                    if(cbSot.isChecked()) {desc = desc + "Sốt; "; sot = sot + 1;};
                    if(cbKhoTho.isChecked()) {desc = desc + "Khó thở; "; khotho = khotho + 1;};
                    if(cbDauMet.isChecked()) {desc = desc + "Đau người, mệt mỏi; "; daumet = daumet + 1;};
                    if(cbTot.isChecked()) {desc = desc + "Sức khỏe tốt. "; sktot = sktot + 1;};
                    Toast.makeText(getActivity(), "Đã lưu thành công", Toast.LENGTH_SHORT).show();
                    addNewTrackerHealthy(0, "Cá Nhân", currentDateandTime, "", desc);
                    //save statistical
                    SaveStatistical(sot, ho, khotho, daumet, sktot);


                } else {
                    Toast.makeText(getActivity(), "Lỗi chưa có thông tin", Toast.LENGTH_SHORT).show();
                }

                desc = "";
                btnGuiBoYTe.setEnabled(true);


            }
        });

        return view;

    }

    public static void clearTempSave(){
        sot = 0; ho = 0; khotho = 0; daumet = 0; sktot = 0;
    }

    public static void clearTempListPrivate(){
        listItem = new ArrayList<>();
    }

    public void getArrayList(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences private", Context.MODE_PRIVATE);

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


    public void addNewTrackerHealthy(int iType, String iName, String iTime, String iRelate, String iDesc){

        TrackerHealthy temp = new TrackerHealthy(iType, iName, iTime, iRelate, iDesc);
        Log.i("tag", "addNewTrackerHealthy");
        listItem.add(temp);
        SaveArrayList();
    }

    public void SaveArrayList(){

        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences private", Context.MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(listItem);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("historyTracker", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

    }

    public void GetStatistical(){

        SharedPreferences statistical = getContext().getSharedPreferences("shared preferences statistical", Context.MODE_PRIVATE);
        sot = statistical.getInt("sot", 1); //0 is the default value
        ho = statistical.getInt("ho", 1);
        khotho = statistical.getInt("khotho", 1);
        daumet = statistical.getInt("daumet", 1);
        sktot = statistical.getInt("sktot", 1);
    }

    public void SaveStatistical(int iSot, int iHo, int iKhoTho, int iDauNguoi, int iSkTot){

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences statistical", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("sot", iSot);
        editor.putInt("ho", iHo);
        editor.putInt("khotho", iKhoTho);
        editor.putInt("daumet", iDauNguoi);
        editor.putInt("sktot", iSkTot);

        editor.apply();

        MainActivity2.GetStatistical(getContext());

    }




}
