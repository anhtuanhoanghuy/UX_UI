package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImgView extends AppCompatActivity {

    private ImageView img_view;
    private ImageView close_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view);
        img_view = findViewById(R.id.img_view);
        String url = getIntent().getBundleExtra("image").getString("URL_image");
        Glide.with(this).load(url).into(img_view);
        close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final MediaPlayer mediaPlayer = MediaPlayer.create(ImgView.this,R.raw.close_effect);
                mediaPlayer.start();
                onBackPressed();
            }
        });
    }
}