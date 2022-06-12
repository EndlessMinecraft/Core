package com.Endless;

import com.Endless.commands.databasecmds.DatabaseCMD;
import com.Endless.commands.filtercmds.FilterCommand;
import com.Endless.commands.gamemode.Gamemode;
import com.Endless.commands.rankset.RankSet;
import com.Endless.database.DatabaseManager;
import com.Endless.server.Config;
import com.Endless.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ELCore extends JavaPlugin
{
    private static ELCore instance;

    FileConfiguration config = getConfig();
    private Config serverConfig;

    public void onEnable(){
        instance = this;
        initCommands();
        initClasses();
        initDatabase();
        this.config.addDefault("server.type", "Lobby");
    }

    private DatabaseManager db;

    private void initDatabase() {
        this.db = new DatabaseManager(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "CORESQL CONNECTED!");
    }

    private void initClasses() {
        getServer().getPluginManager().registerEvents((Listener)new UserManager(), (Plugin)this);
        this.serverConfig = new Config(this);
    }

    public Config getServerConfig() {
        return this.serverConfig;
    }

    public static ELCore getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return this.db;
    }

    public void initCommands(){
        getCommand("rankset").setExecutor((CommandExecutor)new RankSet());
        getCommand("gamemode").setExecutor((CommandExecutor)new Gamemode());
        getCommand("database").setExecutor((CommandExecutor)new DatabaseCMD());
        getCommand("filter").setExecutor((CommandExecutor)new FilterCommand());
    }

}