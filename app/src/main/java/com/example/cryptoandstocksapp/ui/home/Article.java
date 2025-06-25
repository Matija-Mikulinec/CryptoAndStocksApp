package com.example.cryptoandstocksapp.ui.home;

public class Article {
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public Source source;

    public static class Source {
        public String name;
    }
}