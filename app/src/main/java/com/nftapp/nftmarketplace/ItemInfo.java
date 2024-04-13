package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nftapp.nftmarketplace.api.ApiService;
import com.nftapp.nftmarketplace.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemInfo extends AppCompatActivity {
    private TextView item_description;

    private ImageView back_button;
    private ImageView item_image;
    private TextView item_name;
    private TextView item_place;
    private ImageButton speaker;
    private ImageButton favourite;
    private MediaPlayer mediaPlayer1 = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        speaker = findViewById(R.id.speaker);
        favourite = findViewById(R.id.favourite);
        item_description = findViewById(R.id.item_description);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(ItemInfo.this,R.raw.close_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Item item = (Item) bundle.get("object_item");
        item_image = findViewById(R.id.item_image);
        item_name = findViewById(R.id.item_name);
        item_place = findViewById(R.id.item_place);
        Glide.with(this).load(item.getItem_image()).into(item_image);
        item_name.setText(item.getItem_name());
        item_place.setText(item.getItem_place());
        item_description.setText(item.getItem_description());
        if(item.getIsFavourite() == 0) {
            favourite.setBackgroundResource(R.drawable.favorite_black);
        } else {
            favourite.setBackgroundResource(R.drawable.favorite_red);
        }
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("URL_image",item.getItem_image());
                Intent intent = new Intent(ItemInfo.this, ImgView.class);
                intent.putExtra("image",bundle1);
                final MediaPlayer mediaPlayer = MediaPlayer.create(ItemInfo.this,R.raw.click_effect);
                mediaPlayer.start();
                startActivity(intent);
            }
        });
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mediaPlayer1.isPlaying()) {
                   mediaPlayer1.pause();
               } else {
                   prepareMediaPlayer();
                   mediaPlayer1.start();

               }
            }
        });


        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(ItemInfo.this,R.raw.click_effect);
                mediaPlayer.start();
                if (item.getIsFavourite() == 0) {
                    ApiService.apiService.sendPOST_item("",Integer.toString(item.getId()),"1").enqueue(new Callback<List<Item>>() {
                        @Override
                        public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                            List<Item> list = response.body();
                            if (list.get(0).getIsFavourite() == 1) {
                                favourite.setBackgroundResource(R.drawable.favorite_red);
                                item.setIsFavourite(1);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Item>> call, Throwable t) {
                            Toast.makeText(ItemInfo.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ApiService.apiService.sendPOST_item("",Integer.toString(item.getId()),"0").enqueue(new Callback<List<Item>>() {
                        @Override
                        public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                            List<Item> list = response.body();
                            if (list.get(0).getIsFavourite() == 0) {
                                favourite.setBackgroundResource(R.drawable.favorite_black);
                                item.setIsFavourite(0);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Item>> call, Throwable t) {
                            Toast.makeText(ItemInfo.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void prepareMediaPlayer() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Item item = (Item) bundle.get("object_item");
        try {
            mediaPlayer1.setDataSource(item.getItem_voice());
            mediaPlayer1.prepare();

        } catch (Exception exception) {
        }
    }
}