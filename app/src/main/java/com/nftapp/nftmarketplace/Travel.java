package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nftapp.nftmarketplace.adapter.CategoryAdapter;
import com.nftapp.nftmarketplace.adapter.ItemAdapter;
import com.nftapp.nftmarketplace.adapter.ItemAdapter_2;
import com.nftapp.nftmarketplace.api.ApiService;
import com.nftapp.nftmarketplace.model.Category;
import com.nftapp.nftmarketplace.model.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Travel extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ImageView back_button;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private SearchView searchView;
    private ItemAdapter_2 mItemAdapter;
    private List<Item> item;
    private ImageView no_result;
    private TextView no_result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_page);
        back_button = findViewById(R.id.back_button);
        swipeRefreshLayout = findViewById(R.id.swipe_layout_1);
        swipeRefreshLayout.setOnRefreshListener(this);
        no_result = findViewById(R.id.no_result_1);
        no_result_text = findViewById(R.id.no_result_text_1);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(Travel.this,R.raw.click_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
        rcvCategory = findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        getListCategory();

        rcvCategory.setAdapter(categoryAdapter);
        searchView = findViewById(R.id.search_1);
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
        getListCategory();
    }

    private void filterList(String newText) {
        if (newText.equals("")) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvCategory.setLayoutManager(linearLayoutManager);
            getListCategory();
            rcvCategory.setAdapter(categoryAdapter);
            rcvCategory.setVisibility(View.VISIBLE);
            no_result.setVisibility(View.GONE);
            no_result_text.setVisibility(View.GONE);
        } else {

            mItemAdapter = new ItemAdapter_2(this);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Màn hình đang ở chế độ ngang
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
                rcvCategory.setLayoutManager(gridLayoutManager);
            } else {
                // Màn hình đang ở chế độ dọc
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                rcvCategory.setLayoutManager(gridLayoutManager);
            }
            rcvCategory.setAdapter(mItemAdapter);

            ApiService.apiService.sendPOST_item("","",newText.toLowerCase()).enqueue(new Callback<List<Item>>() {

                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    List<Item> filterList = response.body();
                    if (filterList.isEmpty()) {
                        rcvCategory.setVisibility(View.GONE);
                        no_result.setVisibility(View.VISIBLE);
                        no_result_text.setVisibility(View.VISIBLE);

                    } else {
                        rcvCategory.setVisibility(View.VISIBLE);
                        no_result.setVisibility(View.GONE);
                        no_result_text.setVisibility(View.GONE);
                        mItemAdapter.setFilterList(filterList);
                    }
                }
                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
                    Toast.makeText(Travel.this,"failed",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void getListCategory(){

            ApiService.apiService.sendPOST_category("all","","").enqueue(new Callback<List<Category>>() {

                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    List<Category> listCategory = response.body();
                    List<Item> itemList = new ArrayList<>();
                    for (Category category : listCategory) {
                        ApiService.apiService.sendPOST_item(category.getCategory_name(), "10", "").enqueue(new Callback<List<Item>>() {
                            @Override
                            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                                List<Item> list = response.body();

                                if(list.isEmpty()) {
                                    listCategory.remove(category);
                                } else {
                                    category.setItems(list);
                                    categoryAdapter.setData(listCategory);
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Item>> call, Throwable t) {
                                Toast.makeText(Travel.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Toast.makeText(Travel.this,"failed",Toast.LENGTH_SHORT).show();
                }
            });
        }


    @Override
    public void onRefresh() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(Travel.this,R.raw.reload_effect);
        mediaPlayer.start();
        getListCategory();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}