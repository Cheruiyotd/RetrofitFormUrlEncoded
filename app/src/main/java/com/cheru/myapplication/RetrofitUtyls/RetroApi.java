package com.cheru.myapplication.RetrofitUtyls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroApi {

    private static Retrofit retrofit = null;
    public static PostProject getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        PostProject api = retrofit.create(PostProject.class);
        return api; // return the APIInterface object
    }

}
