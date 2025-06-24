package com.example.cryptoandstocksapp.ui.home;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinGeckoApi {
    @GET("coins/markets")
    Call<List<Crypto>> getCryptoMarkets(
            @Query("vs_currency") String vsCurrency,
            @Query("ids") String ids,
            @Query("order") String order,
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query("sparkline") boolean sparkline
    );
}