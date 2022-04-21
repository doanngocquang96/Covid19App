package com.example.applicationcv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.applicationcv.FaqAdapter;
import com.example.applicationcv.FaqModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FaqActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<FaqModel> mFaqModelList;

    int images[] = {R.drawable.faq_logo1, R.drawable.faq_logo2, R.drawable.faq_logo3, R.drawable.faq_logo4, R.drawable.faq_logo5, R.drawable.faq_logo6, R.drawable.faq_logo7, R.drawable.faq_logo8, R.drawable.faq_logo9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        }

        setContentView(R.layout.activity_faq);
        setTitle("Câu hỏi thường gặp");
        mRecyclerView = findViewById(R.id.recyclerViewFAQ);

        initData();
        setRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faq_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.anwseraboutinfo:
                Intent intent = new Intent(this, Terms_of_use.class);
                startActivity(intent);
                finish();
                break;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView() {
        FaqAdapter mFaqAdapter = new FaqAdapter(mFaqModelList, images);
        mRecyclerView.setAdapter(mFaqAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initData() {
        mFaqModelList = new ArrayList<>();
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion1), getString(R.string.faqAnswer1)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion7), getString(R.string.faqAnswer7)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion4), getString(R.string.faqAnswer4)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion5), getString(R.string.faqAnswer5)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion7), getString(R.string.faqAnswer7)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion8), getString(R.string.faqAnswer8)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion7), getString(R.string.faqAnswer7)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion9), getString(R.string.faqAnswer9)));
        mFaqModelList.add(new FaqModel(getString(R.string.faqQuestion8), getString(R.string.faqAnswer8)));
    }
}