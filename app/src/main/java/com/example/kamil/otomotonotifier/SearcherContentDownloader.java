package com.example.kamil.otomotonotifier;

import android.content.Context;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearcherContentDownloader {
    private Context context;
    private int downloadCountLimit = 10;
    private HashMap<String, List<Ad>> newAdsLists = new HashMap();

    public int getDownloadCountLimit() {
        return this.downloadCountLimit;
    }

    public void setDownloadCountLimit(int downloadCountLimit) {
        this.downloadCountLimit = downloadCountLimit;
    }

    public List<Ad> getAdListForSearcher(Searcher searcher) {
        return getAdListByKey(makeAndReturnSearcherKey(searcher));
    }

    private List<Ad> getAdListByKey(String key) {
        return (List) this.newAdsLists.get(key);
    }

    public void downloadSearcherContent(List<Searcher> searchers, Context context) throws Exception {
        this.context = context;
        for (String key : createAndReturnAllKeysFromSearchers(searchers)) {
            downloadSearcherContentByKey(key);
        }
    }

    private List<String> createAndReturnAllKeysFromSearchers(List<Searcher> searchers) {
        List<String> keys = new ArrayList();
        for (Searcher searcher : searchers) {
            String key = makeAndReturnSearcherKey(searcher);
            if (!keys.contains(key)) {
                keys.add(key);
            }
        }
        return keys;
    }

    @NonNull
    private String makeAndReturnSearcherKey(Searcher searcher) {
        if (isItBetterToOmitMake(searcher)) {
            return searcher.getCategory();
        }
        return searcher.getCategory() + "/" + searcher.getMake();
    }

    private void downloadSearcherContentByKey(String key) throws Exception {
        this.newAdsLists.put(key, downloadAndReturnAdsByKey(key));
    }

    private boolean isItBetterToOmitMake(Searcher searcher) {
        return searcher.getMake() == null || !searcher.getCategory().toLowerCase().equals("osobowe");
    }

    private List<Ad> downloadAndReturnAdsByKey(String key) throws Exception {
        AdIterator adIterator = new AdIterator();
        adIterator.initIterator(makeAndReturnUrlByKey(key));
        List<Ad> ads = adIterator.getNewerAdsThan(downloadAndReturnAdIdByKey(key), this.downloadCountLimit);
        if (!ads.isEmpty()) {
            saveNewLastAdId(new LastAdId(((Ad) ads.get(0)).getAdId(), key));
        }
        return ads;
    }

    private String makeAndReturnUrlByKey(String key) {
        return "https://www.otomoto.pl/i2/" + formatStringToUrlString(key) + "/?json=1&order=created_at:desc";
    }

    private String downloadAndReturnAdIdByKey(String key) {
        LastAdId lastAdId = AppSearchersDatabase.getDatabase(this.context).getLastAdIdDao().getLastAdId(key);
        if (lastAdId == null) {
            return "";
        }
        return lastAdId.lastAdId;
    }

    private void saveNewLastAdId(LastAdId adId) {
        AppSearchersDatabase.getDatabase(this.context).getLastAdIdDao().addLastAdId(adId);
    }

    private String formatStringToUrlString(String key) {
        return DiacriticalMarksToEnglishFormatter.formatString(key.toLowerCase().replaceAll(" ", "-"));
    }
}
