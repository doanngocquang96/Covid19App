package com.example.applicationcv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.applicationcv.Rotrofit.RetrofitClient;
import com.example.applicationcv.Rotrofit.Services;
import com.example.database.PersonData;
import com.rengwuxian.materialedittext.MaterialEditText;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class getInfoUser extends AppCompatActivity {

    private DatePickerDialog picker;
    private String countries[] = {"Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua &amp; Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia &amp; Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Cape Verde","Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cruise Ship","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon","Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka","St Kitts &amp; Nevis","St Lucia","St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Uganda","Ukraine","United Arab Emirates","United Kingdom","Uruguay","Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"};
    private  String vaccines[] = {"Chọn Vaccine đã tiêm","AZD1222 (AstraZeneca)","Sputnik-V (Gamalaya)","Vero-Cell (Sinopharm)","Comirnaty (Pfizer BioNtech)","Spikevax (Moderna)","Vaccine Janssen","Vaccine Hayat-Vax","Vaccine-Abdala"};
    private static ArrayList<Provinces> provincesList = new ArrayList<Provinces>();
    private static ArrayList<Districts> districtsList = new ArrayList<Districts>();
    private static ArrayList<Communces> communcesList = new ArrayList<Communces>();
    private Spinner spProvinces,spDistrict, spCommune, spNational, spVaccine;
    private ArrayAdapter mAdapterVaccine;
    private static ArrayAdapter mAdapterCountries;
    private static ArrayAdapter mAdapterProvinces;
    private static ArrayAdapter mAdapterDistricts;
    private ArrayAdapter mAdapterCommunes;
    private static ArrayList<String> adtProvinesList = new ArrayList<String>();
    private static ArrayList<String> adtDistrictsList = new ArrayList<String>();
    private static ArrayList<String> adtCommuncestList = new ArrayList<String>();
    private Handler mainHandler = new Handler();
    private static String codeProvince = "-1";
    private String codeDistrict = "-1";
    private Button btnNextRegis;
    private ImageView btn_backLogin;
    private MaterialEditText editUserFullName, editUserIdCard, editUserBHYT, editUserAddress, editUserNumPhone, editUserEmail;
    private CheckBox checkCommit;
    private RadioButton checkNam, checkNu;

    private EditText userBirthday;
    private String email, sexUser = "Nam", address;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        }
        setContentView(R.layout.activity_getinfo);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(Services.class);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        //need check other info of user
        editUserFullName = (MaterialEditText) findViewById(R.id.editUserNameRegis);
        editUserIdCard = (MaterialEditText)findViewById(R.id.editUserCMND);
        editUserEmail = (MaterialEditText)findViewById(R.id.editUserEmail);
        editUserAddress = (MaterialEditText)findViewById(R.id.editUserNumHouse);
        editUserNumPhone = (MaterialEditText)findViewById(R.id.editUserNumPhone);
        editUserBHYT = (MaterialEditText) findViewById(R.id.editUserBHYT);

        checkCommit = (CheckBox)findViewById(R.id.checkCommit);
        checkNam = (RadioButton)findViewById(R.id.checkNam);
        checkNam.setChecked(true);
        checkNu = (RadioButton)findViewById(R.id.checkNu);

        spVaccine = (Spinner)findViewById(R.id.spVaccine);
        // Initialize an array adapter
        mAdapterVaccine = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vaccines);
        // Data bind the spinner with array adapter items
        spVaccine.setAdapter(mAdapterVaccine);

        userBirthday=(EditText) findViewById(R.id.editUserBirthday);
        userBirthday.setInputType(InputType.TYPE_NULL);
        userBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getInfoUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        userBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();


            }
        });

        btn_backLogin = (ImageView) findViewById(R.id.btn_backLogin);

        btnNextRegis = (Button)findViewById(R.id.btnNextRegis);
        btnNextRegis.setEnabled(false);

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(getApplicationContext(), loginActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Add new Flag to start new Activity
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                finish();
            }
        });

        checkCommit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if(spProvinces.getSelectedItem() != null && spDistrict.getSelectedItem() != null && spCommune.getSelectedItem() != null){
                        Log.i("tagCommit", "Commit");
                        btnNextRegis.setEnabled(true);
                    } else checkCommit.setChecked(false);

                } else {
                    Log.i("tagCommit", "noCommit");
                    btnNextRegis.setEnabled(false);
                }
            }
        });

//        Log.i("tag0", sexUser);

        //Fix sex and email
        checkNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sexUser = "Nam";
                    checkNu.setChecked(false);
                    Log.i("tag1a", sexUser);
                }else {
                    checkNam.setChecked(false);
                    Log.i("tag-1a", sexUser);
                }
            }
        });

        checkNu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sexUser = "Nu";
                    checkNam.setChecked(false);
                    Log.i("tag1b", sexUser);
                }else{
                    checkNu.setChecked(false);
                    Log.i("tag-1b", sexUser);
                }
            }
        });

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get email
        email = user.get(UserSessionManager.KEY_EMAIL);
        editUserEmail.setText(email);
        editUserEmail.setEnabled(false);
        editUserEmail.setClickable(false);

        btnNextRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnNextRegis.setEnabled(false);
                //check info fields
                if(editUserFullName.getText().toString().isEmpty()){
                    editUserFullName.setError("Field is empty");
                    btnNextRegis.setEnabled(true);
                } else if(userBirthday.getText().toString().isEmpty()){
                    userBirthday.setError("Field is empty");
                    btnNextRegis.setEnabled(true);
                }else if(spVaccine.getSelectedItem() == null){
                    ((TextView)spVaccine.getSelectedView()).setError("Error message");
                    btnNextRegis.setEnabled(true);
                } else if(editUserNumPhone.getText().toString().isEmpty()){
                    editUserNumPhone.setError("Field is empty");
                    btnNextRegis.setEnabled(true);
                } else if(editUserBHYT.getText().toString().isEmpty()){
                    editUserBHYT.setError("Field is empty");
                    btnNextRegis.setEnabled(true);
                }else if(editUserIdCard.getText().toString().length() != 9) {
                    if (editUserIdCard.getText().toString().length() != 12) {
                        editUserIdCard.setError("Length of Field must 9 or 12");
                        btnNextRegis.setEnabled(true);
                    } else {

                        address = editUserAddress.getText().toString()+ "/" + spCommune.getSelectedItem().toString() + ", " + spDistrict.getSelectedItem().toString() + ", " + spProvinces.getSelectedItem().toString();
                        //test here
                        addMoreInfoUser(email, editUserFullName.getText().toString(), sexUser, userBirthday.getText().toString(), editUserIdCard.getText().toString(), editUserBHYT.getText().toString(), spNational.getSelectedItem().toString(), address,  editUserNumPhone.getText().toString(), spVaccine.getSelectedItem().toString());

                    }
                }else {

                    address = editUserAddress.getText().toString()+ "/" + spCommune.getSelectedItem().toString() + ", " + spDistrict.getSelectedItem().toString() + ", " + spProvinces.getSelectedItem().toString();
                    //test here
                    addMoreInfoUser(email, editUserFullName.getText().toString(), sexUser, userBirthday.getText().toString(), editUserIdCard.getText().toString(), editUserBHYT.getText().toString(), spNational.getSelectedItem().toString(), address,  editUserNumPhone.getText().toString(), spVaccine.getSelectedItem().toString());

                }

            }
        });

        adtDistrictsList.clear();
        adtCommuncestList.clear();

        Log.i("msg", "start first fetching..");
        new fetchProvince().start();
        adtProvinesList.add("Chọn Tỉnh/TP");
        adtDistrictsList.add("Chọn Quận/Huyện");
        adtCommuncestList.add("Chọn Xã/Phườnng");
        spNational = (Spinner) findViewById(R.id.spNational);
        spProvinces = (Spinner) findViewById(R.id.spProvince);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spCommune = (Spinner) findViewById(R.id.spCommune);

        // Initialize an array adapter
        mAdapterCountries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        // Data bind the spinner with array adapter items
        spNational.setAdapter(mAdapterCountries);
        spNational.setSelection(200);

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


//        Log.i("tag test spniner1", spProvinces.getSelectedItem().toString());
//        Log.i("tag test spniner2", spDistrict.getSelectedItem().toString());
//        Log.i("tag test spniner3", spCommune.getSelectedItem().toString());


   }

    private void addMoreInfoUser(String email, String fullName, String sex, String birth, String idCard, String idBHYT, String national, String address, String numPhone, String vaccine) {

        compositeDisposable.add(myService.addInfoUser(email, fullName, sex, birth, idCard, idBHYT, national, address, numPhone, vaccine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse,this::handleError)
        );

    }

    private void handleError(Throwable error) {
        btnNextRegis.setEnabled(true);
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(String res) throws JSONException {

        //read json response
        //JSONObject resJson = new JSONObject(res);

        session.createUserInfoSession(editUserFullName.getText().toString(), sexUser, userBirthday.getText().toString(), editUserIdCard.getText().toString(), editUserBHYT.getText().toString(), spNational.getSelectedItem().toString(), address, editUserNumPhone.getText().toString(), spVaccine.getSelectedItem().toString());

        PersonData.create(this,
                editUserFullName.getText().toString(),
                editUserIdCard.getText().toString(),
                editUserBHYT.getText().toString(),
                userBirthday.getText().toString(),
                sexUser,
                spNational.getSelectedItem().toString(),
                address,
                editUserNumPhone.getText().toString(),
                email);

        //direct to home page
        // Starting get more info User
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
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

                        Districts districts = new Districts(districtName, districtCode);
                        districtsList.add(districts);
                        adtDistrictsList.add(districtName);

                        JSONArray mjsonArray = mJsonObject.getJSONArray("wards");
                        for (int j = 0; j < mjsonArray.length(); j++) {
                            JSONObject mjsonObject = mjsonArray.getJSONObject(j);
                            String communeName = mjsonObject.getString("name");
                            String communeCode = mjsonObject.getString("district_code");
                            Communces communces = new Communces(communeName, communeCode);
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

                       Provinces provinces = new Provinces(provinceName, provinceCode);
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

    public static class Communces {

        private String nameCommunce;
        private String codeCommunce;

        public Communces() {

        }

        public Communces(String nameCommunce, String codeCommunce) {
            this.nameCommunce = nameCommunce;
            this.codeCommunce = codeCommunce;
        }

        public String getNameCommunce() {
            return nameCommunce;
        }

        public void setNameCommunce(String nameCommunce) {
            this.nameCommunce = nameCommunce;
        }

        public String getCodeCommunce() {
            return codeCommunce;
        }

        public void setCodeCommunce(String codeCommunce) {
            this.codeCommunce = codeCommunce;
        }

        public String findNameCommuneByCode(String codeDistrict){
            if(getCodeCommunce().compareTo(codeDistrict) == 0) return nameCommunce;
            return "-1";
        }

        @Override
        public String toString() {
            return nameCommunce + ", " + codeCommunce;
        }
    }


    public static class Districts {

        private String nameDistrict;
        private String codeDistrict;

        public Districts() {

        }

        public Districts(String nameDistrict, String codeDistrict) {
            this.nameDistrict = nameDistrict;
            this.codeDistrict = codeDistrict;
        }

        public String getNameDistrict() {
            return nameDistrict;
        }

        public void setNameDistrict(String nameDistrict) {
            this.nameDistrict = nameDistrict;
        }

        public String getCodeDistrict() {
            return codeDistrict;
        }

        public void setCodeDistrict(String codeDistrict) {
            this.codeDistrict = codeDistrict;
        }

        public String findCodeByNameDistrict(String nameDistrict){
            if((getNameDistrict()).compareTo(nameDistrict) == 0) return codeDistrict;
            return "-1";
        }
        @Override
        public String toString() {
            return nameDistrict + ", " + codeDistrict;
        }
    }


    public static class Provinces {

        private String nameProvince;
        private String codeProvince;

        public Provinces() {

        }

        public Provinces(String nameProvince, String codeProvince) {
            this.nameProvince = nameProvince;
            this.codeProvince = codeProvince;
        }

        public String getNameProvince() {
            return nameProvince;
        }

        public void setNameProvince(String nameProvince) {
            this.nameProvince = nameProvince;
        }

        public String getCodeProvince() {
            return codeProvince;
        }

        public void setCodeProvince(String codeProvince) {
            this.codeProvince = codeProvince;
        }

        public String findCodeByNameProvince(String nameProvince){
            if((getNameProvince()).compareTo(nameProvince) == 0) return codeProvince;
            return "-1";
        }
        @Override
        public String toString() {
            return nameProvince + ", " + codeProvince;
        }
    }


}