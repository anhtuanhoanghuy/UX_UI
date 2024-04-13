package com.nftapp.nftmarketplace;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nftapp.nftmarketplace.adapter.LevelAdapter;
import com.nftapp.nftmarketplace.model.Level;

import java.util.ArrayList;
import java.util.List;

public class QuizzLevel extends AppCompatActivity {
    private ImageView back_button;
    private RecyclerView rcvLevel;
    private LevelAdapter levelAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_level);
        rcvLevel = findViewById(R.id.rcv_main);
        levelAdapter = new LevelAdapter(this);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzLevel.this,R.raw.close_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Màn hình đang ở chế độ ngang
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvLevel.setLayoutManager(linearLayoutManager);
        } else {
            // Màn hình đang ở chế độ dọc
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvLevel.setLayoutManager(linearLayoutManager);
        }
        levelAdapter.setData(getListLevel());
        rcvLevel.setAdapter(levelAdapter);
    }

    private List<Level> getListLevel(){
        List<Level> list = new ArrayList<>();
        list.add(new Level(Color.rgb(96,197,103),"Dễ"));
        list.add(new Level(Color.rgb(244,174,62), "Trung bình"));
        list.add(new Level(Color.rgb(134,109,237), "Nâng cao"));
        list.add(new Level(Color.rgb(233,51,76), "Khó"));
        return list;
    }


}