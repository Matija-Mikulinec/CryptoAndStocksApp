package com.example.cryptoandstocksapp.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("simple/price")
    Call<CryptoResponse> getCryptoPrices(
            @Query("ids") String ids,
            @Query("vs_currencies") String currencies
    );

}