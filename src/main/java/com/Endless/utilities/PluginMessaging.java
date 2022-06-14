package com.Endless.utilities;

import com.Endless.ELCore;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginMessaging implements PluginMessageListener {
    private static ELCore plugin = ELCore.getInstance();

    private String server = "";

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        String str = "";
        if (!channel.equals("BungeeCord"))
            return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("getServer")) {
            server = in.readUTF();
        }
    }

    public void connect(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage((Plugin) plugin, "BungeeCord", output.toByteArray());
    }

    public void getServer(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("getServer");
        player.sendPluginMessage((Plugin) plugin, "BungeeCord", output.toByteArray());
    }

    public String getServerName(){
        return server;
    }
}