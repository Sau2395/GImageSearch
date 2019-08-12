package com.saurabh.gimagesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    private static final String TAG = "SearchResults";
    List<ImageDetails> listOfImageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        try {
            String jsonResponse = getIntent().getStringExtra("JSONResponse");
            Log.d("ImageDetails Extracting", "Size :");
            listOfImageDetails = getImageDetailsFromJson(jsonResponse);
            Log.d("ImageDetails Success", "Size :" + listOfImageDetails.size());
            ConnectionCheck.isNetworkConnected(this);
            initRecyclerView();
        } catch (Exception e) {
            Log.d(TAG, "Exception caught : " + e.getMessage());
        }
    }

    private void initRecyclerView() {

        Log.d(TAG, "Starting RecyclerView....");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchResults_recycleView);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(listOfImageDetails, this);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public List<ImageDetails> getImageDetailsFromJson(String json) throws JSONException, IOException {
        ResultItemsJsonDataHolder dataHolder = new ObjectMapper()
                .readValue(json, ResultItemsJsonDataHolder.class);
        return dataHolder.resultItems;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ResultItemsJsonDataHolder {
        @JsonProperty("items")
        public List<ImageDetails> resultItems;
    }
}
