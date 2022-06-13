package com.Endless.commands.gui;

import com.Endless.commands.CMDBase;
import com.Endless.creators.InventoryCreator;
import com.Endless.user.UserRank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class guicommand extends CMDBase {
    public guicommand() {
        super("gui", "gui", new String[0], UserRank.MEMBER);
    }

    public void runCMD() {
        openInventory(getPlayer());
    }

    public static void openInventory(Player player) {
            InventoryCreator test = new InventoryCreator(ChatColor.UNDERLINE + "Gui Test", 54);
            test.addItem(ChatColor.GOLD + ChatColor.BOLD.toString() + "Hi this is a test", 13, Material.IRON_SWORD, new String[] { "This is a very cool test!" });
            test.show(player);
    }
}