package com.nftapp.nftmarketplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.CategoryPage;
import com.nftapp.nftmarketplace.ItemInfo;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Category;
import com.nftapp.nftmarketplace.model.Item;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context mContext;
    private List<Category> mListCategory;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public CategoryAdapter(List<Category> mListCategory) {
        this.mListCategory = mListCategory;
    }

    public void setData(List<Category> list) {
        this.mListCategory = list;
        notifyDataSetChanged();
    }
    public void setFilterList(List<Category> filterList) {
        this.mListCategory = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null){
            return;
        }
        holder.nameCategory.setText(category.getCategory_name());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rcvItem.setLayoutManager(linearLayoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(mContext);
        itemAdapter.setData(category.getItems());
        holder.rcvItem.setAdapter(itemAdapter);
        holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.button_effect);
                mediaPlayer.start();
                onClickGotoCategoryPage(category);
            }
        });
    }

    public void onClickGotoCategoryPage(Category category) {
        Intent intent = new Intent(mContext, CategoryPage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_category", category);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    public void release() {
        mContext = null;
    }
    @Override
    public int getItemCount() {
        if(mListCategory != null) {
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private Button view_button;
        private TextView nameCategory;
        private RecyclerView rcvItem;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCategory = itemView.findViewById(R.id.name_category);
            view_button = itemView.findViewById(R.id.view_button);
            rcvItem = itemView.findViewById(R.id.rcv_item);
        }
    }
}
