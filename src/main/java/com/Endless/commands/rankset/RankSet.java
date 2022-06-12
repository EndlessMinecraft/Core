package com.Endless.commands.rankset;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.UserRank;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

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
                        newRank = UserRank.YT;
                        theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                    } else if (theRank.equals("SR.MOD")) {
                        newRank = UserRank.SR_MOD;
                        theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                    } else {
                        theRank = theRank.substring(0, 1).toUpperCase() + theRank.substring(1);
                        newRank = UserRank.valueOf(theRank);
                    }
                } catch (Exception e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Invalid Rank! Valid Ranks: admin, dev, sr.mod, mod, trainee, builder, yt, member."));
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
}
