package me.heymydude.startupcommands.Managers.MythicHourly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MythicHourlySettingsManager {
    static MythicHourlySettingsManager instance = new MythicHourlySettingsManager();

    static Plugin p;

    static FileConfiguration config;

    static File cfile;

    static FileConfiguration data;

    static File dfile;

    static FileConfiguration msg;

    static File mfile;

    public static MythicHourlySettingsManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "mythichourlyconfig.yml");
        config = p.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("cooldown", 3600900);
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
        config.addDefault("claim.sound.enabled", Boolean.TRUE);
        config.addDefault("claim.sound.type", "ENTITY_PLAYER_LEVELUP");
        config.addDefault("claim.sound.volume", 1);
        config.addDefault("claim.sound.pitch", 1);
        config.addDefault("noreward.sound", "");
        config.addDefault("noreward.sound.enabled", Boolean.TRUE);
        config.addDefault("noreward.sound.type", "BLOCK_ANVIL_LAND");
        config.addDefault("noreward.sound.volume", 1);
        config.addDefault("noreward.sound.pitch", 1);
        List<String> command = new ArrayList<>();
        command.add("hourlyitem %player");
        List<String> bworld = new ArrayList<>();
        bworld.add("example_world3");
        bworld.add("example_world4");
        config.addDefault("rewards.basic.name", "Basic");
        config.addDefault("rewards.basic.permission", Boolean.FALSE);
        config.addDefault("rewards.basic.random", Boolean.FALSE);
        config.addDefault("rewards.basic.claim-message", "&aRewards&f: You have claimed Your Mythic Hourly Reward!");
        config.addDefault("rewards.basic.broadcast", "");
        config.addDefault("rewards.basic.commands", command);
        saveConfig();
        if (!p.getDataFolder().exists())
            p.getDataFolder().mkdir();
        dfile = new File(p.getDataFolder(), "mythichourlydata.yml");
        if (!dfile.exists())
            try {
                dfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        data = (FileConfiguration) YamlConfiguration.loadConfiguration(dfile);
        mfile = new File(p.getDataFolder(), "mythichourlymessages.yml");
        if (!mfile.exists())
            try {
                mfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create messages.yml!");
            }
        msg = (FileConfiguration) YamlConfiguration.loadConfiguration(mfile);
        msg.options().copyDefaults(true);
        msg.addDefault("no-rewards", "&aRewards&f: &fYou do not have any  Hourly Rewards to claim at the moment.");
        msg.addDefault("cooldown-msg", "&aRewards&f: &fTime until next reward: %time%");
        msg.addDefault("no-permission", "&aRewards&f: &fYou do not have permission to do ");
        msg.addDefault("reward-available", "&aRewards&f: &fYou have unclaimed hourly Rewards, do &e/mythichourly &fto claim!");
        msg.addDefault("PlaceholderAPI.reward-available", "Unclaimed Rewards Available!");
        msg.addDefault("PlaceholderAPI.no-rewards", "No hourly Rewards Available");
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