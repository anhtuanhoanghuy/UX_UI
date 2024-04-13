package com.nftapp.nftmarketplace.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.ImgView;
import com.nftapp.nftmarketplace.QuizzLevel;
import com.nftapp.nftmarketplace.Travel;
import com.nftapp.nftmarketplace.ItemInfo;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.Feature;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>{
    private Context mContext;
    private List<Feature> mListFeature;

    public FeatureAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Feature> list) {
        this.mListFeature = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feature,parent,false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Feature feature = mListFeature.get(position);
        if (feature == null) {
            return;
        }
        holder.featureImg.setImageResource(feature.getResourceImage());
        holder.featureName.setText(feature.getFeature_name());
        holder.layout_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                onClickGoToActivity(position);
            }
        });

    }

    private void onClickGoToActivity(int position){
        Intent intent = null;
       switch (position) {
           case 0:
               intent = new Intent(mContext, Travel.class);
               break;
           case 1:
               intent = new Intent(mContext, ItemInfo.class);
               break;
           case 2:
               intent = new Intent(mContext, ImgView.class);
               break;
           case 3:
               intent = new Intent(mContext, QuizzLevel.class);
               break;
           
       }
        mContext.startActivity(intent);

    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(mListFeature != null) {
            return mListFeature.size();
        }
        return 0;
    }


    public class FeatureViewHolder extends RecyclerView.ViewHolder{
        private CardView layout_feature;
        private ImageView featureImg;
        private TextView featureName;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_feature = itemView.findViewById(R.id.layout_item);
            featureImg = itemView.findViewById(R.id.feature_image);
            featureName = itemView.findViewById(R.id.feature_name);
        }
    }
}
