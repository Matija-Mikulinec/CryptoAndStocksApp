package com.example.cryptoandstocksapp.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
