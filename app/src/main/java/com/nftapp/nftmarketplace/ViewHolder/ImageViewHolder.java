package com.nftapp.nftmarketplace.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.Intreface.ImageOnClick;
import com.nftapp.nftmarketplace.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    private ImageOnClick imageOnClick;

    public void setImageOnClick(ImageOnClick imageOnClick) {
        this.imageOnClick = imageOnClick;
    }

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_outline);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnClick.onClick(getAbsoluteAdapterPosition());
            }
        });
    }
}
