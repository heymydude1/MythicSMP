package me.heymydude.startupcommands.Managers.MythicHourly;

import me.heymydude.startupcommands.Managers.MySQLManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.heymydude.startupcommands.StartupCommands;
import org.bukkit.entity.Player;

public class MythicHourlyPAPIExtensions extends PlaceholderExpansion {
    static StartupCommands plugin = (StartupCommands) StartupCommands.getPlugin(StartupCommands.class);

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    public String getIdentifier() {
        return "dailyrewards";
    }

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        long releaseip;
        String ip = player.getAddress().getAddress().getHostAddress();
        ip = ip.replace(".", "-");
        long current = System.currentTimeMillis();
        if (MythicHourlySettingsManager.getConfig().getBoolean("mysql.enabled")) {
            if (!MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                releaseip = MySQLManager.getCooldownUUID(player.getUniqueId());
            } else {
                releaseip = MySQLManager.getCooldownIP(ip);
            }
        } else if (!MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
            releaseip = MythicHourlySettingsManager.getData().getLong(player.getUniqueId() + ".millis");
        } else {
            releaseip = MythicHourlySettingsManager.getData().getLong(String.valueOf(ip) + ".millis");
        }
        long millis = releaseip - current;
        if (identifier.equals("remaining_time"))
            return MythicHourlyCooldownManager.getRemainingTime(millis);
        if (identifier.equals("remaining_hours"))
            return MythicHourlyCooldownManager.getRemainingHour(millis);
        if (identifier.equals("remaining_minutes"))
            return MythicHourlyCooldownManager.getRemainingMin(millis);
        if (identifier.equals("remaining_seconds"))
            return MythicHourlyCooldownManager.getRemainingSec(millis);
        if (identifier.equals("player_test_qualification")) {
            boolean output;
            if (!MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                output = MythicHourlyCooldownManager.getAllowRewardip(player);
            } else {
                output = MythicHourlyCooldownManager.getAllowRewardUUID(player);
            }
            if (output)
                return MythicHourlySettingsManager.getMsg().getString("PlaceholderAPI.reward_available");
            return MythicHourlySettingsManager.getMsg().getString("PlaceholderAPI.no_rewards");
        }
        if (identifier.equals("player_reward_available"))
            return MythicHourlySettingsManager.getMsg().getString("PlaceholderAPI.reward_available");
        if (identifier.equals("player_no_rewards"))
            return MythicHourlySettingsManager.getMsg().getString("PlaceholderAPI.no_rewards");
        return null;
    }
}

