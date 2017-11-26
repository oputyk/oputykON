package com.example.kamil.otomotonotifier.Converters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.Models.AdEntity;

/**
 * Created by kamil on 26/11/2017.
 */

public class AdEntityToTextMessageConverter {
    public static String convert(AdEntity adEntity) {
        StringBuilder messageBuilder = new StringBuilder();
        Ad ad = adEntity.getAd();
        messageBuilder.append(ad.getLink() + '\n');
        messageBuilder.append(ad.toString());

        return messageBuilder.toString();
    }
}
