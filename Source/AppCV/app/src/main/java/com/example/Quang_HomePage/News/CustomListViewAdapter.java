package com.example.Quang_HomePage.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.applicationcv.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<News> newsList;

    public CustomListViewAdapter(Context context, int layout, List<News> newsList) {
        this.context = context;
        this.layout = layout;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // anh xa

        TextView txtTitle = (TextView) view.findViewById(R.id.tv_tittle);
        TextView txtDate = (TextView) view.findViewById(R.id.tv_date);
        ImageView imgThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);

        // gan gia tri

        News news = newsList.get(i);
        txtTitle.setText((news.getTitle()));
        txtDate.setText(news.getDate());
        Picasso.get().load(news.getThumbnail()).into(imgThumbnail);     // nếu ảnh động thì dùng thư viện Glide thay cho Picasso

        return view;
    }
}
