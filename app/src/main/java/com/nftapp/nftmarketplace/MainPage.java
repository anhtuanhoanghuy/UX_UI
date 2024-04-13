package com.nftapp.nftmarketplace;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.adapter.FeatureAdapter;
import com.nftapp.nftmarketplace.model.Feature;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private RecyclerView rcvFeature;
    private FeatureAdapter featureAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        rcvFeature = findViewById(R.id.rcv_main);
        featureAdapter = new FeatureAdapter(this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Màn hình đang ở chế độ ngang
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvFeature.setLayoutManager(linearLayoutManager);
        } else {
            // Màn hình đang ở chế độ dọc
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvFeature.setLayoutManager(linearLayoutManager);
        }
        featureAdapter.setData(getListFeature());
        rcvFeature.setAdapter(featureAdapter);
    }
    private List<Feature> getListFeature(){
        List<Feature> list = new ArrayList<>();
        list.add(new Feature(R.drawable.thamquan, "Tham quan"));
        list.add(new Feature(R.drawable.avt2, "Ghép tranh"));
        list.add(new Feature(R.drawable.tomau, "Tô màu"));
        list.add(new Feature(R.drawable.avt5, "Giải đố"));
        return list;
    }


}