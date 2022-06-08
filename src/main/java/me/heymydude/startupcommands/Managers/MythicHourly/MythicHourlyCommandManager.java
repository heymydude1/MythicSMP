package me.heymydude.startupcommands.Managers.MythicHourly;

import me.clip.placeholderapi.PlaceholderAPI;
import me.heymydude.startupcommands.StartupCommands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MythicHourlyCommandManager {
    public void adminHelp(Player player) {
        player.sendMessage(ChatColor.BOLD + "DailyRewards Admin Help");
        player.sendMessage(
                ChatColor.YELLOW + "/hdr reload" + ChatColor.WHITE + ChatColor.ITALIC + " Reload all DR files.");
        player.sendMessage(
                ChatColor.YELLOW + "/hdr reset" + ChatColor.WHITE + ChatColor.ITALIC + " Reset your cooldown.");
        player.sendMessage(ChatColor.YELLOW + "/hdr reset (player)" + ChatColor.WHITE + ChatColor.ITALIC
                + " Reset a player's cooldown.");
    }

    public void playerHelp(Player player) {
        String msg = MythicHourlySettingsManager.getMsg().getString("player-help");
        if (msg.equalsIgnoreCase("")) {
            if (StartupCommands.papi)
                msg = PlaceholderAPI.setPlaceholders(player, msg);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}

