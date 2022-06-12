package com.Endless.commands.gamemode;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.User;
import com.Endless.user.UserRank;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gamemode extends CMDBase {
    private User user;

    public Gamemode() {
        super("gamemode", "gamemode (player) (mode)", new String[]{ "gm" }, UserRank.MOD);
    }

    @Override
    public void runCMD() {
        if (ELCore.getInstance().getServerConfig().getString("server.type").equalsIgnoreCase("games")) {
            this.user = new User(getPlayer());
            if (!this.user.compareRank(UserRank.ADMIN)) {
                getPlayer().sendMessage(ServerUtilities.format("Permission", ChatColor.GRAY + "You do not have permission to run this command."));
                return;
            }
        }

        if ((getArgs()).length == 0) {
            switch(getPlayer().getGameMode()){
                case CREATIVE -> {
                    getPlayer().setGameMode(GameMode.ADVENTURE);
                    getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" + ChatColor.GREEN + " adventure" + ChatColor.GRAY + "."));
                }
                case ADVENTURE, SURVIVAL -> {
                    getPlayer().setGameMode(GameMode.CREATIVE);
                    getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" + ChatColor.GREEN + " creative" + ChatColor.GRAY + "."));
                }

            }
        }
        if ((getArgs().length == 1)) {
            Player other = Bukkit.getPlayer(getArgs()[0]);
            if(other == null){
                getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "That player does not exist."));
                return;
            }
            switch(other.getGameMode()){
                case CREATIVE -> {
                    other.setGameMode(GameMode.ADVENTURE);
                    if(other != getPlayer()) {
                        getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Set " + ChatColor.YELLOW + other.getName() + ChatColor.GRAY +
                                "'s gamemode to" + ChatColor.GREEN + " adventure" + ChatColor.GRAY + "."));
                    }
                    other.sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" + ChatColor.GREEN + " adventure" + ChatColor.GRAY + "."));
                }
                case ADVENTURE, SURVIVAL -> {
                    other.setGameMode(GameMode.CREATIVE);
                    if(other != getPlayer()) {
                        getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Set " + ChatColor.YELLOW + other.getName() + ChatColor.GRAY +
                                "'s gamemode to" + ChatColor.GREEN + " creative" + ChatColor.GRAY + "."));
                    }
                    other.sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" + ChatColor.GREEN + " creative" + ChatColor.GRAY + "."));
                }
            }
        }

    }

    public static class GamemodeComp implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            //create new array
            final List<String> completions = new ArrayList<>();
            //copy matches of first argument from list
            if (args.length == 1) {
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    completions.add(pl.getName());
                }
            }
            return completions;
        }
    }

}
