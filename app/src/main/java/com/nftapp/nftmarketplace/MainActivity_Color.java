package com.nftapp.nftmarketplace;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nftapp.nftmarketplace.adapter.ColorAdapter;


public class MainActivity_Color extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ImageView back_button;
    private RecyclerView rcvItem;
    private ColorAdapter mColorAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


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

        rcvItem = findViewById(R.id.recycler_view_images);
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
        rcvItem.setAdapter(mColorAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}