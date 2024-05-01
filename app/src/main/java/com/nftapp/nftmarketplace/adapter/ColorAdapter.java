package com.nftapp.nftmarketplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nftapp.nftmarketplace.PaintActivity;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Item;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ItemViewHolder>{
    private Context mContext;
    private List<Item> mListItem;

    public ColorAdapter(List<Item> mListItem) {
        this.mListItem = mListItem;
    }

    public ColorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Item> list) {
        this.mListItem = list;
        notifyDataSetChanged();
    }
    public void setFilterList(List<Item> filterList) {
        this.mListItem = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mListItem.get(position);
        if (item == null) {
            return;
        }
        Glide.with( mContext).load(item.getItem_image()).into(holder.image_outline);
        holder.image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                onClickGoToDetail(item);
            }
        });

    }

    private void onClickGoToDetail(Item item){
        Intent intent = new Intent(mContext, PaintActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("object_image",item.getItem_image());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(mListItem != null) {
            return mListItem.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_outline;
        private CardView image_layout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image_layout = itemView.findViewById(R.id.image_layout);
            image_outline = itemView.findViewById(R.id.image_outline);
        }
    }
}
