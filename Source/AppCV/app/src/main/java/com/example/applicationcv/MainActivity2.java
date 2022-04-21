package com.example.applicationcv;

import static com.example.Quang_HomePage.SucKhoe.CaNhan_Fragment.clearTempListPrivate;
import static com.example.Quang_HomePage.SucKhoe.CaNhan_Fragment.clearTempSave;
import static com.example.Quang_HomePage.SucKhoe.NguoiThan_Fragment.clearTempListRelate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
//import android.graphics.Typeface;
import android.icu.number.FormattedNumber;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Quang_HomePage.News.MainActivity_News;
import com.example.Quang_HomePage.SucKhoe.CaNhan_Fragment;
import com.example.Quang_HomePage.SucKhoe.NguoiThan_Fragment;
import com.example.Quang_HomePage.ViewPagerAdapter2;
import com.example.applicationcv.Rotrofit.RetrofitClient;
import com.example.applicationcv.Rotrofit.Services;
import com.example.database.DataBase;
import com.example.database.PersonData;
import com.example.database.QRhistory;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity2 extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Services myService;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    private FrameLayout home;
    //private FrameLayout thermometer;
    private FrameLayout qr;
    private FrameLayout message;
    private FrameLayout menu;
    private FrameLayout declare;
    private FrameLayout scanner;
    private FrameLayout qr_tab;
    private RelativeLayout home_tab;
    private FrameLayout person_info;
    private FrameLayout terms_of_use;
    private FrameLayout policyapp;
    private FrameLayout aboutapp;
    private LinearLayout menu_tab;
    private FrameLayout answer;


    private LinearLayout phanhoi_tab;
    private ImageView qrcode;
    private TextView info, id_list;
    private WebView webview_tiemchung;

    //phan loc
    private Handler mainHandler = new Handler();
    private DatePickerDialog picker;
    private Button PhoneNum, btnsent;
    private ImageButton PhoneImg;
    private EditText timeOccour;

    private ArrayList<getInfoUser.Provinces> provincesList = new ArrayList<getInfoUser.Provinces>();
    private ArrayList<getInfoUser.Districts> districtsList = new ArrayList<getInfoUser.Districts>();
    private ArrayList<getInfoUser.Communces> communcesList = new ArrayList<getInfoUser.Communces>();
    private Spinner spProvinces,spDistrict, spCommune;

    private ArrayAdapter mAdapterProvinces;
    private ArrayAdapter mAdapterDistricts;
    private ArrayAdapter mAdapterCommunes;
    private ArrayList<String> adtProvinesList = new ArrayList<String>();
    private ArrayList<String> adtDistrictsList = new ArrayList<String>();
    private ArrayList<String> adtCommuncestList = new ArrayList<String>();
    private String codeProvince = "-1";
    private String codeDistrict = "-1";

    private CheckBox checkBox1, checkBox2, checkBox3, commit;
    private EditText AnotherReflect, place4;

    private JSONObject phanAnh = new JSONObject();
    private JSONArray tinhHinh = new JSONArray();;


    //phan quang
    private static String pre = "Bạn đang có vấn đề sức khỏe: ";
    private Button btnThegioi, btnNews, btnKbyt, btn_lichsu, btnVietnam, btn_refresh;
    private static TextView statusHealthy;
    TextView tongNhiemBenh;
    TextView nhiemBenhHomNay;
    TextView tongTuVong;
    TextView tuVongHomNay;
    TextView tongBinhPhuc;
    TextView binhPhucHomNay;
    String cases, todayCases, deaths, todayDeaths, recovered, todayRecovered;
    String casesW, todayCasesW, deathsW, todayDeathsW, recoveredW, todayRecoveredW;
    View view;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private static int state, sot, ho, khotho, daumet, sktot, total, staticHealthy;
    private static PieChart pieChart;
    private static ArrayList<PieEntry> pieEntries =  new ArrayList<>();
    private static PieData pieData = new PieData();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        }

        setContentView(R.layout.activity_main2);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        if(session.checkLogin())
            finish();

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(Services.class);

        home = findViewById(R.id.home);
        //thermometer = findViewById(R.id.thermometer);
        qr = findViewById(R.id.qr);
        phanhoi_tab = findViewById(R.id.phanhoi_tab);
        home_tab = findViewById(R.id.home_tab);
        message = findViewById(R.id.message);
        menu = findViewById(R.id.menu);
        qrcode = findViewById(R.id.qrcode);
        info = findViewById(R.id.info);
        declare = findViewById(R.id.declare);
        scanner = findViewById(R.id.scanner);
        qr_tab = findViewById(R.id.qr_tab);
        menu_tab = findViewById(R.id.menu_tab);

        person_info = findViewById(R.id.person_info);
        terms_of_use = findViewById(R.id.terms_of_use);
        aboutapp = findViewById(R.id.aboutapp);
        answer = findViewById(R.id.anwseraboutinfo);
        id_list = findViewById(R.id.id_list);
        policyapp = findViewById(R.id.policyapp);

        //phan loc ->

        checkBox1 = (CheckBox) findViewById(R.id.th1);
        checkBox2 = (CheckBox) findViewById(R.id.th2);
        checkBox3 = (CheckBox) findViewById(R.id.th3);
        commit = (CheckBox) findViewById(R.id.Agree);

        AnotherReflect = (EditText) findViewById(R.id.AnotherReflect);
        place4 = (EditText) findViewById(R.id.place4);


        PhoneNum = (Button) findViewById(R.id.PhoneNumber);
        PhoneImg = (ImageButton) findViewById(R.id.PhoneImage);
        btnsent = (Button) findViewById(R.id.sent);
        btnsent.setEnabled(false);


        timeOccour = (EditText) findViewById(R.id.timeOcc);
        timeOccour.setInputType(InputType.TYPE_NULL);
        timeOccour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        timeOccour.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();


            }
        });



        adtDistrictsList.clear();
        adtCommuncestList.clear();

        Log.i("msg", "start first fetching..");
        new fetchProvince().start();
        adtProvinesList.add("Chọn Tỉnh/TP");
        adtDistrictsList.add("Chọn Quận/Huyện");
        adtCommuncestList.add("Chọn Xã/Phườnng");

        spProvinces = (Spinner) findViewById(R.id.place1);
        spDistrict = (Spinner) findViewById(R.id.place2);
        spCommune = (Spinner) findViewById(R.id.place3);



        mAdapterProvinces = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,adtProvinesList);
        spProvinces.setAdapter(mAdapterProvinces);
        spProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the spinner selected item text
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                // Display the selected item
                Log.i("Select ", selectedItemText);

                for(int j = 0; j < provincesList.size(); j++) {
                    Log.i("msg", "Finding code Province..");
                    codeProvince = provincesList.get(j).findCodeByNameProvince(selectedItemText);
                    if(codeProvince != "-1"){
                        Log.i("Code province is ", codeProvince);
                        adtDistrictsList.clear();
                        new fetchDistrict().start();
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAdapterDistricts = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adtDistrictsList);
        spDistrict.setAdapter(mAdapterDistricts);


        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the spinner selected item text
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                // Display the selected item
                Log.i("Select ", selectedItemText);

                adtCommuncestList.clear();
                for(int j = 0; j < districtsList.size(); j++) {
                    Log.i("msg", "Finding code District..");
                    codeDistrict = districtsList.get(j).findCodeByNameDistrict(selectedItemText);
                    if(codeDistrict != "-1"){
                        Log.i("Code district is ", codeDistrict);

                        for(int k = 0; k < communcesList.size(); k++) {
                            Log.i("msg", "Finding name Commune..");
                            String nameCommune = "-1";
                            nameCommune = communcesList.get(k).findNameCommuneByCode(codeDistrict);

                            if(nameCommune != "-1"){
                                adtCommuncestList.add(nameCommune);
                            }
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAdapterCommunes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adtCommuncestList);
        spCommune.setAdapter(mAdapterCommunes);


        // <-
        //phan quang

        statusHealthy = (TextView) findViewById(R.id.statusHealthy);
        btn_refresh = (Button)findViewById(R.id.btn_refresh);
        btn_lichsu = findViewById(R.id.btn_lichsu);
        btnVietnam = findViewById(R.id.vietnam);
        btnThegioi = findViewById(R.id.thegioi);
        btnNews = findViewById(R.id.btn_covidnews);
        btnKbyt = (Button) findViewById(R.id.btn_kbyt);

        tongNhiemBenh = findViewById(R.id.txtTongNhiemBenh);
        nhiemBenhHomNay = findViewById(R.id.txtNhiemBenhHomNay);
        tongTuVong = findViewById(R.id.txtTongTuVong);
        tuVongHomNay = findViewById(R.id.txtTuVongHomNay);
        tongBinhPhuc = findViewById(R.id.txtTongBinhPhuc);
        binhPhucHomNay = findViewById(R.id.txtBinhPhucHomNay);

        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager_suckhoe);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        GetStatistical(this);



        getAPIVietNam();


        ViewPagerAdapter2 viewPagerAdapter2 = new ViewPagerAdapter2(this);
        viewPager2.setAdapter(viewPagerAdapter2);


        // Them 2 tab vao tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Cá nhân"));
        tabLayout.addTab(tabLayout.newTab().setText("Người thân"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tab onTabSelected", String.valueOf(tab.getPosition()));
                state = tab.getPosition();
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("tab onTabUnselected", String.valueOf(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("tab onTabReselected", String.valueOf(tab.getPosition()));
            }
        });


        btnVietnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVietnam.setBackgroundResource(R.drawable.grounded);
                btnThegioi.setBackgroundResource(R.drawable.thegioi2);
                getAPIVietNam();
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == 0){
                    //remove tracker private and session of statistical
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences private", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences statistical = getSharedPreferences("shared preferences statistical", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = statistical.edit();
                    editor1.clear();
                    editor1.commit();

                    clearTempSave();
                    clearTempListPrivate();

                    pre = "Bạn đang có vấn đề sức khỏe: ";
                    GetStatistical(MainActivity2.this);

                } else {
                    //remove tracker relate
                    clearTempListRelate();
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences relate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                }
            }
        });

        btn_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), historyActivity.class);
                startActivity(intent);
            }
        });

        btnThegioi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVietnam.setBackgroundResource(R.drawable.thegioi2);
                btnThegioi.setBackgroundResource(R.drawable.grounded);
                getAPIWorld();
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity_News.class);
                startActivity(intent);
            }
        });

        btnKbyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), kbytActivity.class);
                startActivity(intent);
            }
        });
        //


        // phan loc ->
        PhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:0907417373"));
                startActivity(in);
            }
        });

        PhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:0907417373"));
                startActivity(in);
            }
        });

        commit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if(spProvinces.getSelectedItem() != null && spDistrict.getSelectedItem() != null && spCommune.getSelectedItem() != null){
                        Log.i("open", "btnSendPhanAnh");
                        btnsent.setEnabled(true);
                    } else commit.setChecked(false);

                } else {
                    Log.i("close", "nobtnSendPhanAnh");
                    btnsent.setEnabled(false);
                }
            }
        });

        btnsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnsent.setEnabled(false);

                if(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked()){

                    if(checkBox1.isChecked()) tinhHinh.put("TiepXucNghiBenhHoacMacBenh");
                    if(checkBox2.isChecked()) tinhHinh.put("TiepXucDiTuVungDich");
                    if(checkBox3.isChecked()) tinhHinh.put("CoNguoiTuVungDich");

                    String address = place4.getText().toString()+ "/" + spCommune.getSelectedItem().toString() + ", " + spDistrict.getSelectedItem().toString() + ", " + spProvinces.getSelectedItem().toString();
                    //test here
                    Log.i("Address Phan anh", address);

                    try {
                        phanAnh.put("email", PersonData.getEmail(MainActivity2.this));
                        phanAnh.put("tinhhinh", tinhHinh);
                        phanAnh.put("timeOcc", timeOccour.getText().toString());
                        phanAnh.put("place", address);
                        phanAnh.put("content", AnotherReflect.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //printf
                    String jsonStr = phanAnh.toString();

                    System.out.println("jsonStringKBYT: " + jsonStr);


                    sendInfoPhaAnh(jsonStr);
                    phanAnh = new JSONObject();
                    tinhHinh = new JSONArray();

                } else {
                    btnsent.setEnabled(true);
                }

            }
        });
        // <-




        person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalInfor();
            }
        });

        policyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), policyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        terms_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Terms_of_use.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        aboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), aboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        id_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRListActivity.class);
                intent.putExtra("web", "https://tokhaiyte.vn/?page=login");
                intent.putExtra("title", "DANH SÁCH MÃ QR");
                startActivity(intent);
            }
        });

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FaqActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        declare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declare();
            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        setmenu();
        reload();
        home.performClick();
    }



    private void sendInfoPhaAnh(String phananh) {

        Log.i("prepare", "send phan anh to server");

        compositeDisposable.add(myService.sendPhananh(phananh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(String res) {

        btnsent.setEnabled(true);
        Toast.makeText(this, " " + "Đã gửi thành công!", Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable error) {

        btnsent.setEnabled(true);
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    class fetchProvince extends Thread{

        String data = "";

        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("msg", "Fetching provinces..waiting a moment");
                }
            });

            try {

                URL url = new URL("https://provinces.open-api.vn/api/p/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    data = data + line;

                }

                if(!data.isEmpty()){

                    JSONArray mJsonArray = new JSONArray(data);

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);

                        String provinceName = mJsonObject.getString("name");
                        String provinceCode = mJsonObject.getString("code");

                        getInfoUser.Provinces provinces = new getInfoUser.Provinces(provinceName, provinceCode);
                        provincesList.add(provinces);
                        adtProvinesList.add(provinceName);
                    }

                } else {
                    Log.i("district1", data);
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch ( IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("msg", "Fetch provinces success");
                    mAdapterProvinces.notifyDataSetChanged(); //start point after fetch finished
                }
            });

        }

    }

    class fetchDistrict extends Thread{
        String data = "";
        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("msg", "Fetching district..waiting a moment");
                }
            });

            try {

                URL url = new URL("https://provinces.open-api.vn/api/p/" + codeProvince + "?depth=3");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    data = data + line;

                }

                if(!data.isEmpty()){

                    JSONObject mJSONObject = new JSONObject(data);
                    JSONArray jsonArray = mJSONObject.getJSONArray("districts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String districtName = mJsonObject.getString("name");
                        String districtCode = mJsonObject.getString("code");

                        getInfoUser.Districts districts = new getInfoUser.Districts(districtName, districtCode);
                        districtsList.add(districts);
                        adtDistrictsList.add(districtName);

                        JSONArray mjsonArray = mJsonObject.getJSONArray("wards");
                        for (int j = 0; j < mjsonArray.length(); j++) {
                            JSONObject mjsonObject = mjsonArray.getJSONObject(j);
                            String communeName = mjsonObject.getString("name");
                            String communeCode = mjsonObject.getString("district_code");
                            getInfoUser.Communces communces = new getInfoUser.Communces(communeName, communeCode);
                            adtCommuncestList.add(communeName);
                            communcesList.add(communces);


                        }

                    }

                } else {
                    Log.i("province1", data);
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch ( IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("msg", "Fetch district success");
                    mAdapterDistricts.notifyDataSetChanged(); //start point after fetch finished
                }
            });

        }
    }

    //quang

    public static void GetStatistical(Context _context){

        SharedPreferences statistical = _context.getSharedPreferences("shared preferences statistical", Context.MODE_PRIVATE);
        sot =  statistical.getInt("sot", 1); //0 is the default value
        ho = statistical.getInt("ho", 1);
        khotho = statistical.getInt("khotho", 1);
        daumet = statistical.getInt("daumet", 1);
        sktot =  statistical.getInt("sktot", 1);

        total = sot + ho + khotho + daumet + sktot;
        //proccess data set state healthy
        float rate = (float)sktot/total;
        Log.i("test staticHealthy", String.valueOf((float)rate));

        if(rate >= 0.8f) {
            staticHealthy = 5;
            statusHealthy.setText("Bạn rất khỏe mạnh");
        };
        if(rate < 0.8f && rate >= 0.6f) staticHealthy = 4;
        if(rate < 0.6f && rate >= 0.4f) staticHealthy = 3;
        if(rate < 0.4f && rate >= 0.2f) staticHealthy = 2;
        if(rate < 0.2f) staticHealthy = 1;

        Log.i("test Sốt", String.valueOf((float) sot/total));
        Log.i("test Ho", String.valueOf((float)ho/total));
        Log.i("test Khó thở", String.valueOf((float)khotho/total));
        Log.i("test Đau mệt", String.valueOf((float)daumet/total));

        pre = "Bạn đang có vấn đề sức khỏe: ";

        if((float)sot/total >= 0.15f){
            pre = pre + " Sốt;";
            statusHealthy.setText(pre);
        }

        if((float)ho/total >= 0.15f){
            pre = pre + " Ho;";
            statusHealthy.setText(pre);
        }

        if((float)khotho/total >= 0.10f){
            pre = pre + " Khó thở;";
            statusHealthy.setText(pre);
        }

        if((float)daumet/total >= 0.15f) {
            pre = pre + " Đau mệt;";
            statusHealthy.setText(pre);
        }

        addDataPie();
    }

    public static void addDataPie(){

        pieEntries =  new ArrayList<>();
        pieEntries.add(new PieEntry(sktot, "SK tốt"));
        pieEntries.add(new PieEntry(sot, "Sốt"));
        pieEntries.add(new PieEntry(ho, "Ho"));
        pieEntries.add(new PieEntry(khotho, "Khó thở"));
        pieEntries.add(new PieEntry(daumet, "Đau mệt"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries," \t\t\tcreate by five group");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueFormatter(new LargeValueFormatter());
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(16);


        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);


        switch (staticHealthy){
            case 1: {
                pieChart.setCenterText("VERYBAD");
                pieChart.setCenterTextColor(ColorTemplate.rgb("#ff0000"));
                break;
            }
            case 2: {
                pieChart.setCenterText("BAD"); //orange #FFECB205
                pieChart.setCenterTextColor(ColorTemplate.rgb("#FFECB205"));
                break;
            }
            case 3: {
                pieChart.setCenterText("MEDIUM");
                pieChart.setCenterTextColor(ColorTemplate.rgb("#03A9F4"));
                break;
            }
            case 4: {
                pieChart.setCenterText("GOOD"); //greenlight #B6ED35
                pieChart.setCenterTextColor(ColorTemplate.rgb("#B6ED35"));
                break;
            }
            case 5: {
                pieChart.setCenterText("VERYGOOD"); // green #50C51F
                pieChart.setCenterTextColor(ColorTemplate.rgb("#50C51F"));
                break;
            }
        }


        pieChart.setCenterTextSize(14f);

        pieChart.animateXY(1500,1500);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(ColorTemplate.rgb("#03A9F4"));
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDescription(null);
        pieChart.invalidate();
    }



    public static int getStateFragment(){
        Log.d("state ", String.valueOf(state));
        return state;
    }

    private void getAPIVietNam(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/countries/vietnam";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cases = response.getString("cases");
                            todayCases = (response.getString("todayCases"));
                            deaths = response.getString("deaths");
                            todayDeaths = response.getString("todayDeaths");
                            recovered = response.getString("recovered");
                            todayRecovered = response.getString("todayRecovered");

                            tongNhiemBenh.setText(String.format("%,d", Integer.parseInt(cases)));  // dinh dang 123,456
                            nhiemBenhHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayCases)));
                            tongTuVong.setText(String.format("%,d", Integer.parseInt(deaths)));
                            tuVongHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayDeaths)));
                            tongBinhPhuc.setText(String.format("%,d", Integer.parseInt(recovered)));
                            binhPhucHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayRecovered)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void getAPIWorld(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/all";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            casesW = response.getString("cases");
                            todayCasesW = (response.getString("todayCases"));
                            deathsW = response.getString("deaths");
                            todayDeathsW = response.getString("todayDeaths");
                            recoveredW = response.getString("recovered");
                            todayRecoveredW = response.getString("todayRecovered");

                            tongNhiemBenh.setText(String.format("%,d", Integer.parseInt(casesW)));      // dinh dang 123,456
                            nhiemBenhHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayCasesW)));
                            tongTuVong.setText(String.format("%,d", Integer.parseInt(deathsW)));
                            tuVongHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayDeathsW)));
                            tongBinhPhuc.setText(String.format("%,d", Integer.parseInt(recoveredW)));
                            binhPhucHomNay.setText("+ " + String.format("%,d", Integer.parseInt(todayRecoveredW)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    //


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void reload() {

        String infoUser = PersonData.getFullName(this) + "\n" + PersonData.getSDT(this) + "\n" + PersonData.getEmail(this);
        info.setText(infoUser);

        try {
            qrcode.setImageBitmap(generateQRCodeImage(infoUser, dpToPixels(170, this), dpToPixels(170, this)));
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Activity getActivity() {
        return this;
    }

    class JavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            System.out.println(html);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Bitmap generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

//        Path path = FileSystems.getDefault().getPath(filePath);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return bitmatToBitmap(bitMatrix);
    }

    private Bitmap bitmatToBitmap(BitMatrix bitMatrix) {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    private void personalInfor() {

        Intent intent = new Intent(getActivity(), infoUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void declare() {
        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        intent.putExtra("web", "https://tokhaiyte.vn/?page=login");
        intent.putExtra("title", "KHAI BÁO Y TẾ");
        this.startActivity(intent);
    }

    public int dpToPixels(int i, Activity context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = ((int) (i * scale + 0.5f));
        return pixels;
    }

    private void scan() {
        IntentIntegrator qrScan;
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, data);
        Log.i("tag", "here");
        if (result != null) {
            if (result.getContents() == null) {
                Toast toast = Toast.makeText(MainActivity2.this, "Scan Have An Error!", Toast.LENGTH_SHORT);
                toast.show();
                //scan have an error
            } else {
                //scan is successful
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());
                if (!QRhistory.insert(this, currentDateandTime, result.getContents())) {
                    if (QRhistory.create(this)) {
                        Log.i("tag", result.getContents());
                        QRhistory.insert(this, currentDateandTime, result.getContents());
                        Toast toast = Toast.makeText(MainActivity2.this, result.getContents(), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(MainActivity2.this, "Save Have An Error!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                Toast toast = Toast.makeText(MainActivity2.this, "Create new record!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setmenu() {

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundResource(R.drawable.sharp_border_oval_enable);
                ((TextView) home.getChildAt(0)).setBackgroundResource(R.drawable.ic_home_enable);
                qr.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) qr.getChildAt(0)).setBackgroundResource(R.drawable.ic_qr_disable);
                message.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) message.getChildAt(0)).setBackgroundResource(R.drawable.ic_message_disable);
                menu.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) menu.getChildAt(0)).setBackgroundResource(R.drawable.ic_menu_disable);

                qr_tab.setVisibility(View.GONE);
                menu_tab.setVisibility(View.GONE);
                phanhoi_tab.setVisibility(View.GONE);
                home_tab.setVisibility(View.VISIBLE);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                home.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) home.getChildAt(0)).setBackgroundResource(R.drawable.ic_home_disable);
                qr.setBackgroundResource(R.drawable.sharp_border_oval_enable);
                ((TextView) qr.getChildAt(0)).setBackgroundResource(R.drawable.ic_qr_enable);
                message.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) message.getChildAt(0)).setBackgroundResource(R.drawable.ic_message_disable);
                menu.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) menu.getChildAt(0)).setBackgroundResource(R.drawable.ic_menu_disable);

                reload();
                qr_tab.setVisibility(View.VISIBLE);
                menu_tab.setVisibility(View.GONE);
                phanhoi_tab.setVisibility(View.GONE);
                home_tab.setVisibility(View.GONE);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) home.getChildAt(0)).setBackgroundResource(R.drawable.ic_home_disable);
                qr.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) qr.getChildAt(0)).setBackgroundResource(R.drawable.ic_qr_disable);
                message.setBackgroundResource(R.drawable.sharp_border_oval_enable);
                ((TextView) message.getChildAt(0)).setBackgroundResource(R.drawable.ic_message_enable);
                menu.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) menu.getChildAt(0)).setBackgroundResource(R.drawable.ic_menu_disable);

                qr_tab.setVisibility(View.GONE);
                menu_tab.setVisibility(View.GONE);
                phanhoi_tab.setVisibility(View.VISIBLE);
                home_tab.setVisibility(View.GONE);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) home.getChildAt(0)).setBackgroundResource(R.drawable.ic_home_disable);
//                thermometer.setBackgroundResource(R.drawable.sharp_border_oval_disable);
//                ((TextView) thermometer.getChildAt(0)).setBackgroundResource(R.drawable.ic_thermometer_disable);
                qr.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) qr.getChildAt(0)).setBackgroundResource(R.drawable.ic_qr_disable);
                message.setBackgroundResource(R.drawable.sharp_border_oval_disable);
                ((TextView) message.getChildAt(0)).setBackgroundResource(R.drawable.ic_message_disable);
                menu.setBackgroundResource(R.drawable.sharp_border_oval_enable);
                ((TextView) menu.getChildAt(0)).setBackgroundResource(R.drawable.ic_menu_enable);

                qr_tab.setVisibility(View.GONE);
                menu_tab.setVisibility(View.VISIBLE);
                phanhoi_tab.setVisibility(View.GONE);
                home_tab.setVisibility(View.GONE);
            }
        });

    }
}