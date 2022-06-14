package com.Endless.user;

import org.bukkit.ChatColor;

// User rank enum with return values.
public enum UserRank {
    MEMBER("Member", ChatColor.YELLOW, "&f", false),
    YOUTUBE("YouTube", ChatColor.RED, "&cYouTube&f ", false),
    BUILDER("Builder", ChatColor.BLUE, "&9Builder&f ", true),
    TRAINEE("Trainee", ChatColor.LIGHT_PURPLE, "&dTrainee&f ", true),
    MOD("Mod", ChatColor.DARK_PURPLE, "&5Mod&f ", true),
    ADMIN("Admin", ChatColor.DARK_RED, "&4Admin&f ", true),
    DEV("Dev", ChatColor.RED, "&cDev&f ", true),
    OWNER("Owner", ChatColor.RED, "&cOwner&f ", true);

    private String name;

    private ChatColor color;

    private String prefix;

    private boolean staff;

    UserRank(String name, ChatColor color, String prefix, boolean staff) {
        this.name = name;
        this.color = color;
        this.staff = staff;
        this.prefix = prefix;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public boolean isStaff() {
        return this.staff;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
