package me.dev.creeper.bungeeplugin;

//Author : Dev-Creeper

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public final class BungeePlugin extends Plugin implements Listener{

    public Configuration config;
    private static BungeePlugin instance;
    private static ProxyServer proxy;

    public BungeePlugin() {
    }

    public static BungeePlugin getInstance() {
        return instance;
    }

    public static ProxyServer getProxy_() {
        return proxy;
    }

    public void onEnable() { //Load Plugin and create config.yml
        this.saveDefaultConfig();
        this.reloadConfig();
        instance = this;
        //Get Plugin config 
        //if null,getConfig reload
        if (this.getConfig().get("lobby-server") == null) {
            this.getConfig().set("lobby-server", "lobby");
            this.saveConfig();
            this.reloadConfig();
        }
        if (this.getConfig().get("again-server-bedwars") == null) {
            this.getConfig().set("again-server-bedwars", "again");
            this.saveConfig();
            this.reloadConfig();
        }

        if (this.getConfig().get("again-server-uhc") == null) {
            this.getConfig().set("again-server-uhc", "again_uhc");
            this.saveConfig();
            this.reloadConfig();
        }

        if (this.getConfig().get("lobby-server").equals("lobby")) {
            this.getLogger().info(ChatColor.YELLOW + "[警告] " + ChatColor.GREEN + "你退出游戏传送的大厅名字为lobby(可能你没有去设置),如果不存在则会报错！");
        }

        if (this.getConfig().get("again-server-bedwars").equals("again")) {
            this.getLogger().info(ChatColor.YELLOW + "[警告] " + ChatColor.GREEN + "你起床战争选择下一局游戏传送的服务器名字为again(可能你没有去设置),如果不存在则会报错！");
        }

        if (this.getConfig().get("again-server-uhc").equals("again_uhc")) {
            this.getLogger().info(ChatColor.YELLOW + "[警告] " + ChatColor.GREEN + "你uhc选择下一局游戏传送的服务器名字为again(可能你没有去设置),如果不存在则会报错！");
        }
        proxy = this.getProxy();
        //Register Plugin Event
        this.getProxy().getPluginManager().registerListener(this, new Lisetener());
        this.getProxy().getPluginManager().registerListener(this, this);
    }

    public void onDisable() {
    }

    public void saveDefaultConfig() {
        File dir = this.getDataFolder();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, "config.yml");
        if (!file.exists()) {
            try {
                InputStream in = this.getResourceAsStream("config.yml");

                try {
                    Files.copy(in, file.toPath(), new CopyOption[0]);
                } catch (Throwable var7) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }
                    throw var7;
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }

    }

    public void reloadConfig() {
        File file = new File(this.getDataFolder(), "config.yml");

        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void saveConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, file);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    public Configuration getConfig() {
        return this.config;
    }
}
