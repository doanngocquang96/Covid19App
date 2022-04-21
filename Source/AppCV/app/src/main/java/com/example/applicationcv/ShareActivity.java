package com.example.applicationcv;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class ShareActivity extends AppCompatActivity {
    private TextView name, description;
    private String nameID, descrp;
    //private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        Bundle bundle = getIntent().getExtras();
        nameID = bundle.getString("name");
        descrp = bundle.getString("description");
        name = findViewById(R.id.dNameId);
        this.setTitle(nameID + " Vaccine");
        description = findViewById(R.id.dDescriptionId);

        description.setText(descrp);

    }



    public void shareInfo(View view) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("")
                .setText(descrp)
                .startChooser();
    }
}