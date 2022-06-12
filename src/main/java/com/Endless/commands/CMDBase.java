package com.Endless.commands;

import com.Endless.ELCore;
import com.Endless.user.User;
import com.Endless.user.UserRank;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CMDBase implements CommandExecutor {
    private String name;

    private String[] aliases;

    private String usage;

    private Player player;

    private User user;

    private String[] args;

    private UserRank rank;

    public CMDBase(String name, String usage, String[] aliases, UserRank RANK) {
        this.name = name;
        this.aliases = aliases;
        this.usage = usage;
        this.rank = RANK;
    }

    public void registerCommand() {
        Bukkit.getPluginCommand(this.name).setExecutor(this);
        String[] arrayOfString;
        int j = (arrayOfString = this.aliases).length;
        for (int i = 0; i < j; i++) {
            String alias = arrayOfString[i];
            Bukkit.getPluginCommand(alias).setExecutor(this);
        }
    }

    public abstract void runCMD();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            this.args = args;
            if (!(sender instanceof Player))
                return true;
            this.user = new User((Player)sender);
            Player player = (Player)sender;
            this.player = player;
            if (this.user.compareRank(this.rank)) {
                if (label.equalsIgnoreCase(this.name.toLowerCase())) {
                    runCMD();
                    return false;
                }
                String[] arrayOfString;
                int j = (arrayOfString = this.aliases).length;
                for (int i = 0; i < j; i++) {
                    String alias = arrayOfString[i];
                    if (label.equalsIgnoreCase(alias)) {
                        runCMD();
                        return false;
                    }
                }
            } else {
                player.sendMessage(ServerUtilities.format("Permission",  ChatColor.GRAY + "You do not have permission to run this command."));
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "There was an error performing this command, please contact a staff member with information."));
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getUsage() {
        return this.usage;
    }

    public UserRank getRequiredRank() {
        return this.rank;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public UserRank getPlayerRank() {
        return ELCore.getInstance().getDatabaseManager().getRank(this.player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public String[] getArgs() {
        return this.args;
    }
}
