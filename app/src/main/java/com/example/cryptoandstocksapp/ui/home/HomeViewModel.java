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

    private MutableLiveData<List<Crypto>> cryptoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> newsSnippetLiveData = new MutableLiveData<>();

    public LiveData<List<Crypto>> getCrypto() {
        return cryptoLiveData;
    }

    public LiveData<String> getNewsSnippet() {
        return newsSnippetLiveData;
    }

    public void fetchCrypto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinGeckoApi api = retrofit.create(CoinGeckoApi.class);
        api.getCryptoMarkets("usd", "bitcoin,ethereum").enqueue(new Callback<List<Crypto>>() {
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
        api.getTopHeadlines("us", "249cb7bc37ef4b4bbcbfb485c55ccc42").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().articles.isEmpty()) {
                    newsSnippetLiveData.postValue(response.body().articles.get(0).title);
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


    private MutableLiveData<Object> text = new MutableLiveData<>();

    public LiveData<Object> getText() {
        return text;
    }

}
