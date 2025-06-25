package com.example.cryptoandstocksapp.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything") // Assuming this is the correct endpoint for top headlines
    Call<NewsResponse> getTopHeadlines(
            @Query("q") String query,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey // *** FIXED: Removed colon before "apiKey" ***
    );

    @GET("everything") // Or your specific endpoint for crypto news, e.g., "v2/everything"
    Call<NewsResponse> getCryptoNews(
            @Query("q") String query,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}