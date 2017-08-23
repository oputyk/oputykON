package com.example.kamil.otomotonotifier;

import java.util.HashMap;

public class DiacriticalMarksToEnglishFormatter {
    private static final HashMap<String, String> diacriticalMarksMap = new HashMap();

    static {
        diacriticalMarksMap.put("ą", "a");
        diacriticalMarksMap.put("ć", "c");
        diacriticalMarksMap.put("ę", "e");
        diacriticalMarksMap.put("ł", "l");
        diacriticalMarksMap.put("ń", "n");
        diacriticalMarksMap.put("ó", "o");
        diacriticalMarksMap.put("ś", "s");
        diacriticalMarksMap.put("ź", "z");
        diacriticalMarksMap.put("ż", "z");
        diacriticalMarksMap.put("ë", "e");
        diacriticalMarksMap.put("š", "s");
        diacriticalMarksMap.put("č", "c");
        diacriticalMarksMap.put("ť", "t");
        diacriticalMarksMap.put("ž", "z");
        diacriticalMarksMap.put("á", "a");
        diacriticalMarksMap.put("í", "i");
        diacriticalMarksMap.put("é", "e");
        diacriticalMarksMap.put("đ", "d");
        diacriticalMarksMap.put("ä", "a");
        diacriticalMarksMap.put("ö", "o");
        diacriticalMarksMap.put("ü", "u");
        diacriticalMarksMap.put("ß", "ss");
    }

    public static String formatString(String string) {
        for (String diacriticalMark : diacriticalMarksMap.keySet()) {
            if (string.contains(diacriticalMark)) {
                string = string.replaceAll(diacriticalMark, (String) diacriticalMarksMap.get(diacriticalMark));
            }
        }
        return string;
    }
}
