package com.Endless.commands.vanished;

import com.Endless.ELCore;
import com.Endless.user.User;
import com.Endless.utilities.PlayerUtilities;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;


public class Vanish implements Listener {

    private static final ArrayList<String> vanished = new ArrayList<>();

    public static void vanishPlayer(Player player, int t) {
        User user = new User(player);
        switch (t) {
            case 0:
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    User user1 = new User(pl);
                    if (user.compareRank(user1.getRank())) {
                        pl.hidePlayer(player);
                    }
                }
                player.sendMessage(ServerUtilities.format("Vanish", ChatColor.GRAY + "You are vanished."));
                if(!vanished.contains(player.getName())) { vanished.add(player.getName()); }
                player.setGameMode(GameMode.CREATIVE);
            case 1:
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    pl.showPlayer(player);
                }
                PlayerUtilities.reset(player);
                if(vanished.contains(player.getName())) { vanished.remove(player.getName()); }
        }

    }

    public boolean isVanished(String name){
        if(vanished.contains(name)){
            return true;
        }else{
            return false;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent event) {
        if(vanished.contains(event.getPlayer().getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event){
        if(ELCore.getInstance().getDatabaseManager().getVanished(event.getPlayer().getName()) == 1){
            vanishPlayer(event.getPlayer(), 0);
            event.setJoinMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quitEvent(PlayerQuitEvent event){
        if(ELCore.getInstance().getDatabaseManager().getVanished(event.getPlayer().getName()) == 1){
            event.setQuitMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChat(AsyncPlayerChatEvent event) {
        if(vanished.contains(event.getPlayer().getName())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ServerUtilities.format("Vanish", ChatColor.GRAY + "Shh! You are currently vanished and unable to talk in chat to " +
                    "avoid giving yourself away."));
        }
    }

}
