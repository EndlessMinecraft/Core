package com.Endless.user;

import org.bukkit.ChatColor;

// User rank enum with return values.
public enum UserRank {
    MEMBER("Member", ChatColor.YELLOW, "&f", false),
    YT("YT", ChatColor.GOLD, "&6[YT]&f ", false),
    BUILDER("Builder", ChatColor.BLUE, "&9[BUILDER]&f ", true),
    TRAINEE("Trainee", ChatColor.LIGHT_PURPLE, "&d[TRAINEE]&f ", true),
    MOD("Mod", ChatColor.DARK_PURPLE, "&5[MOD]&f ", true),
    SR_MOD("Sr.Mod", ChatColor.DARK_PURPLE, "&5[SR.MOD]&f ", true),
    ADMIN("Admin", ChatColor.DARK_RED, "&4[ADMIN]&f ", true),
    DEV("Dev", ChatColor.RED, "&c[DEV]&f ", true),
    OWNER("Owner", ChatColor.RED, "&c[OWNER]&f ", true);

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
