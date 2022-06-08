package me.heymydude.startupcommands.Managers.MythicDaily;

import me.heymydude.startupcommands.Managers.MySQLManager;
import me.heymydude.startupcommands.Managers.UpdateChecker;
import me.clip.placeholderapi.PlaceholderAPI;
import me.heymydude.startupcommands.StartupCommands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MythicDailyJoinManager implements Listener {
    static StartupCommands plugin = (StartupCommands) StartupCommands.getPlugin(StartupCommands.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled"))
            MySQLManager.createPlayer(player);
        (new BukkitRunnable() {
            public void run() {
                if (player.getName().equalsIgnoreCase("halflove"))
                    player.sendMessage(ChatColor.GREEN + "Hey that's cool, they use DailyRewards! :) v"
                            + MythicDailyJoinManager.plugin.getDescription().getVersion());
            }

        }).runTaskLater((Plugin) plugin, 50L);
        if (MythicDailySettingsManager.getConfig().getBoolean("loginclaim.enabled") && player.hasPermission("dr.claim")) {
            (new BukkitRunnable() {
                public void run() {
                    if (player.hasPermission("dr.claim")) {
                        String ip = player.getAddress().getAddress().getHostAddress();
                        ip = ip.replace(".", "-");
                        if (MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                            if (!MythicDailyCooldownManager.getAllowRewardip(player)) {
                                long releaseip;
                                String noreward = MythicDailySettingsManager.getMsg().getString("no-rewards");
                                if (!noreward.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        noreward = PlaceholderAPI.setPlaceholders(player, noreward);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noreward));
                                }
                                long current = System.currentTimeMillis();
                                if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled")) {
                                    releaseip = MySQLManager.getCooldownIP(ip);
                                } else {
                                    releaseip = MythicDailySettingsManager.getData().getLong(String.valueOf(ip) + ".millis");
                                }
                                long millis = releaseip - current;
                                String cdmsg = MythicDailySettingsManager.getMsg().getString("cooldown-msg");
                                cdmsg = cdmsg.replace("%time%", MythicDailyCooldownManager.getRemainingTime(millis));
                                cdmsg = cdmsg.replace("%s%", MythicDailyCooldownManager.getRemainingSec(millis));
                                cdmsg = cdmsg.replace("%m%", MythicDailyCooldownManager.getRemainingMin(millis));
                                cdmsg = cdmsg.replace("%h%", MythicDailyCooldownManager.getRemainingHour(millis));
                                cdmsg = cdmsg.replace("%time", MythicDailyCooldownManager.getRemainingTime(millis));
                                cdmsg = cdmsg.replace("%s", MythicDailyCooldownManager.getRemainingSec(millis));
                                cdmsg = cdmsg.replace("%m", MythicDailyCooldownManager.getRemainingMin(millis));
                                cdmsg = cdmsg.replace("%h", MythicDailyCooldownManager.getRemainingHour(millis));
                                if (!cdmsg.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        cdmsg = PlaceholderAPI.setPlaceholders(player, cdmsg);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cdmsg));
                                }
                                MythicDailyRewardManager.noReward(player);
                            } else {
                                MythicDailyRewardManager.setReward(player);
                            }
                        } else if (!MythicDailyCooldownManager.getAllowRewardUUID(player)) {
                            long releaseip;
                            String noreward = MythicDailySettingsManager.getMsg().getString("no-rewards");
                            if (!noreward.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    noreward = PlaceholderAPI.setPlaceholders(player, noreward);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noreward));
                            }
                            long current = System.currentTimeMillis();
                            if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled")) {
                                releaseip = MySQLManager.getCooldownUUID(player.getUniqueId());
                            } else {
                                releaseip = MythicDailySettingsManager.getData().getLong(player.getUniqueId() + ".millis");
                            }
                            long millis = releaseip - current;
                            String cdmsg = MythicDailySettingsManager.getMsg().getString("cooldown-msg");
                            cdmsg = cdmsg.replace("%time%", MythicDailyCooldownManager.getRemainingTime(millis));
                            cdmsg = cdmsg.replace("%s%", MythicDailyCooldownManager.getRemainingSec(millis));
                            cdmsg = cdmsg.replace("%m%", MythicDailyCooldownManager.getRemainingMin(millis));
                            cdmsg = cdmsg.replace("%h%", MythicDailyCooldownManager.getRemainingHour(millis));
                            cdmsg = cdmsg.replace("%time", MythicDailyCooldownManager.getRemainingTime(millis));
                            cdmsg = cdmsg.replace("%s", MythicDailyCooldownManager.getRemainingSec(millis));
                            cdmsg = cdmsg.replace("%m", MythicDailyCooldownManager.getRemainingMin(millis));
                            cdmsg = cdmsg.replace("%h", MythicDailyCooldownManager.getRemainingHour(millis));
                            if (!cdmsg.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    cdmsg = PlaceholderAPI.setPlaceholders(player, cdmsg);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', cdmsg));
                            }
                            MythicDailyRewardManager.noReward(player);
                        } else {
                            MythicDailyRewardManager.setReward(player);
                        }
                    } else {
                        String msg = MythicDailySettingsManager.getMsg().getString("no-permission");
                        msg = msg.replace("%player", player.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            }).runTaskLater((Plugin) plugin, MythicDailySettingsManager.getConfig().getInt("loginclaim.delay"));
        } else if (player.hasPermission("dr.claim")
                && (MythicDailyCooldownManager.getAllowRewardip(player) || MythicDailyCooldownManager.getAllowRewardUUID(player))) {
            (new BukkitRunnable() {
                public void run() {
                    String available = MythicDailySettingsManager.getMsg().getString("reward-available");
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
