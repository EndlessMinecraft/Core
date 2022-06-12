package com.Endless.commands.vanished;

import com.Endless.ELCore;
import com.Endless.commands.CMDBase;
import com.Endless.user.UserRank;
import com.Endless.utilities.ServerUtilities;
import org.bukkit.ChatColor;

import java.sql.SQLException;

public class VanishCommand extends CMDBase {

    public VanishCommand(){
        super("vanish", "vanish", new String[]{ "v" }, UserRank.TRAINEE);
    }


    @Override
    public void runCMD(){
        switch(ELCore.getInstance().getDatabaseManager().getVanished(getPlayer().getName().toString())) {
            case 0:
                try {
                    ELCore.getInstance().getDatabaseManager().setVanish(getPlayer().getName().toString(), 1);
                    Vanish.vanishPlayer(getPlayer(), 0);
                } catch (SQLException e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Something went wrong, please try again or make a bug report."));
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    ELCore.getInstance().getDatabaseManager().setVanish(getPlayer().getName().toString(), 0);
                    getPlayer().sendMessage(ServerUtilities.format("Vanish", ChatColor.GRAY + "You have deactivated vanish."));
                    Vanish.vanishPlayer(getPlayer(), 1);
                } catch (SQLException e) {
                    getPlayer().sendMessage(ServerUtilities.format("Error", ChatColor.GRAY + "Something went wrong, please try again or make a bug report."));
                    e.printStackTrace();
                }
        }
    }
}
