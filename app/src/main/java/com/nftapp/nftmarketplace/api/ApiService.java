package com.nftapp.nftmarketplace.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nftapp.nftmarketplace.model.Category;
import com.nftapp.nftmarketplace.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//Link API:http://localhost:3000/VNTravel/Travel.php
public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.95.1/VNTravel/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @FormUrlEncoded
    @POST("Travel.php")
    Call<List<Item>> sendPOST_item(@Field("category_name") String name,
                             @Field("item_limit") String item_limit,
                             @Field("search") String search);
    @FormUrlEncoded
    @POST("Travel.php")
    Call<List<Category>> sendPOST_category(@Field("category_name") String name,
                                  @Field("item_limit") String item_limit,
                                  @Field("search") String search);

    @FormUrlEncoded
    @POST("Travel.php")
    Call<List<Category>> sendPOST_favourite(@Field("category_name") String name,
                                           @Field("item_limit") String item_limit,
                                           @Field("search") String search);

}
