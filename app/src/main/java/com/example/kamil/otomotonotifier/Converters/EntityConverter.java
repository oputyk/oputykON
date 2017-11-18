package com.example.kamil.otomotonotifier.Converters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 18/11/2017.
 */

public class EntityConverter {

    public static List<Ad> AdEntitiesToAds(List<AdEntity> adEntities) {
        List<Ad> ads = new ArrayList<>();
        for(AdEntity adEntity : adEntities) {
            ads.add(adEntity.getAd());
        }
        return ads;
    }

    public static List<Searcher> SearcherEntitiesToSearchers(List<SearcherEntity> searcherEntities) {
        List<Searcher> searchers = new ArrayList<>();
        for(SearcherEntity searcherEntity : searcherEntities) {
            searchers.add(searcherEntity.getSearcher());
        }
        return searchers;
    }
}
