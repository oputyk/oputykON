package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Formatters.DiacriticalMarksToEnglishFormatter;

/**
 * Created by kamil on 01/09/2017.
 */

public class AdFilterTools {
    static public boolean stringsEqual(String str1, String str2) {
        str1 = formatStringToAdFilterString(str1);
        str2 = formatStringToAdFilterString(str2);
        return str1.contains(str2) || str2.contains(str1);
    }

    static private String formatStringToAdFilterString(String str) {
        return DiacriticalMarksToEnglishFormatter.formatString(str).toLowerCase();
    }
}
