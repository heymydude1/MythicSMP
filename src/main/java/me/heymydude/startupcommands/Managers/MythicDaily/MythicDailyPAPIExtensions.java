package me.heymydude.startupcommands.Managers.MythicDaily;

import me.heymydude.startupcommands.Managers.MySQLManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.heymydude.startupcommands.StartupCommands;
import org.bukkit.entity.Player;

public class MythicDailyPAPIExtensions extends PlaceholderExpansion {
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
        if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled")) {
            if (!MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                releaseip = MySQLManager.getCooldownUUID(player.getUniqueId());
            } else {
                releaseip = MySQLManager.getCooldownIP(ip);
            }
        } else if (!MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
            releaseip = MythicDailySettingsManager.getData().getLong(player.getUniqueId() + ".millis");
        } else {
            releaseip = MythicDailySettingsManager.getData().getLong(String.valueOf(ip) + ".millis");
        }
        long millis = releaseip - current;
        if (identifier.equals("remaining_time"))
            return MythicDailyCooldownManager.getRemainingTime(millis);
        if (identifier.equals("remaining_hours"))
            return MythicDailyCooldownManager.getRemainingHour(millis);
        if (identifier.equals("remaining_minutes"))
            return MythicDailyCooldownManager.getRemainingMin(millis);
        if (identifier.equals("remaining_seconds"))
            return MythicDailyCooldownManager.getRemainingSec(millis);
        if (identifier.equals("player_test_qualification")) {
            boolean output;
            if (!MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                output = MythicDailyCooldownManager.getAllowRewardip(player);
            } else {
                output = MythicDailyCooldownManager.getAllowRewardUUID(player);
            }
            if (output)
                return MythicDailySettingsManager.getMsg().getString("PlaceholderAPI.reward_available");
            return MythicDailySettingsManager.getMsg().getString("PlaceholderAPI.no_rewards");
        }
        if (identifier.equals("player_reward_available"))
            return MythicDailySettingsManager.getMsg().getString("PlaceholderAPI.reward_available");
        if (identifier.equals("player_no_rewards"))
            return MythicDailySettingsManager.getMsg().getString("PlaceholderAPI.no_rewards");
        return null;
    }
}

