package com.example.kamil.otomotonotifier.Formatters;

/**
 * Created by kamil on 31/08/2017.
 */

public class ParameterFormatter {
    public static String format(String parameter) {
        parameter = DiacriticalMarksToEnglishFormatter.formatString(parameter);
        parameter = parameter.replaceAll(" ", "-");
        parameter = parameter.toLowerCase();
        return parameter;
    }
}
