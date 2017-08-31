package com.example.kamil.otomotonotifier;

/**
 * Created by kamil on 31/08/2017.
 */

public class ParameterFormatter {
    static String format(String parameter) {
        parameter = DiacriticalMarksToEnglishFormatter.formatString(parameter);
        parameter = parameter.replaceAll(" ", "-");
        parameter = parameter.toLowerCase();
        return parameter;
    }
}
