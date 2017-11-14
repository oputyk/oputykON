package com.example.kamil.otomotonotifier.AdEngine.Downloaders;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDownloader {
    static int downloadingCounter;

    public static void setDownloadingCounter(int value) {
        downloadingCounter = value;
    }

    public static int getDownloadingCounter() {
        return downloadingCounter;
    }

    public static JSONObject downloadJson(String url) throws IOException, JSONException {
        String jsonBody = downloadJsonBody(url);
        downloadingCounter++;
        return new JSONObject(jsonBody);
    }

    private static String downloadJsonBody(String url) throws IOException {
        return new OkHttpClient().newCall(new Builder().url(url).build()).execute().body().string();
    }
}
