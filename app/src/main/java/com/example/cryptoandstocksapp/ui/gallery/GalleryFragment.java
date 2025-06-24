package com.example.cryptoandstocksapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cryptoandstocksapp.R;
import com.example.cryptoandstocksapp.ui.home.CoinGeckoApi;
import com.example.cryptoandstocksapp.ui.home.Crypto;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private List<Crypto> cryptoList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CryptoAdapter(cryptoList);
        recyclerView.setAdapter(adapter);

        fetchCryptoData();

        return root;
    }

    private void fetchCryptoData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinGeckoApi api = retrofit.create(CoinGeckoApi.class);

        Call<List<Crypto>> call = api.getCryptoMarkets(
                "usd",       // vs_currency
                "bitcoin,ethereum,tether,ripple", // ids
                "market_cap_desc", // order
                10,          // per_page
                1,           // page
                false        // sparkline
        );

        call.enqueue(new Callback<List<Crypto>>() {
            @Override
            public void onResponse(Call<List<Crypto>> call, Response<List<Crypto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cryptoList.clear();
                    cryptoList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Crypto>> call, Throwable t) {
                // Handle error
            }
        });
    }
}