package com.lizaveta.contactsapp.utils;

public class StringUtils {

    public static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "?";

        String[] parts = name.trim().split("\\s+");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        } else if (parts.length == 1 && !parts[0].isEmpty()) {
            return ("" + parts[0].charAt(0)).toUpperCase();
        }
        return "?";
    }
}