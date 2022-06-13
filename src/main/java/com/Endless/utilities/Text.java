package com.Endless.utilities;

import org.bukkit.ChatColor;

public class Text {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatName(String text) {
        String[] name = text.replaceAll("_", " ").toLowerCase().split(" ");
        StringBuilder message = new StringBuilder();
        for (String s : name) {
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            message.append(s).append(" ");
        }
        return message.toString().trim();
    }

}
