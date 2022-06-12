package com.Endless.commands.databasecmds;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.UserRank;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseCMD extends CMDBase {
        ELCore plugin = (ELCore) ELCore.getPlugin(ELCore.class);

  public DatabaseCMD() {
            super("db", "/db lookup <player> | /db stats", new String[0], UserRank.DEV);
        }

        public void runCMD() {
            if ((getArgs()).length == 1 && getArgs()[0].equalsIgnoreCase("stats")) {
                try {
                    getPlayer().sendMessage(ChatColor.RED + "Fetching stats...");
                    long start = System.currentTimeMillis();
                    String playerEntries = ChatColor.WHITE.toString() + ELCore.getInstance().getDatabaseManager().getRowCount("players") + ChatColor.RED;
                    double elapsed = (System.currentTimeMillis() - start) / 1000.0D;
                    String Database = ELCore.getInstance().getServerConfig().getString("database.database");
                    getPlayer().sendMessage(ChatColor.RED + "\nDB Stats (completed in " + ChatColor.WHITE + elapsed + "s" + ChatColor.RED + "): \nDatabase: " + ChatColor.WHITE + Database + ChatColor.RED + "\nPlayer Entries: " + playerEntries + "\n");
                    ELCore.getInstance().getDatabaseManager().getDBTotalSize("players");
                } catch (Exception e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.RED + "There was a problem fetching info, please try again!"));
                }
            } else if ((getArgs()).length == 2 && getArgs()[0].equalsIgnoreCase("lookup")) {
                try {
                    if (!ELCore.getInstance().getDatabaseManager().exists(getArgs()[1])) {
                        getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.RED + "Player does not exist in DB!"));
                    } else {
                        getPlayer().sendMessage(ChatColor.RED + "Fetching info for " + ChatColor.WHITE + ELCore.getInstance().getDatabaseManager().getUsername(getArgs()[1]) + ChatColor.RED + "...");
                        long start = System.currentTimeMillis();
                        String uuid = ChatColor.WHITE + ELCore.getInstance().getDatabaseManager().getUUID(getArgs()[1]) + ChatColor.RED;
                        String username = ChatColor.WHITE + ELCore.getInstance().getDatabaseManager().getUsername(getArgs()[1]) + ChatColor.RED;
                        String rank = ChatColor.WHITE + ELCore.getInstance().getDatabaseManager().getRank(getArgs()[1]).getName() + ChatColor.RED;
                        String lastlogin = ChatColor.WHITE+ ELCore.getInstance().getDatabaseManager().getLastLogin(getArgs()[1]) + ChatColor.RED;
                        double elapsed = (System.currentTimeMillis() - start) / 1000.0D;
                        getPlayer().sendMessage(ChatColor.RED + "\nDB Lookup (completed in " + ChatColor.WHITE + elapsed + "s" + ChatColor.RED + "): \nUUID: " + uuid + "\nUsername: " + username + "\nRank: " + rank + "\nLast Login: " + lastlogin + "\n");
                    }
                } catch (Exception e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.RED + "There was a problem fetching info, please try again!"));
                }
            } else {
                getPlayer().sendMessage(ServerUtilities.format("Error",  ChatColor.RED + "Invalid Arguments! Correct Arguments: " + getUsage()));
            }
        }
    public static class DatabaseComp implements TabCompleter {
        private static final String[] completes = { "stats", "lookup" };
        //create a static array of values

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            //create new array
            final List<String> completions = new ArrayList<>();
            //copy matches of first argument from list
            if(args.length == 2){
                if(args[0].toString() != "stats") {
                    completions.clear();
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        completions.add(pl.getName());
                    }
                }
            }
            if(args.length == 1) {
                completions.clear();
                StringUtil.copyPartialMatches(args[0], Arrays.asList(completes), completions);
            }
            return completions;
        }
}

    }
