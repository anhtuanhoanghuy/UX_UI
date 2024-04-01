package com.nftapp.nftmarketplace.model;

import java.io.Serializable;

public class Item implements Serializable {
    public int id;
    public String item_image;
    public String item_name;
    public String item_place;
    public String item_description;

    public String item_voice;
    public int isFavourite;


    public Item(int id, String item_name, String item_place, String item_description, String item_image, String item_voice, int isFavourite) {
        this.id = id;
        this.item_name = item_name;
        this.item_place = item_place;
        this.item_description = item_description;
        this.item_image = item_image;
        this.item_voice = item_voice;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_place() {
        return item_place;
    }

    public void setItem_place(String item_place) {
        this.item_place = item_place;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }
    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_voice() {
        return item_voice;
    }

    public void setItem_voice(String item_voice) {
        this.item_voice = item_voice;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }
}
