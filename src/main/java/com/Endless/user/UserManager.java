package com.Endless.user;

import com.Endless.ELCore;
import com.Endless.database.ConnectionPoolManager;
import com.Endless.utilities.PlayerUtilities;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class UserManager implements Listener {


//    public static ArrayList<String> badWords = new ArrayList<>();
//
//   public UserManager() {
//        addBadWords();
//    }
//      Filter stuff WIP.
//
//    private void addBadWords() {
//    }

    // PlayerLoginEvent to create player in DB if they don't already exist.
    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {

        try {
            if (!ELCore.getInstance().getDatabaseManager().exists(event.getPlayer().getName())) {
                ELCore.getInstance().getDatabaseManager().createPlayer(event.getPlayer().getUniqueId(), event.getPlayer());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ELCore.getInstance().getDatabaseManager().updateUsername(event.getPlayer());
        ELCore.getInstance().getDatabaseManager().updateLastLogin(event.getPlayer());
    }

    // Set player tablist on join.
    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        PlayerUtilities.sendTabList(e.getPlayer(), "\n" + ChatColor.translateAlternateColorCodes('&', "&cYou are playing ") + ChatColor.RED.toString() + ChatColor.BOLD.toString() +
                "EndlessMC" + "\n", "\n" +
                ChatColor.translateAlternateColorCodes('&', "&cendlessmc.xyz") + "\n");
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {

//        String message = event.getMessage();
//        boolean filter = false;
//
//        for (String badWord : badWords) {
//            if (message.toLowerCase().contains(badWord.toLowerCase())) {
//                filter = true;
//            }
//        }
//
//              More WIP filter stuff
//
//            for (String badWord : badWords) {
//                event.setMessage(message.replaceAll(badWord, convertWord(badWord)));
//            }

        // Chat formatting
        User user = new User(event.getPlayer());
        if (user.getRank() == UserRank.MEMBER) {
            event.setFormat(ChatColor.GRAY + event.getPlayer().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "%2$s");
        } else {
            event.setFormat(user.getRank().getColor().toString() + ChatColor.translateAlternateColorCodes('&', user.getPrefix()) + ChatColor.GRAY + event.getPlayer().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "%2$s");
        }
    }
//              Filter stuff
//    public String convertWord (String word){
//        String replaceWord = StringUtils.repeat("*", word.length());
//        return replaceWord;
//    }

}
