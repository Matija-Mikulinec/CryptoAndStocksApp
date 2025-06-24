package com.example.cryptoandstocksapp.ui.home;

import com.google.gson.annotations.SerializedName;

public class Crypto {
    @SerializedName("id")
    public String id;

    @SerializedName("symbol")
    public String symbol;

    @SerializedName("name")
    public String name;

    @SerializedName("current_price")
    public double currentPrice;  // Changed from current_price to currentPrice

    @SerializedName("image")
    public String image;

    @SerializedName("price_change_percentage_24h")
    public double priceChange24h;

    // Add constructor
    public Crypto(String id, String symbol, String name, double currentPrice, String image) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.image = image;
    }
}