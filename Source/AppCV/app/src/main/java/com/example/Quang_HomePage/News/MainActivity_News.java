package com.example.Quang_HomePage.News;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationcv.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity_News extends AppCompatActivity {
    ListView listView;
    CustomListViewAdapter adapter;
    ArrayList<News> newsArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news);

        anhxa();

        new ReadRSS().execute("https://vnexpress.net/rss/covid-19.rss");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity_News.this, DetailActivity.class);
                intent.putExtra("link", newsArrayList.get(i).getLink());
                startActivity(intent);
            }
        });



    }

    private void anhxa() {
        listView = (ListView) findViewById(R.id.lv_news);
    }



    private class ReadRSS extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            newsArrayList = new ArrayList<>();
            Document document = null;
            try {
                document = Jsoup.connect(strings[0]).get();
                Elements elements = document.select("item");
                News news = null;

                //for (Element element : elements) {      // duyet het phan tu elements
                for (int i=0;i<10;i++){                   // chi duyet 10 phan tu => 10 news
                    news = new News();
                    news.setTitle(elements.get(i).select("title").text());
                    news.setThumbnail(Jsoup.parse(elements.get(i).select("description").text()).select("img").attr("src"));
                    news.setLink(elements.get(i).select("link").text());
                    news.setDate(elements.get(i).select("pubDate").text().substring(0,25)); // substring cat bot chu thua dang sau cung

                    newsArrayList.add(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newsArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);
            adapter = new CustomListViewAdapter(MainActivity_News.this, R.layout.custom_list_item, newsArrayList);
            listView.setAdapter(adapter);

        }
    }
}