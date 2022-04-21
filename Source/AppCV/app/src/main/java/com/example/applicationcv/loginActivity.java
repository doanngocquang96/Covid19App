package com.example.applicationcv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationcv.Rotrofit.RetrofitClient;
import com.example.applicationcv.Rotrofit.Services;
import com.example.database.PersonData;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class loginActivity extends AppCompatActivity {

    UserSessionManager session;

    TextView txt_create_account;
    MaterialEditText edit_login_email, edit_login_password;
    Button btn_login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Services myService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(Services.class);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());


        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        //Init view
        edit_login_email = (MaterialEditText)findViewById(R.id.editEmail);
        edit_login_password = (MaterialEditText)findViewById(R.id.editPassword);

        //enable btn login before fill all info

        btn_login = (Button)findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login.setEnabled(false);
                Log.i("tag", "here");
                loginUser(edit_login_email.getText().toString(), edit_login_password.getText().toString());

//                String s1;
//                s1 =edit_login_email.getText().toString();
//                s1 =edit_login_password.getText().toString();
//                s1 =LoginData.Email(LoginRegisterActivity.this);
//                s1 =LoginData.getPassWord(LoginRegisterActivity.this);
//                if (edit_login_email.getText().toString().equalsIgnoreCase(LoginData.Email(LoginRegisterActivity.this)) && edit_login_password.getText().toString().equalsIgnoreCase(LoginData.getPassWord(LoginRegisterActivity.this))) {
//                    maninmenu();
//                }

            }

        });

        txt_create_account = (TextView) findViewById(R.id.txtCreateAccount);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View register_layout = getLayoutInflater().inflate(R.layout.register_layout, null);
                MaterialEditText edit_register_email = (MaterialEditText)register_layout.findViewById(R.id.editEmail);
                MaterialEditText edit_register_name = (MaterialEditText)register_layout.findViewById(R.id.editName);
                MaterialEditText edit_register_password = (MaterialEditText)register_layout.findViewById(R.id.editPassword);
                MaterialEditText edit_register_rePassword = (MaterialEditText)register_layout.findViewById(R.id.editRePassword);


                TextView openPolicy = (TextView) register_layout.findViewById(R.id.policyapp);

                openPolicy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(loginActivity.this, policyActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);

                builder.setView(register_layout)
                        .setTitle("Registration")
                        .setIcon(R.drawable.ic_userplus)
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

                                if(TextUtils.isEmpty(edit_register_email.getText().toString())){
                                    edit_register_email.setError("Cannot empty this field");
                                    edit_register_email.requestFocus();
                                    Toast toast =  Toast.makeText(loginActivity.this, "email error, try again", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else if(!(edit_register_email.getText().toString().matches(emailPattern))){
                                    edit_register_email.setError("Email ís not valid");
                                    edit_register_email.requestFocus();
                                    Toast toast =  Toast.makeText(loginActivity.this, "email error, try again", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else if(TextUtils.isEmpty(edit_register_name.getText().toString())){
                                    edit_register_name.setError("Cannot empty this field");
                                    edit_register_name.requestFocus();
                                    Toast toast =  Toast.makeText(loginActivity.this, "password error, try again", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else if(TextUtils.isEmpty(edit_register_password.getText().toString())){
                                    edit_register_password.setError("Cannot empty this field");
                                    edit_register_password.requestFocus();
                                    Toast toast =  Toast.makeText(loginActivity.this, "password error, try again", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else if(TextUtils.isEmpty(edit_register_rePassword.getText().toString())){
                                    edit_register_rePassword.setError("Cannot empty this field");
                                    edit_register_rePassword.requestFocus();
                                }
                                else if(edit_register_password.getText().toString().compareTo(edit_register_rePassword.getText().toString()) != 0){
                                    edit_register_rePassword.setError("Confirm is not match");
                                    edit_register_rePassword.requestFocus();
                                    Toast toast =  Toast.makeText(loginActivity.this, "password not match, try again", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else{
                                    registerUser(edit_register_email.getText().toString(),
                                            edit_register_name.getText().toString(),
                                            edit_register_password.getText().toString());
                                }
                            }

                        });
                builder.show();

            }

        });


    }



    private void registerUser(String email, String name, String password) {

        compositeDisposable.add(myService.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse,this::handleError)
        );

    }
    private void handleResponse(String res ) {
        Toast toast =  Toast.makeText(loginActivity.this, "" + res, Toast.LENGTH_SHORT);
        toast.show();

        //can auto login with res call back

    }

    private void handleError(Throwable e) {


        int httpException =((HttpException) e).code();
        Log.i("error", String.valueOf(httpException));
        if(httpException == 400) Toast.makeText(this, "Error " + "Email is already exits in system!" , Toast.LENGTH_SHORT).show();
        if(httpException == 401)Toast.makeText(this, "Error " + "An error in database!" , Toast.LENGTH_SHORT).show();

    }


    private void loginUser(String email, String password) {

        Log.i("tag", "here1");
        if(TextUtils.isEmpty(email)){
            edit_login_email.setError("Cannot empty this field");
            edit_login_email.requestFocus();
            btn_login.setEnabled(true);
        }else if(!email.matches(emailPattern)){
            edit_login_email.setError("Email is not valid");
            edit_login_email.requestFocus();
            btn_login.setEnabled(true);
        }else if(TextUtils.isEmpty(password)){
            edit_login_password.setError("Cannot empty this field");
            edit_login_password.requestFocus();
            btn_login.setEnabled(true);
        } else{
            compositeDisposable.add(myService.loginUser(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse1, this::handleError1)
            );
        }

    }

    private void handleResponse1(String res ) throws JSONException {

        btn_login.setEnabled(true);
        Log.i("tag handleResponse", res);
        //read json response
        JSONObject resJson = new JSONObject(res);
        String nameUser = resJson.getString("name");
        String emailUser = resJson.getString("email");
        Boolean isInfo = resJson.getBoolean("isinfo");

        Toast toast =  Toast.makeText(loginActivity.this, "Login with " + nameUser, Toast.LENGTH_SHORT);
        toast.show();
        //will using acc from res
        session.createUserLoginSession(nameUser,emailUser);
        //direct to other info acc user
        if(isInfo){
            //set more info user
            String fullName = resJson.getString("fullname"),
                    sex = resJson.getString("sex"),
                    birth = resJson.getString("birth"),
                    idCard = resJson.getString("idcard"),
                    idBHYT = resJson.getString("idBHYT"),
                    national = resJson.getString("national"),
                    address = resJson.getString("address"),
                    numPhone = resJson.getString("numphone"),
                    vaccine = resJson.getString("vaccine");

            session.createUserInfoSession(fullName, sex, birth, idCard, idBHYT, national, address,numPhone, vaccine);

            PersonData.create(this,
                    fullName,
                    idCard,
                    idBHYT,
                    birth,
                    sex,
                    national,
                    address,
                    numPhone,
                    emailUser);
            //direct to home page
            // Starting home act
            //need add more info of user
            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            // Starting get more info User
            Intent i = new Intent(getApplicationContext(), getInfoUser.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        }


    }

    private void handleError1(Throwable e) {

        btn_login.setEnabled(true);

        int httpException =((HttpException) e).code();
        Log.i("error", String.valueOf(httpException));
        if(httpException == 400 || httpException == 401) Toast.makeText(this, "Error " + "Account not exits in system!" , Toast.LENGTH_SHORT).show();
        if(httpException == 402)Toast.makeText(this, "Error " + "Password is not match!" , Toast.LENGTH_SHORT).show();
    }



}
