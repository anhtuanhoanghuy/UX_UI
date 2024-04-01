package com.nftapp.nftmarketplace.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String category_name;
    private List<Item> items;

    public Category(String category_name, List<Item> items) {
        this.category_name = category_name;
        this.items = items;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
