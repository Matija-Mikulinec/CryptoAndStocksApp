package com.example.cryptoandstocksapp.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryptoandstocksapp.databinding.FragmentHomeBinding;
import com.example.cryptoandstocksapp.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false); // Make sure layout exists
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView cryptoTextView = view.findViewById(R.id.text_crypto);
        TextView newsTextView = view.findViewById(R.id.text_news);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getCrypto().observe(getViewLifecycleOwner(), cryptos -> {
            if (cryptos != null && !cryptos.isEmpty()) {
                String info = "BTC: $" + cryptos.get(0).current_price;
                cryptoTextView.setText(info);
            }
        });

        homeViewModel.getNewsSnippet().observe(getViewLifecycleOwner(), snippet -> {
            if (snippet != null) {
                newsTextView.setText(snippet.toString());
            }
        });

        homeViewModel.fetchAll();
    }
}
