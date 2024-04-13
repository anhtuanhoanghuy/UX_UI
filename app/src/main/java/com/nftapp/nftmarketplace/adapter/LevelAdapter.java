package com.nftapp.nftmarketplace.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.QuizzPackageSelector;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Level;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder>{
    private Context mContext;
    private List<Level> mListLevel;

    public LevelAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Level> list) {
        this.mListLevel = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizz_level_item,parent,false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Level level = mListLevel.get(position);
        if (level == null) {
            return;
        }
        holder.layout_level.setCardBackgroundColor(level.getResourceImage());
        holder.levelName.setText(level.getLevel_name());
        holder.layout_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                onClickGoToActivity(level);
            }
        });

    }

    private void onClickGoToActivity(Level level){
        Bundle bundle = new Bundle();
        bundle.putString("level",level.getLevel_name());
        Intent intent = new Intent(mContext, QuizzPackageSelector.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(mListLevel != null) {
            return mListLevel.size();
        }
        return 0;
    }


    public class LevelViewHolder extends RecyclerView.ViewHolder{
        private CardView layout_level;

        private TextView levelName;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_level = itemView.findViewById(R.id.layout_item);
            levelName = itemView.findViewById(R.id.level_name);
        }
    }
}
