package com.example.applicationcv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class infoUserActivity extends AppCompatActivity {

    private static final String IS_USER_INFO = "IsUserInfo";
    // User Session Manager Class
    UserSessionManager session;


    private Button btnLogout, btnEditAll;
    private TextView userName, userFullName, userBirth, userSex, userCMND, userBHYT, userAddress, userNational, userVaccine, userNumPhone, userEmail;
    private ImageView edt1, edt8, edt9, edt10;

    private  String vaccines[] = {"Chọn Vaccine đã tiêm","AZD1222 (AstraZeneca)","Sputnik-V (Gamalaya)","Vero-Cell (Sinopharm)","Comirnaty (Pfizer BioNtech)","Spikevax (Moderna)","Vaccine Janssen","Vaccine Hayat-Vax","Vaccine-Abdala"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infouser);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        userName = (TextView) findViewById(R.id.userName);
        userFullName = (TextView) findViewById(R.id.userFullName);
        userBirth = (TextView) findViewById(R.id.userBirth);
        userCMND = (TextView) findViewById(R.id.userCMDN);
        userBHYT = (TextView) findViewById(R.id.userBHYT);
        userSex = (TextView) findViewById(R.id.userSex);
        userNational = (TextView) findViewById(R.id.userNational);
        userAddress = (TextView) findViewById(R.id.userAddress);
        userVaccine = (TextView) findViewById(R.id.userVaccine);
        userNumPhone = (TextView) findViewById(R.id.userNumPhone);
        userEmail = (TextView) findViewById(R.id.userEmail);


        edt1 = (ImageView) findViewById(R.id.btn_edit1);
        edt8 = (ImageView) findViewById(R.id.btn_edit8);
        edt9 = (ImageView) findViewById(R.id.btn_edit9);
        edt10 = (ImageView) findViewById(R.id.btn_edit10);


        btnLogout = (Button) findViewById(R.id.btn_Logout);
        btnEditAll = (Button)findViewById(R.id.btn_editAll);


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get infos user from session
        String name = user.get(UserSessionManager.KEY_NAME);
        String email = user.get(UserSessionManager.KEY_EMAIL);
        String fullName = user.get(UserSessionManager.KEY_FULLNAME);
        String sex = user.get(UserSessionManager.KEY_SEX);
        String birth = user.get(UserSessionManager.KEY_BIRTH);
        String idCard = user.get(UserSessionManager.KEY_IDCARD);
        String idBHYT = user.get(UserSessionManager.KEY_IDBHYT);
        String national = user.get(UserSessionManager.KEY_NATIONAL);
        String address = user.get(UserSessionManager.KEY_ADDRESS);
        String numPhone = user.get(UserSessionManager.KEY_NUMPHONE);
        String vaccine = user.get(UserSessionManager.KEY_VACCINE);

        userName.setText(name);
        userFullName.setText(fullName);
        userBirth.setText(birth);
        userSex.setText(sex);
        userCMND.setText(idCard);
        userBHYT.setText(idBHYT);
        userNational.setText(national);
        userAddress.setText(address);
        userVaccine.setText(vaccine);
        userNumPhone.setText(numPhone);
        userEmail.setText(email);

        edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editName_layout = getLayoutInflater().inflate(R.layout.editname, null);

                MaterialEditText editName = (MaterialEditText) editName_layout.findViewById(R.id.editName);
                editName.setText(name);

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(editName_layout)
                        .setTitle("Edit Information Account")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                Log.d("tag", editName.getText().toString());
                                session.editor.putString("name", editName.getText().toString());
                                session.editor.commit();
                                userName.setText(editName.getText().toString());
                            }

                        });

                builder.show();
            }
        });

        edt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editAddress_layout = getLayoutInflater().inflate(R.layout.editaddress, null);

                MaterialEditText editAddress = (MaterialEditText) editAddress_layout.findViewById(R.id.editAddress);
                editAddress.setText(address);

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(editAddress_layout)
                        .setTitle("Edit Information Account")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.d("tag", editAddress.getText().toString());
                                session.editor.putString("address", editAddress.getText().toString());
                                session.editor.commit();
                                userAddress.setText(editAddress.getText().toString());
                            }

                        });

                builder.show();
            }
        });

        edt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editVaccine_layout = getLayoutInflater().inflate(R.layout.editvaccine, null);

                Spinner editVaccine = (Spinner) editVaccine_layout.findViewById(R.id.editVaccine);
                // Initialize an array adapter
                ArrayAdapter mAdapterVaccine = new ArrayAdapter<String>(infoUserActivity.this, android.R.layout.simple_spinner_dropdown_item, vaccines);
                // Data bind the spinner with array adapter items
                editVaccine.setAdapter(mAdapterVaccine);

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(editVaccine_layout)
                        .setTitle("Edit Information Account")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                Log.d("tag", editVaccine.getSelectedItem().toString());
                                session.editor.putString("vaccine", editVaccine.getSelectedItem().toString());
                                session.editor.commit();
                                userVaccine.setText(editVaccine.getSelectedItem().toString());
                            }

                        });

                builder.show();
            }
        });

        edt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editNumPhone_layout = getLayoutInflater().inflate(R.layout.editnumphone, null);

                MaterialEditText editNumPhone = (MaterialEditText) editNumPhone_layout.findViewById(R.id.editNumPhone);
                editNumPhone.setText(numPhone);

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(editNumPhone_layout)
                        .setTitle("Edit Information Account")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.d("tag", editNumPhone.getText().toString());
                                session.editor.putString("numPhone", editNumPhone.getText().toString());
                                session.editor.commit();
                                userNumPhone.setText(editNumPhone.getText().toString());
                            }

                        });

                builder.show();
            }
        });
        btnEditAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                View announce_layout = getLayoutInflater().inflate(R.layout.announment, null);

                TextView titleAnnounce = (TextView) announce_layout.findViewById(R.id.announceTitle);
                TextView descAnnounce = (TextView) announce_layout.findViewById(R.id.announceDesc);

                titleAnnounce.setText("You will be directed to edit all info and cant return back doing!");
                descAnnounce.setText("Make sure that your choose, please!");

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(announce_layout)
                        .setTitle("Announcement")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                session.editor.putBoolean(IS_USER_INFO, false);
                                session.editor.commit();
                                Intent ii = new Intent(getApplicationContext(), MainActivity2.class);
                                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(ii);
                                finish();

                            }

                        });

                builder.show();

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View announce_layout = getLayoutInflater().inflate(R.layout.announment, null);

                TextView titleAnnounce = (TextView) announce_layout.findViewById(R.id.announceTitle);
                TextView descAnnounce = (TextView) announce_layout.findViewById(R.id.announceDesc);

                titleAnnounce.setText("You will be Logout after press CONTINUE");
                descAnnounce.setText("Any session of you about account will be clear until next times login");

                AlertDialog.Builder builder = new AlertDialog.Builder(infoUserActivity.this);

                builder.setView(announce_layout)
                        .setTitle("Announcement")
                        .setIcon(R.drawable.ic_edit)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(loginActivity.this, MainActivity3.class);
//                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                session.logoutUser();

                            }

                        });

                builder.show();




            }
        });

    }
}
