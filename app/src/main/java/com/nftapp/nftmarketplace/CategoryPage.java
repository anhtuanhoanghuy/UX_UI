package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nftapp.nftmarketplace.adapter.CategoryAdapter;
import com.nftapp.nftmarketplace.adapter.ItemAdapter;
import com.nftapp.nftmarketplace.adapter.ItemAdapter_2;
import com.nftapp.nftmarketplace.api.ApiService;
import com.nftapp.nftmarketplace.model.Category;
import com.nftapp.nftmarketplace.model.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.ArrayList;
import java.util.List;

public class CategoryPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView category_name;
    private ImageView back_button;
    private ImageView no_result;
    private TextView no_result_text;
    private RecyclerView rcvItem;
    private ItemAdapter_2 mItemAdapter;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Item> item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        back_button = findViewById(R.id.back_button);
        swipeRefreshLayout = findViewById(R.id.swipe_layout_2);
        swipeRefreshLayout.setOnRefreshListener(this);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(CategoryPage.this,R.raw.click_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
        no_result = findViewById(R.id.no_result_2);
        no_result_text = findViewById(R.id.no_result_text_2);
        rcvItem = findViewById(R.id.rcv_items);
        mItemAdapter = new ItemAdapter_2(this);
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
        Category category = (Category) bundle.get("object_category");
        category_name = findViewById(R.id.category_name);
        Intent intent = getIntent();
        category_name.setText(category.getCategory_name());
        getListItem(category.getCategory_name());
        rcvItem.setAdapter(mItemAdapter);
        searchView = findViewById(R.id.search_2);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        Category category = (Category) bundle.get("object_category");
        getListItem(category.getCategory_name());

    }

    private void filterList(String newText) {
        ApiService.apiService.sendPOST_item("","",newText.toLowerCase()).enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> filterList = response.body();
                if (filterList.isEmpty()) {
                    rcvItem.setVisibility(View.GONE);
                    no_result.setVisibility(View.VISIBLE);
                    no_result_text.setVisibility(View.VISIBLE);

                } else {
                    rcvItem.setVisibility(View.VISIBLE);
                    no_result.setVisibility(View.GONE);
                    no_result_text.setVisibility(View.GONE);
                    mItemAdapter.setFilterList(filterList);
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(CategoryPage.this,"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getListItem(String category){

        ApiService.apiService.sendPOST_item(category,"","").enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> list = response.body();
                mItemAdapter.setData(list);
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(CategoryPage.this,"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


//    Hàm dưới đây dùng để tạo hiệu ứng refresh và âm thanh khi nhấn vào button

    @Override
    public void onRefresh() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(CategoryPage.this,R.raw.reload_effect);
        mediaPlayer.start();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        Category category = (Category) bundle.get("object_category");
        getListItem(category.getCategory_name());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}


