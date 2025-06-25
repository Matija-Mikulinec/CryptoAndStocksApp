package com.example.cryptoandstocksapp.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull; // Added for @NonNull
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout; // Correct import

import com.example.cryptoandstocksapp.R;
import com.example.cryptoandstocksapp.ui.home.Article;
import com.example.cryptoandstocksapp.ui.home.NewsApi;
import com.example.cryptoandstocksapp.ui.home.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout; // Variable is declared
    private List<Article> articles = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Initialize SwipeRefreshLayout from the layout
        // *** FIX: Make sure your fragment_slideshow.xml has a SwipeRefreshLayout with this ID ***
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout_slideshow); // Or whatever ID you gave it in XML

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.news_recycler_view); // This was correct

        adapter = new NewsAdapter(articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set the refresh listener for SwipeRefreshLayout
        // *** Make sure swipeRefreshLayout is not null here ***
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this::fetchNewsData);
        } else {
            Log.e("SlideshowFragment", "SwipeRefreshLayout not found. Check your XML layout ID.");
            // Optionally, you could disable refresh functionality or show an error
        }


        // Initial data fetch if the list is empty
        if (articles.isEmpty()) {
            fetchNewsData();
        }

        return root;
    }

    private void fetchNewsData() {
        // Show the refresh indicator if swipeRefreshLayout is initialized
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi api = retrofit.create(NewsApi.class);

        api.getCryptoNews(
                "crypto OR blockchain OR bitcoin",  // Query keywords
                "publishedAt",                      // Sort by publish date
                20,                                 // Number of articles
                "249cb7bc37ef4b4bbcbfb485c55ccc42"  // ✅ Replace with your actual API key
        ).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                // Hide the refresh indicator if swipeRefreshLayout is initialized
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (response.isSuccessful() && response.body() != null) {
                    articles.clear();
                    articles.addAll(response.body().articles);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("News", "Failed to fetch news: " + response.message());
                    // Consider showing a user-friendly error message here
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                // Hide the refresh indicator if swipeRefreshLayout is initialized
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Log.e("News", "Error fetching news: ", t);
                // Consider showing a user-friendly error message here (e.g., network error)
            }
        });
    }
}