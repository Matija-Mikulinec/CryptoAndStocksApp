package com.example.cryptoandstocksapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryptoandstocksapp.R;
import com.example.cryptoandstocksapp.util.LocaleHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView cryptoTextView, stockCryptoData, newsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        // apply the saved language
        LocaleHelper.setLocale(context, getSavedLang(context));

        // now call the base implementation with the original context
        super.onAttach(context);
    }


    private String getSavedLang(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return prefs.getString("lang", "en");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cryptoTextView = view.findViewById(R.id.text_crypto);
        stockCryptoData = view.findViewById(R.id.stockCryptoData);
        newsTextView = view.findViewById(R.id.text_news);

        Button languageButton = view.findViewById(R.id.button_language);
        languageButton.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext()
                    .getSharedPreferences("settings", Context.MODE_PRIVATE);
            String current = prefs.getString("Locale.Helper.Selected.Language", "en");
            String next = current.equals("hr") ? "en" : "hr";
            prefs.edit().putString("Locale.Helper.Selected.Language", next).apply();

            getActivity().recreate();
        });




        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getCrypto().observe(getViewLifecycleOwner(), cryptos -> {
            if (cryptos != null && !cryptos.isEmpty()) {
                cryptoTextView.setText("BTC: $" + cryptos.get(0).current_price);
                stockCryptoData.setText("ETH: $" + cryptos.get(1).current_price);
            }
        });

        homeViewModel.getNewsSnippet().observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                newsTextView.setText(article.title + "\n" + article.description);
            }
        });

        homeViewModel.fetchAll();


    }





}
