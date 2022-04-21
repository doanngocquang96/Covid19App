package com.example.applicationcv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.applicationcv.Rotrofit.RetrofitClient;
import com.example.applicationcv.Rotrofit.Services;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class kbytActivity extends AppCompatActivity {

    private MaterialEditText editDeclareAbroad, editDeclareOther;
    private CheckBox checkBoxHo, checkBoxSot, checkBoxKhoTho,
            checkBoxViemPhoi, checkBoxDauHong, checkBoxMetMoi,
            checkBoxTiepXucNghiNgo, checkBoxTiepXucNuocNgoai, checkBoxTiepXucBieuHien,
            checkBoxBenhGan,  checkBoxBenhPhoi,  checkBoxBenhMau,  checkBoxBenhThan,  checkBoxBenhUngThu;
    private Button btnDeclare;
    private ImageView btn_backHome;
    private JSONObject kbyt = new JSONObject();
    private JSONArray trieuChung = new JSONArray();
    private JSONArray tiepXuc = new JSONArray();
    private JSONArray benhNen = new JSONArray();
    private JSONArray kBaoKhac = new JSONArray();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Services myService;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kbyt);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(Services.class);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        btnDeclare = (Button) findViewById(R.id.btnDeclare);
        btn_backHome = (ImageView) findViewById(R.id.btn_backHome);

        editDeclareAbroad = (MaterialEditText) findViewById(R.id.editDeclareAbroad);
        editDeclareOther = (MaterialEditText) findViewById(R.id.editDeclareOther);

        checkBoxHo = (CheckBox) findViewById(R.id.checkBoxHo);
        checkBoxSot = (CheckBox) findViewById(R.id.checkBoxSot);
        checkBoxKhoTho = (CheckBox) findViewById(R.id.checkBoxKhoTho);
        checkBoxViemPhoi = (CheckBox) findViewById(R.id.checkViemPhoi);
        checkBoxDauHong = (CheckBox) findViewById(R.id.checkBoxDauHong);
        checkBoxMetMoi = (CheckBox) findViewById(R.id.checkBoxMetMoi);

        checkBoxTiepXucNghiNgo = (CheckBox) findViewById(R.id.checkBoxTiepXuc1);
        checkBoxTiepXucNuocNgoai = (CheckBox) findViewById(R.id.checkBoxTiepXuc2);
        checkBoxTiepXucBieuHien = (CheckBox) findViewById(R.id.checkBoxTiepXuc3);

        checkBoxBenhGan = (CheckBox) findViewById(R.id.checkBoxBenhNen1);
        checkBoxBenhMau = (CheckBox) findViewById(R.id.checkBoxBenhNen2);
        checkBoxBenhPhoi = (CheckBox) findViewById(R.id.checkBoxBenhNen3);
        checkBoxBenhThan = (CheckBox) findViewById(R.id.checkBoxBenhNen4);
        checkBoxBenhUngThu = (CheckBox) findViewById(R.id.checkBoxBenhNen5);


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        btnDeclare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxHo.isChecked()) trieuChung.put("Ho");
                if(checkBoxSot.isChecked()) trieuChung.put("Sot");
                if(checkBoxKhoTho.isChecked()) trieuChung.put("KhoTho");
                if(checkBoxViemPhoi.isChecked()) trieuChung.put("ViemPhoi");
                if(checkBoxDauHong.isChecked()) trieuChung.put("DauHong");
                if(checkBoxMetMoi.isChecked()) trieuChung.put("MetMoi");

                if(checkBoxTiepXucNghiNgo.isChecked()) tiepXuc.put("TiepXucNghiNgo");
                if(checkBoxTiepXucNuocNgoai.isChecked()) tiepXuc.put("TiepXucNuocNgoai");
                if(checkBoxTiepXucBieuHien.isChecked()) tiepXuc.put("TiepXucBieuHien");

                if(checkBoxBenhGan.isChecked()) benhNen.put("BenhGan");
                if(checkBoxBenhMau.isChecked()) benhNen.put("BenhMau");
                if(checkBoxBenhPhoi.isChecked()) benhNen.put("BenhPhoi");
                if(checkBoxBenhThan.isChecked()) benhNen.put("benhThan");
                if(checkBoxBenhUngThu.isChecked()) benhNen.put("UngThu");

                if(!editDeclareOther.getText().toString().isEmpty()) kBaoKhac.put(editDeclareOther.getText().toString());
                if(!editDeclareAbroad.getText().toString().isEmpty()) kBaoKhac.put(editDeclareAbroad.getText().toString());

                try {
                    kbyt.put("email", email);
                    kbyt.put("trieuchung", trieuChung);
                    kbyt.put("tiepxuc", tiepXuc);
                    kbyt.put("benhnen", benhNen);
                    kbyt.put("kBaoKhac", kBaoKhac);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //printf
                String jsonStr = kbyt.toString();

                System.out.println("jsonStringKBYT: " + jsonStr);

                sendInfoKBYT(jsonStr);

                trieuChung = new JSONArray();
                tiepXuc = new JSONArray();
                benhNen = new JSONArray();
                kBaoKhac = new JSONArray();

             }
        });

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }
        });



    }

    private void sendInfoKBYT(String kbyt) {

        compositeDisposable.add(myService.sendKbyt(kbyt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(String res) {

        Toast.makeText(this, " " + "Well, Declared!", Toast.LENGTH_SHORT).show();
    }


    private void handleError(Throwable error) {

        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}