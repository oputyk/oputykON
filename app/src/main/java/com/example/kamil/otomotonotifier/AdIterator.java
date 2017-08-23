package com.example.kamil.otomotonotifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class AdIterator {
    AdListJsonParser adListJsonParser = new AdListJsonParser();
    List<Ad> ads = new ArrayList();
    String basicPageUrl;
    String nextPageUrl;

    public void initIterator(String url) throws Exception {
        this.basicPageUrl = url;
        this.nextPageUrl = url;
        loadPage();
    }

    public Ad getAd(int i) throws Exception {
        if (this.ads.size() > i) {
            return (Ad) this.ads.get(i);
        }
        if (this.adListJsonParser.getAllAdsCount() > i) {
            loadPage();
            return getAd(i);
        }
        throw new Exception("Too big value in \"Ad getAd(int i)\", index = " + Integer.toString(i) + ", size = " + Integer.toString(this.adListJsonParser.getAllAdsCount()));
    }

    public void reload() throws Exception {
        this.ads = new ArrayList();
        this.nextPageUrl = this.basicPageUrl;
        loadPage();
    }

    public int getAllAdsCount() {
        return this.adListJsonParser.getAllAdsCount();
    }

    public List<Ad> getNewerAdsThan(String adId, int countLimit) {
        List<Ad> newerAds = new ArrayList();
        int i = 0;
        while (i < countLimit) {
            try {
                if (getAd(i).equals(adId)) {
                    break;
                }
                newerAds.add(this.ads.get(i));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
            }
        }
        return newerAds;
    }

    private void loadPage() throws IOException, JSONException {
        this.adListJsonParser.parse(JsonDownloader.downloadJson(this.nextPageUrl));
        this.nextPageUrl = this.adListJsonParser.getNextPageUrl();
        for (Ad ad : this.adListJsonParser.getAds()) {
            this.ads.add(ad);
        }
    }
}
