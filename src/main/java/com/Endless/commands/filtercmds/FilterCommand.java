package com.Endless.commands.filtercmds;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.UserManager;
import com.Endless.user.UserRank;
import org.bukkit.ChatColor;

public class FilterCommand extends CMDBase {

    public FilterCommand() {
        super("filter", "filter add/remove <word>", new String[0], UserRank.ADMIN);
    }

    @Override
    public void runCMD() {
        if (getArgs().length < 2 || getArgs().length > 2) {
            getPlayer().sendMessage(ChatColor.BLUE + "Error > " + ChatColor.GRAY + "Incorrect arguments. Correct arguments: " + getArgs());
            return;
        }
        boolean exists = false;
//        if (UserManager.badWords.contains(getArgs()[0])) {
//            exists = true;
//        }
        if (getArgs()[0].equalsIgnoreCase("add")) {
            if (exists) {
                getPlayer().sendMessage(ChatColor.BLUE + "Error > " + ChatColor.GRAY + "That word is already in the filter.");
                return;
            }
            ELCore.getInstance().getDatabaseManager().addFilter(getArgs()[1].toLowerCase());
            getPlayer().sendMessage(ChatColor.BLUE + "Filter > " + ChatColor.GRAY + "Added " + ChatColor.YELLOW + getArgs()[1] + ChatColor.GRAY + " to the filter.");
        }else if(getArgs()[0].equalsIgnoreCase("remove")){
            if(!exists){
                getPlayer().sendMessage(ChatColor.BLUE + "Error > " + ChatColor.GRAY + "That word is not in the filter.");
                return;
            }

            ELCore.getInstance().getDatabaseManager().deleteFilter(getArgs()[1].toLowerCase());
            getPlayer().sendMessage(ChatColor.BLUE + "Filter > " + ChatColor.GRAY + "Deleted " + ChatColor.YELLOW + getArgs()[1] + ChatColor.GRAY + " from the filter.");

        }


    }
}
