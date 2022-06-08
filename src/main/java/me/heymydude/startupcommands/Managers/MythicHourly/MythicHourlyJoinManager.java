package me.heymydude.startupcommands.Managers.MythicHourly;

import me.heymydude.startupcommands.*;
import me.heymydude.startupcommands.Managers.MySQLManager;
import me.heymydude.startupcommands.Managers.UpdateChecker;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MythicHourlyJoinManager implements Listener {
    static StartupCommands plugin = (StartupCommands) StartupCommands.getPlugin(StartupCommands.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (MythicHourlySettingsManager.getConfig().getBoolean("mysql.enabled"))
            MySQLManager.createPlayer(player);
        (new BukkitRunnable() {
            public void run() {
                if (player.getName().equalsIgnoreCase("halflove"))
                    player.sendMessage(ChatColor.GREEN + "Hey that's cool, they use DailyRewards! :) v"
                            + MythicHourlyJoinManager.plugin.getDescription().getVersion());

            }
        }).runTaskLater((Plugin) plugin, 50L);
        if (MythicHourlySettingsManager.getConfig().getBoolean("loginclaim.enabled") && player.hasPermission("hdr.claim")) {
            (new BukkitRunnable() {
                public void run() {
                    if (player.hasPermission("hdr.claim")) {
                        String ip = player.getAddress().getAddress().getHostAddress();
                        ip = ip.replace(".", "-");
                        if (MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                            if (!MythicHourlyCooldownManager.getAllowRewardip(player)) {
                                long releaseip;
                                String noreward = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                                if (!noreward.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        noreward = PlaceholderAPI.setPlaceholders(player, noreward);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noreward));
                                }
                                long current = System.currentTimeMillis();
                                if (MythicHourlySettingsManager.getConfig().getBoolean("mysql.enabled")) {
                                    releaseip = MySQLManager.getCooldownIP(ip);
                                } else {
                                    releaseip = MythicHourlySettingsManager.getData().getLong(String.valueOf(ip) + ".millis");
                                }
                                long millis = releaseip - current;
                                String cdmsg = MythicHourlySettingsManager.getMsg().getString("cooldown-msg");
                                cdmsg = cdmsg.replace("%time%", MythicHourlyCooldownManager.getRemainingTime(millis));
                                cdmsg = cdmsg.replace("%s%", MythicHourlyCooldownManager.getRemainingSec(millis));
                                cdmsg = cdmsg.replace("%m%", MythicHourlyCooldownManager.getRemainingMin(millis));
                                cdmsg = cdmsg.replace("%h%", MythicHourlyCooldownManager.getRemainingHour(millis));
                                cdmsg = cdmsg.replace("%time", MythicHourlyCooldownManager.getRemainingTime(millis));
                                cdmsg = cdmsg.replace("%s", MythicHourlyCooldownManager.getRemainingSec(millis));
                                cdmsg = cdmsg.replace("%m", MythicHourlyCooldownManager.getRemainingMin(millis));
                                cdmsg = cdmsg.replace("%h", MythicHourlyCooldownManager.getRemainingHour(millis));
                                if (!cdmsg.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        cdmsg = PlaceholderAPI.setPlaceholders(player, cdmsg);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cdmsg));
                                }
                                MythicHourlyRewardManager.noReward(player);
                            } else {
                                MythicHourlyRewardManager.setReward(player);
                            }
                        } else if (!MythicHourlyCooldownManager.getAllowRewardUUID(player)) {
                            long releaseip;
                            String noreward = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                            if (!noreward.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    noreward = PlaceholderAPI.setPlaceholders(player, noreward);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noreward));
                            }
                            long current = System.currentTimeMillis();
                            if (MythicHourlySettingsManager.getConfig().getBoolean("mysql.enabled")) {
                                releaseip = MySQLManager.getCooldownUUID(player.getUniqueId());
                            } else {
                                releaseip = MythicHourlySettingsManager.getData().getLong(player.getUniqueId() + ".millis");
                            }
                            long millis = releaseip - current;
                            String cdmsg = MythicHourlySettingsManager.getMsg().getString("cooldown-msg");
                            cdmsg = cdmsg.replace("%time%", MythicHourlyCooldownManager.getRemainingTime(millis));
                            cdmsg = cdmsg.replace("%s%", MythicHourlyCooldownManager.getRemainingSec(millis));
                            cdmsg = cdmsg.replace("%m%", MythicHourlyCooldownManager.getRemainingMin(millis));
                            cdmsg = cdmsg.replace("%h%", MythicHourlyCooldownManager.getRemainingHour(millis));
                            cdmsg = cdmsg.replace("%time", MythicHourlyCooldownManager.getRemainingTime(millis));
                            cdmsg = cdmsg.replace("%s", MythicHourlyCooldownManager.getRemainingSec(millis));
                            cdmsg = cdmsg.replace("%m", MythicHourlyCooldownManager.getRemainingMin(millis));
                            cdmsg = cdmsg.replace("%h", MythicHourlyCooldownManager.getRemainingHour(millis));
                            if (!cdmsg.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    cdmsg = PlaceholderAPI.setPlaceholders(player, cdmsg);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', cdmsg));
                            }
                            MythicHourlyRewardManager.noReward(player);
                        } else {
                            MythicHourlyRewardManager.setReward(player);
                        }
                    } else {
                        String msg = MythicHourlySettingsManager.getMsg().getString("no-permission");
                        msg = msg.replace("%player", player.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            }).runTaskLater((Plugin) plugin, MythicHourlySettingsManager.getConfig().getInt("loginclaim.delay"));
        } else if (player.hasPermission("hdr.claim")
                && (MythicHourlyCooldownManager.getAllowRewardip(player) || MythicHourlyCooldownManager.getAllowRewardUUID(player))) {
            (new BukkitRunnable() {
                public void run() {
                    String available = MythicHourlySettingsManager.getMsg().getString("reward-available");
                    if (!available.equals("")) {
                        if (StartupCommands.papi)
                            available = PlaceholderAPI.setPlaceholders(player, available);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', available));
                    }
                }
            }).runTaskLater((Plugin) plugin, 50L);
        }
    }
}
