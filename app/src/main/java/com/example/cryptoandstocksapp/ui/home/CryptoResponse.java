package com.example.cryptoandstocksapp.ui.home;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
public class CryptoResponse {
    @SerializedName("bitcoin")
    public Map<String, Double> bitcoin;

    @SerializedName("ethereum")
    public Map<String, Double> ethereum;
}