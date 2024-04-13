package com.nftapp.nftmarketplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftapp.nftmarketplace.ItemInfo;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Item;

import java.util.List;

public class ItemAdapter_2 extends RecyclerView.Adapter<ItemAdapter_2.ItemViewHolder>{
    private Context mContext;
    private List<Item> mListItem;

    public ItemAdapter_2(List<Item> mListItem) {
        this.mListItem = mListItem;
    }

    public ItemAdapter_2(Context mContext) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heritage_2,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mListItem.get(position);
        if (item == null) {
            return;
        }
        holder.itemName.setText(item.getItem_name());
        holder.itemPlace.setText(item.getItem_place());
        Glide.with( mContext).load(item.getItem_image()).into(holder.itemImg);

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                onClickGoToDetail(item);
            }
        });

    }

    private void onClickGoToDetail(Item item){
        Intent intent = new Intent(mContext, ItemInfo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item",item);
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

        private CardView layout_item;
        private ImageView itemImg;
        private TextView itemName;
        private TextView itemPlace;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            itemImg = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPlace = itemView.findViewById(R.id.item_place);

        }
    }
}
