package com.example.applicationcv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.database.PersonData;

public class PersonalInformationActivity2 extends AppCompatActivity {
    private EditText first_and_last_name;
    private EditText number_of_identifications_passports;
    private EditText health_insurance_number;
    private EditText birthday;
    private EditText sex;
    private EditText nationality;
    private EditText current_address;
    private EditText phone_number;
    private EditText email;
    private TextView bt_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information2);
        first_and_last_name = findViewById(R.id.first_and_last_name);
        number_of_identifications_passports = findViewById(R.id.number_of_identifications_passports);
        health_insurance_number = findViewById(R.id.health_insurance_number);
        birthday = findViewById(R.id.birthday);
        sex = findViewById(R.id.sex);
        nationality = findViewById(R.id.nationality);
        current_address = findViewById(R.id.current_address);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);

        first_and_last_name.setText(PersonData.getFullName(this));
        number_of_identifications_passports.setText(PersonData.getCMND(this));
        health_insurance_number.setText(PersonData.getBHYT(this));
        birthday.setText(PersonData.getNgaySinh(this));
        sex.setText(PersonData.getGioiTinh(this));
        nationality.setText(PersonData.getQuocTich(this));
        current_address.setText(PersonData.getDiaChi(this));
        phone_number.setText(PersonData.getSDT(this));
        email.setText(PersonData.getEmail(this));

        bt_save = findViewById(R.id.bt_save);


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void testset() {
        PersonData.create(this, "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9");
    }

    private void testget() {
        String s = PersonData.getFullName(this);
        String s2 = PersonData.getCMND(this);
        String s3 = PersonData.getBHYT(this);
        String s4 = PersonData.getNgaySinh(this);
        String s5 = PersonData.getSDT(this);
        String s6 = PersonData.getDiaChi(this);
        String s7 = PersonData.getEmail(this);
        String s8 = PersonData.getGioiTinh(this);
        String s9 = PersonData.getQuocTich(this);
    }

    private void infor() {
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void webview() {
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void save() {
        PersonData.create(this,
                first_and_last_name.getText().toString(),
                number_of_identifications_passports.getText().toString(),
                health_insurance_number.getText().toString(),
                birthday.getText().toString(),
                sex.getText().toString(),
                nationality.getText().toString(),
                current_address.getText().toString(),
                phone_number.getText().toString(),
                email.getText().toString());
//        infor();

//        webview();
    }
}