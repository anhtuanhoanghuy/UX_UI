package com.nftapp.nftmarketplace.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftapp.nftmarketplace.MainActivity_Color;
import com.nftapp.nftmarketplace.PaintActivity;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Common;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ImageViewHolder>{
    private Context mContext;
    private List<Integer> listImages;

    //    public ImageAdapter(MainActivity mContext) {
//        this.mContext = mContext;
//        this.listImages = getImages();
//    }


    public ColorAdapter(Context mContext) {
        this.mContext = mContext;
        this.listImages = getImages();
    }

    private List<Integer> getImages() {
        List<Integer> results = new ArrayList<>();
        results.add(R.drawable.image1);
        results.add(R.drawable.image2);

        results.add(R.drawable.image3);
        results.add(R.drawable.image4);

        results.add(R.drawable.image6);
        results.add(R.drawable.image8);

        return results;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_images, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.imageView.setImageResource(listImages.get(position));
        Glide.with( mContext).load(listImages.get(position)).into(holder.imageView);
        holder.setImageOnClick(new ImageOnClick() {
            @Override
            public void onClick(int pos) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Common.ITEM_SELECTED = " "+position;
                Common.PICTURE_SELECTED = listImages.get(pos);
                mContext.startActivity(new Intent(mContext, PaintActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }
}
