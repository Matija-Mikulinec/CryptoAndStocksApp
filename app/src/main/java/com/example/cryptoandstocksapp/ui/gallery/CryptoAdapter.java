package com.example.cryptoandstocksapp.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cryptoandstocksapp.R;
import com.example.cryptoandstocksapp.ui.home.Crypto;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolder> {

    private List<Crypto> cryptoList;

    public CryptoAdapter(List<Crypto> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crypto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Crypto crypto = cryptoList.get(position);

        holder.name.setText(crypto.name);
        holder.symbol.setText(crypto.symbol.toUpperCase());
        holder.price.setText(String.format("$%,.2f", crypto.currentPrice)); // Changed to currentPrice

        Picasso.get()
                .load(crypto.image)
                .placeholder(R.drawable.ic_crypto_placeholder)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, symbol, price;
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.crypto_name);
            symbol = view.findViewById(R.id.crypto_symbol);
            price = view.findViewById(R.id.crypto_price);
            icon = view.findViewById(R.id.crypto_icon);
        }
    }
}