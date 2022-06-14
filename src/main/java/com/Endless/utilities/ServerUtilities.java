package com.Endless.utilities;

import com.Endless.ELCore;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ServerUtilities{
    private static ELCore plugin = ELCore.getInstance();


    public static String format(String s, String m){
        return (ChatColor.RED + s + ChatColor.DARK_RED + " » " + m);
    }

}
