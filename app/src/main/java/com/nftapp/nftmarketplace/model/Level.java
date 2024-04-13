package com.nftapp.nftmarketplace.model;

import android.graphics.Color;

import java.io.Serializable;

public class Level implements Serializable {
    public int resourceImage;
    public String level_name;


    public Level(int resourceImage, String level_name) {
        this.resourceImage = resourceImage;
        this.level_name = level_name;

    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String feature_name) {
        this.level_name = level_name;
    }
}
