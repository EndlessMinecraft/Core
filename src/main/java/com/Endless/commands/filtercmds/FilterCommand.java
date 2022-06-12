package com.Endless.commands.filtercmds;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.UserManager;
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

public class FilterCommand extends CMDBase {

    public FilterCommand() {
        super("filter", "filter add/remove <word>", new String[0], UserRank.ADMIN);
    }

    @Override
    public void runCMD() {
        if (getArgs().length < 2 || getArgs().length > 2) {
            getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Incorrect arguments. Correct arguments: " + getArgs()));
            return;
        }
        boolean exists = false;
//        if (UserManager.badWords.contains(getArgs()[0])) {
//            exists = true;
//        }
        if (getArgs()[0].equalsIgnoreCase("add")) {
            if (exists) {
                getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "That word is already in the filter."));
                return;
            }
            ELCore.getInstance().getDatabaseManager().addFilter(getArgs()[1].toLowerCase());
            getPlayer().sendMessage(ServerUtilities.format("Filter", ChatColor.GRAY + "Added " + ChatColor.YELLOW + getArgs()[1] + ChatColor.GRAY + " to the filter."));
        }else if(getArgs()[0].equalsIgnoreCase("remove")){
            if(!exists){
                getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "That word is not in the filter."));
                return;
            }

            ELCore.getInstance().getDatabaseManager().deleteFilter(getArgs()[1].toLowerCase());
            getPlayer().sendMessage(ServerUtilities.format("Filter", ChatColor.GRAY + "Deleted " + ChatColor.YELLOW + getArgs()[1] + ChatColor.GRAY + " from the filter."));

        }


    }

    public static class FilterComp implements TabCompleter {
        private static final String[] completes = { "add", "remove" };

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            //create new array
            final List<String> completions = new ArrayList<>();
            //copy matches of first argument from list
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], Arrays.asList(completes), completions);
            }
            return completions;
        }
    }

}
