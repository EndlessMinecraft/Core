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
import java.util.Collections;
import java.util.List;

import static org.bukkit.GameMode.ADVENTURE;

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

        Player player = getPlayer();
        if ((getArgs().length == 1)) {
            player = Bukkit.getPlayer(getArgs()[0]);
            if (player == null) {
                getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "That player does not exist."));
                return;
            }
        }

        switch (player.getGameMode()) {
            case CREATIVE:
                player.setGameMode(ADVENTURE);
                if (player != getPlayer()) {
                    getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Set " + ChatColor.YELLOW + player.getName() + ChatColor.GRAY +
                            "'s gamemode to" + ChatColor.GREEN + " adventure" + ChatColor.GRAY + "."));
                }
                player.sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" +
                        ChatColor.GREEN + " adventure" + ChatColor.GRAY + "."));
                return;
            case ADVENTURE: case SURVIVAL:
                player.setGameMode(GameMode.CREATIVE);
                if (player != getPlayer()) {
                    getPlayer().sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Set " + ChatColor.YELLOW + player.getName() + ChatColor.GRAY +
                            "'s gamemode to" + ChatColor.GREEN + " creative" + ChatColor.GRAY + "."));
                }
                player.sendMessage(ServerUtilities.format("Gamemode", ChatColor.GRAY + "Your gamemode has been set to" +
                        ChatColor.GREEN + " creative" + ChatColor.GRAY + "."));
            }
        }

    }
