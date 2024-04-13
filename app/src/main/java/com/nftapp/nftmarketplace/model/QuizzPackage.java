package com.nftapp.nftmarketplace.model;

import java.io.Serializable;

public class QuizzPackage implements Serializable {
    public int id;

    public String package_number;

    public QuizzPackage(int id, String package_number) {
        this.id = id;
        this.package_number = package_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackage_number() {
        return package_number;
    }

    public void setPackage_number(String package_number) {
        this.package_number = package_number;
    }
}
