package com.Endless.server;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config {
    private FileConfiguration config;

    private File folder;

    private File file;

    public Config(JavaPlugin plugin) {
        this.folder = plugin.getDataFolder();
        this.file = new File(this.folder, "config.yml");
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);
        createFile();
    }

    private void createFile() {
        try {
            this.folder.mkdir();
            if (!this.file.exists())
                this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addDefaults();
    }

    private void addDefaults() {
        this.config.addDefault("server.type", "hub");
        this.config.addDefault("server.private", Boolean.valueOf(false));
        this.config.addDefault("database.host", "na02-db.cus.mc-panel.net");
        this.config.addDefault("database.user", "db_549961");
        this.config.addDefault("database.password", "b32dd0d82d");
        this.config.addDefault("database.database", "db_549961");
        this.config.options().copyDefaults(true);
        save();
    }

    private void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        if (this.config.contains(path))
            return this.config.getString(path);
        return null;
    }

    public boolean getBoolean(String path) {
        if (this.config.contains(path))
            return this.config.getBoolean(path);
        return false;
    }

    public int getInt(String path) {
        if (this.config.contains(path))
            return this.config.getInt(path);
        return 0;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
