package com.example.cryptoandstocksapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Crypto>> cryptoLiveData = new MutableLiveData<>();
    private final MutableLiveData<Article> newsSnippetLiveData = new MutableLiveData<>();

    public LiveData<List<Crypto>> getCrypto() {
        return cryptoLiveData;
    }

    public LiveData<Article> getNewsSnippet() {
        return newsSnippetLiveData;
    }

    public void fetchCrypto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinGeckoApi api = retrofit.create(CoinGeckoApi.class);
        api.getCryptoMarkets("usd", "bitcoin", "market_cap_desc", 1, 100, false)
                .enqueue(new Callback<List<Crypto>>() {
            @Override
            public void onResponse(Call<List<Crypto>> call, Response<List<Crypto>> response) {
                if (response.isSuccessful()) {
                    cryptoLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Crypto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi api = retrofit.create(NewsApi.class);
        api.getTopHeadlines("us", "business", 10, "249cb7bc37ef4b4bbcbfb485c55ccc42").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().articles.isEmpty()) {
                    Article art = new Article();
                    art.title = response.body().articles.get(0).title;
                    art.description = response.body().articles.get(0).description;
                    art.url = response.body().articles.get(0).url;
                    art.urlToImage = response.body().articles.get(0).urlToImage;
                    newsSnippetLiveData.postValue(art);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchAll() {
        fetchCrypto();
        fetchNews();
    }


    private final MutableLiveData<Object> text = new MutableLiveData<>();

    public LiveData<Object> getText() {
        return text;
    }

}
