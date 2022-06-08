package me.heymydude.startupcommands.Commands.MythicDaily;

import me.heymydude.startupcommands.Managers.*;
import me.heymydude.startupcommands.Managers.MythicDaily.*;

import me.clip.placeholderapi.PlaceholderAPI;
import me.heymydude.startupcommands.StartupCommands;
import me.heymydude.startupcommands.Util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MythicDailyRewardCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("mythicdaily"))
            onCommand(player);
        return true;
    }

    public static void onCommand(Player player) {
        {
            if (player.getWorld().getName().equalsIgnoreCase("Bedwars_Lobby")) {
                if (player.hasPermission("md.claim")) {
                    String ip = player.getAddress().getAddress().getHostAddress();
                    ip = ip.replace(".", "-");
                    if (MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                        if (!MythicDailyCooldownManager.getAllowRewardip(player)) {
                            long releaseip;
                            String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                            if (!norewards.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                        String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                        if (!norewards.equalsIgnoreCase("")) {
                            if (StartupCommands.papi)
                                norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                    if (!msg.equalsIgnoreCase("")) {
                        if (StartupCommands.papi)
                            msg = PlaceholderAPI.setPlaceholders(player, msg);
                        msg = msg.replace("%player", player.getName());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
                }
            else {

                if (player.getWorld().getName().equalsIgnoreCase("MythicSMP_nether"))
                    if (player.hasPermission("md.claim")) {
                        String ip = player.getAddress().getAddress().getHostAddress();
                        ip = ip.replace(".", "-");
                        if (MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                            if (!MythicDailyCooldownManager.getAllowRewardip(player)) {
                                long releaseip;
                                String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                                if (!norewards.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                            String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                            if (!norewards.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                        if (!msg.equalsIgnoreCase("")) {
                            if (StartupCommands.papi)
                                msg = PlaceholderAPI.setPlaceholders(player, msg);
                            msg = msg.replace("%player", player.getName());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }else{

                    if (player.getWorld().getName().equalsIgnoreCase("MythicSMP_the_end"))
                        if (player.hasPermission("md.claim")) {
                            String ip = player.getAddress().getAddress().getHostAddress();
                            ip = ip.replace(".", "-");
                            if (MythicDailySettingsManager.getConfig().getBoolean("savetoip")) {
                                if (!MythicDailyCooldownManager.getAllowRewardip(player)) {
                                    long releaseip;
                                    String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                                    if (!norewards.equalsIgnoreCase("")) {
                                        if (StartupCommands.papi)
                                            norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                                String norewards = MythicDailySettingsManager.getMsg().getString("no-rewards");
                                if (!norewards.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                            if (!msg.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    msg = PlaceholderAPI.setPlaceholders(player, msg);
                                msg = msg.replace("%player", player.getName());
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }

                        }

                    else

                    {

                            System.out.println("Only a player can run this command");
                        Msg.send(player, "Only A pla");
                    }
                }
            }
        }
    }
}