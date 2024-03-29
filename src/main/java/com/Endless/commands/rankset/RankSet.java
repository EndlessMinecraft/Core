package com.Endless.commands.rankset;

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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankSet extends CMDBase {
    public RankSet() {
        super("rankset", "rankset <player> <rank>", new String[0], UserRank.ADMIN);
    }

    public void runCMD() {
        if ((getArgs()).length >= 1) {
            UserRank newRank = null;
            String theRank = getArgs()[1].toUpperCase();
            try {
                String player = getArgs()[0];
                if (!ELCore.getInstance().getDatabaseManager().exists(player)) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Player does not exist."));
                    return;
                }
                if (ELCore.getInstance().getDatabaseManager().getRank(getArgs()[0]) == UserRank.OWNER) {
                    if (ELCore.getInstance().getDatabaseManager().getRank(getPlayer()) != UserRank.OWNER) {
                        getPlayer().sendMessage(ServerUtilities.format("Permission", ChatColor.GRAY + "This user's rank cannot be changed in-game."));
                        return;
                    }
                    theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                    newRank = UserRank.valueOf(theRank);
                }
                try {
                    if (theRank.equals("YT")) {
                        newRank = UserRank.YOUTUBE;
                        theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                    } else {
                        theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                        newRank = UserRank.valueOf(theRank);
                    }
                } catch (Exception e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Invalid Rank! Valid Ranks: admin, dev, mod, trainee, builder, yt, member."));
                }
                if (newRank == null)
                    return;
                if (newRank == UserRank.OWNER && (
                        ELCore.getInstance().getDatabaseManager().getRank(getArgs()[0]) != UserRank.OWNER || ELCore.getInstance().getDatabaseManager().getRank(getArgs()[0]) != UserRank.DEV)) {
                    getPlayer().sendMessage(ServerUtilities.format("Permission", ChatColor.GRAY + "This rank cannot be set in-game."));
                    return;
                }
                ELCore.getInstance().getDatabaseManager().setRank(player, newRank);
                getPlayer().sendMessage(ServerUtilities.format("Rank", ChatColor.YELLOW + ELCore.getInstance().getDatabaseManager().getUsername(getArgs()[0]) + ChatColor.GRAY + " has been set to " + newRank.getColor() + newRank.getName() + ChatColor.GRAY + ". They will need to relog for full effects to take action."));
                Player target = Bukkit.getPlayer(getArgs()[0]);
                if (target == null)
                    return;
                target.sendMessage(ServerUtilities.format("Rank", ChatColor.GRAY + "Your rank has been set to " + newRank.getColor() + newRank.getName() + ChatColor.GRAY + ". You will need to relog for full effects to take action."));
                return;
            } catch (SQLException e) {
                getPlayer().sendMessage(ServerUtilities.format("Error",  ChatColor.GRAY + "There was a problem setting rank, please try again."));
            }
        } else {
            getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Invalid arguments. Correct arguments: /rankset <player> <rank>."));
        }
    }

    public static class SetRankComp implements TabCompleter {

        private static final String[] RANKS = { "member", "yt", "builder", "trainee", "mod", "admin", "dev" };
        //create a static array of values

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            //create new array
            final List<String> completions = new ArrayList<>();
            //copy matches of first argument from list
            final List<String> Players1 = new ArrayList<>();
            if(args.length == 1){
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    Collections.sort(completions);
                    Players1.add(pl.getName());
                }
                StringUtil.copyPartialMatches(args[0], Players1, completions);
            }
            if(args.length == 2) {
                completions.clear();
                StringUtil.copyPartialMatches(args[1], Arrays.asList(RANKS), completions);
            }
            return completions;
        }
    }


}


