package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.nftapp.nftmarketplace.adapter.ColorAdapter;
import com.nftapp.nftmarketplace.model.Item;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.nftapp.nftmarketplace.api.ApiService;

public class MainActivity_Color extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ImageView back_button;
    private RecyclerView rcvItem;
    private ColorAdapter mColorAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Item> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_color);
        back_button = findViewById(R.id.back_button);
        swipeRefreshLayout = findViewById(R.id.swipe_layout_3);
        swipeRefreshLayout.setOnRefreshListener(this);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity_Color.this,R.raw.close_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                onBackPressed();
            }
        });
        rcvItem = findViewById(R.id.rcv_main);
        mColorAdapter = new ColorAdapter(this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Màn hình đang ở chế độ ngang
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvItem.setLayoutManager(linearLayoutManager);
        } else {
            // Màn hình đang ở chế độ dọc
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvItem.setLayoutManager(linearLayoutManager);
        }
        getListImage();
        rcvItem.setAdapter(mColorAdapter);
    }

    private void getListImage() {
        ApiService.apiService.sendPOST_color("all").enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> list = response.body();
                mColorAdapter.setData(list);
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onRefresh() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity_Color.this,R.raw.reload_effect);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        getListImage();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}