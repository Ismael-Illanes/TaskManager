package com.task.task.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clase para normalizar espacios en blanco en una cadena de texto
public class StringUtils {
    public static String normalizeWhitespace(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        Pattern pattern = Pattern.compile("\\s+");
        Matcher matcher = pattern.matcher(text.trim());
        return matcher.replaceAll(" ");
    }
}
