package com.saurabh.gimagesearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    static String result = null;
    EditText editText;
    Button button;
    ProgressDialog progress;
    Integer responseCode = null;
    String responseMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Please enter something !!!");
                } else {
                    final String searchString = editText.getText().toString();
                    Log.d(TAG, "Searching for : " + searchString);

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // looking for
                    String searchStringNoSpaces = searchString.replace(" ", "+");

                    String urlString = getString(R.string.baseUrl) + searchStringNoSpaces + "&key=" + getString(R.string.key) + "&cx=" + getString(R.string.cx) + "&alt=" + getString(R.string.responsetype);
                    URL url = null;
                    try {
                        url = new URL(urlString);
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "ERROR converting String to URL " + e.toString());
                    }
                    Log.d(TAG, "Url = " + urlString);

                    // start AsyncTask
                    GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
                    Log.d(TAG, "Checking internet connectivity");
                    if (ConnectionCheck.isNetworkConnected(v.getContext())) {
                        searchTask.execute(url);
                    }
                }
            }
        });
    }

    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask - onPreExecute");
            progress.show();
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }

            try {
                responseCode = httpURLConnection.getResponseCode();
                responseMessage = httpURLConnection.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if (responseCode == 200) {

                    // response OK
                    BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    rd.close();
                    httpURLConnection.disconnect();
                    result = sb.toString();

                    Log.d(TAG, "result=" + result);

                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);
        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);
            progress.dismiss();
            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
            intent.putExtra("JSONResponse", result);
            startActivity(intent);
        }
    }
}

