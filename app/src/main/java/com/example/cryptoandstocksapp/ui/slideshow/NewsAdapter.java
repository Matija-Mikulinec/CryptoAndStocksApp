package com.example.cryptoandstocksapp.ui.slideshow;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptoandstocksapp.R;
import com.example.cryptoandstocksapp.ui.home.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Article> articles;

    public NewsAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.title);
        holder.description.setText(article.description);

        if (article.source != null) {
            holder.source.setText(article.source.name);
        }

        if (article.publishedAt != null) {
            holder.date.setText(formatDate(article.publishedAt));
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private String formatDate(String rawDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return rawDate;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, source, date;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_title);
            description = view.findViewById(R.id.news_description);
            source = view.findViewById(R.id.news_source);
            date = view.findViewById(R.id.news_date);
        }
    }
}