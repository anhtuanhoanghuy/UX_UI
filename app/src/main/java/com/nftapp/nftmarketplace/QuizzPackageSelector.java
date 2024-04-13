package com.nftapp.nftmarketplace;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nftapp.nftmarketplace.adapter.QuizzPackageAdapter;
import com.nftapp.nftmarketplace.api.ApiService;
import com.nftapp.nftmarketplace.model.Item;
import com.nftapp.nftmarketplace.model.Level;
import com.nftapp.nftmarketplace.model.QuizzPackage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzPackageSelector extends AppCompatActivity {

    private ImageView back_button;
    private RecyclerView rcvItem;
    private QuizzPackageAdapter mQuizzPackageAdapter;

    private List<QuizzPackage> mQuizzPackagesList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_package_selector);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzPackageSelector.this,R.raw.close_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
        rcvItem = findViewById(R.id.rcv_items);
        mQuizzPackageAdapter = new QuizzPackageAdapter(this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Màn hình đang ở chế độ ngang
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            rcvItem.setLayoutManager(gridLayoutManager);
        } else {
            // Màn hình đang ở chế độ dọc
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            rcvItem.setLayoutManager(gridLayoutManager);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        String level =  bundle.getString("level");
        getListItem(level);
        mQuizzPackageAdapter.setLevel(level);
        rcvItem.setAdapter(mQuizzPackageAdapter);

    }

    private void getListItem(String level){

        ApiService.apiService.sendPOST_package(level,"").enqueue(new Callback<List<QuizzPackage>>() {

            @Override
            public void onResponse(Call<List<QuizzPackage>> call, Response<List<QuizzPackage>> response) {
                List<QuizzPackage> list = response.body();
                mQuizzPackageAdapter.setData(list);
            }
            @Override
            public void onFailure(Call<List<QuizzPackage>> call, Throwable t) {
            }
        });
    }


}
