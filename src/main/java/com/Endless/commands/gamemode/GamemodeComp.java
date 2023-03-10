package com.Endless.commands.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeComp implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();
        //copy matches of first argument from list
        if (args.length == 1) {
            final List<String> Players1 = new ArrayList<>();
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                Players1.add(pl.getName());
                Collections.sort(completions);
            }
            Collections.sort(completions);
            StringUtil.copyPartialMatches(args[0], Players1, completions);
            return completions;
        }
        return completions;
    }
}
