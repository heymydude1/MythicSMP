package me.heymydude.startupcommands.Managers.MythicDaily;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MythicDailySettingsManager {
    static MythicDailySettingsManager instance = new MythicDailySettingsManager();

    static Plugin p;

    static FileConfiguration config;

    static File cfile;

    static FileConfiguration data;

    static File dfile;

    static FileConfiguration msg;

    static File mfile;

    public static MythicDailySettingsManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "mythicdailyconfig.yml");
        config = p.getConfig();
        config.options().copyDefaults(true);
        //1hr = 3600900
        //4hr = 14401760
        //8hr = 28803520
        //16hr = 57607040
        //20hr = 72000800
        config.addDefault("cooldown", 72000800);
        config.addDefault("savetoip", Boolean.FALSE);
        config.addDefault("regenerate-default-rewards", Boolean.valueOf(true));
        config.addDefault("mysql.enabled", Boolean.valueOf(false));
        config.addDefault("mysql.host-name", "localhost");
        config.addDefault("mysql.port", Integer.valueOf(3306));
        config.addDefault("mysql.database", "example");
        config.addDefault("mysql.username", "root");
        config.addDefault("mysql.password", "password");
        config.addDefault("loginclaim.enabled", Boolean.valueOf(false));
        config.addDefault("loginclaim.delay", Integer.valueOf(3));
        config.addDefault("claim.sound", "");
        config.addDefault("claim.sound.enabled", Boolean.valueOf(true));
        config.addDefault("claim.sound.type", "ENTITY_PLAYER_LEVELUP");
        config.addDefault("claim.sound.volume", Integer.valueOf(1));
        config.addDefault("claim.sound.pitch", Integer.valueOf(1));
        config.addDefault("noreward.sound", "");
        config.addDefault("noreward.sound.enabled", Boolean.valueOf(true));
        config.addDefault("noreward.sound.type", "ITEM_TRIDENT_RIPTIDE_1");
        config.addDefault("noreward.sound.volume", Integer.valueOf(1));
        config.addDefault("noreward.sound.pitch", Integer.valueOf(1));
        List<String> command = new ArrayList<>();
        command.add("dailyitem %player");
        command.add("dailyitem %player");
        List<String> bworld = new ArrayList<>();
        bworld.add("example_world");
        bworld.add("example_world2");
        config.addDefault("rewards.basic.name", "Basic");
        config.addDefault("rewards.basic.permission", Boolean.valueOf(false));
        config.addDefault("rewards.basic.random", Boolean.valueOf(false));
        config.addDefault("rewards.basic.claim-message", "&aRewards&f: You have claimed Your Mythic Daily Reward!");
        config.addDefault("rewards.basic.broadcast", "");
        config.addDefault("rewards.basic.commands", command);

        saveConfig();
        if (!p.getDataFolder().exists())
            p.getDataFolder().mkdir();
        dfile = new File(p.getDataFolder(), "mythicdailydata.yml");
        if (!dfile.exists())
            try {
                dfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        data = (FileConfiguration) YamlConfiguration.loadConfiguration(dfile);
        mfile = new File(p.getDataFolder(), "mythicdailymessages.yml");
        if (!mfile.exists())
            try {
                mfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create messages.yml!");
            }
        msg = (FileConfiguration) YamlConfiguration.loadConfiguration(mfile);
        msg.options().copyDefaults(true);
        msg.addDefault("no-rewards", "&aRewards&f: &fYou do not have any available rewards at the moment.");
        msg.addDefault("cooldown-msg", "&aRewards&f: &fTime until next reward: %time%");
        msg.addDefault("no-permission", "&aRewards&f: &fYou do not have permission to do ");
        msg.addDefault("reward-available", "&aRewards&f: &fYou have unclaimed daily rewards, do &e/reward &fto claim!");
        msg.addDefault("PlaceholderAPI.reward-available", "");
        msg.addDefault("PlaceholderAPI.no-rewards", "No Rewards Available");
        saveMsg();
    }

    public static FileConfiguration getMsg() {
        return msg;
    }

    public void saveMsg() {
        try {
            msg.save(mfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save messages.yml!");
        }
    }

    public void reloadMsg() {
        msg = (FileConfiguration) YamlConfiguration.loadConfiguration(mfile);
    }

    public static FileConfiguration getData() {
        return data;
    }

    public static void saveData() {
        try {
            data.save(dfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        data = (FileConfiguration) YamlConfiguration.loadConfiguration(dfile);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void reloadConfig() {
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }

    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}