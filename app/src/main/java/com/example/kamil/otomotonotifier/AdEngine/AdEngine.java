package com.example.kamil.otomotonotifier.AdEngine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kamil.otomotonotifier.AdEngine.Converters.SearcherToFilterContainerConverter;
import com.example.kamil.otomotonotifier.AdEngine.Downloaders.SearcherContentDownloader;
import com.example.kamil.otomotonotifier.AdEngine.Filters.FilterContainer;
import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamil on 18/11/2017.
 */

public class AdEngine {

    public static Map<Searcher, List<Ad>> findNewAdsBySearchers(List<Searcher> searchers, Context context) {
        SearcherContentDownloader downloader = new SearcherContentDownloader();
        try {
            downloader.downloadSearcherContent(searchers, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makeSearchersAdListMap(searchers, downloader);
    }

    @NonNull
    private static Map<Searcher, List<Ad>> makeSearchersAdListMap(List<Searcher> searchers, SearcherContentDownloader downloader) {
        Map<Searcher, List<Ad>> map = new HashMap<>();
        for(Searcher searcher : searchers) {
            map.put(searcher, filterAdsBySearcher(downloader.getAdListForSearcher(searcher), searcher));
        }
        return map;
    }

    private static List<Ad> filterAdsBySearcher(List<Ad> adListForSearcher, Searcher searcher) {
        SearcherToFilterContainerConverter converter = new SearcherToFilterContainerConverter();
        FilterContainer filterContainer = converter.convertSearcherToFilterContainer(searcher);
        return filterContainer.filterAds(adListForSearcher);
    }

}
