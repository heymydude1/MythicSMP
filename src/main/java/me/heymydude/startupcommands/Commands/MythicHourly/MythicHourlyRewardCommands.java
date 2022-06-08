package me.heymydude.startupcommands.Commands.MythicHourly;

import me.heymydude.startupcommands.Managers.MySQLManager;
import me.heymydude.startupcommands.Managers.MythicHourly.MythicHourlyCooldownManager;
import me.heymydude.startupcommands.Managers.MythicHourly.MythicHourlyRewardManager;
import me.heymydude.startupcommands.Managers.MythicHourly.MythicHourlySettingsManager;
import me.heymydude.startupcommands.StartupCommands;
import me.heymydude.startupcommands.Util.Msg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MythicHourlyRewardCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("mythichourly"))
            onCommand(player);
        return true;
    }

    public static void onCommand(Player player) {
        {
            if (player.getWorld().getName().equalsIgnoreCase("Bedwars_Lobby")) {
                if (player.hasPermission("mythichourly.claim")) {
                    String ip = player.getAddress().getAddress().getHostAddress();
                    ip = ip.replace(".", "-");
                    if (MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                        if (!MythicHourlyCooldownManager.getAllowRewardip(player)) {
                            long releaseip;
                            String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                            if (!norewards.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                        String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                        if (!norewards.equalsIgnoreCase("")) {
                            if (StartupCommands.papi)
                                norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                    if (player.hasPermission("mythichourly.claim")) {
                        String ip = player.getAddress().getAddress().getHostAddress();
                        ip = ip.replace(".", "-");
                        if (MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                            if (!MythicHourlyCooldownManager.getAllowRewardip(player)) {
                                long releaseip;
                                String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                                if (!norewards.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                            String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                            if (!norewards.equalsIgnoreCase("")) {
                                if (StartupCommands.papi)
                                    norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                        if (!msg.equalsIgnoreCase("")) {
                            if (StartupCommands.papi)
                                msg = PlaceholderAPI.setPlaceholders(player, msg);
                            msg = msg.replace("%player", player.getName());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }else{

                    if (player.getWorld().getName().equalsIgnoreCase("MythicSMP_the_end"))
                        if (player.hasPermission("mythichourly.claim")) {
                            String ip = player.getAddress().getAddress().getHostAddress();
                            ip = ip.replace(".", "-");
                            if (MythicHourlySettingsManager.getConfig().getBoolean("savetoip")) {
                                if (!MythicHourlyCooldownManager.getAllowRewardip(player)) {
                                    long releaseip;
                                    String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                                    if (!norewards.equalsIgnoreCase("")) {
                                        if (StartupCommands.papi)
                                            norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                                String norewards = MythicHourlySettingsManager.getMsg().getString("no-rewards");
                                if (!norewards.equalsIgnoreCase("")) {
                                    if (StartupCommands.papi)
                                        norewards = PlaceholderAPI.setPlaceholders(player, norewards);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', norewards));
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
                        Msg.send(player, "Only A player Can RUn this ");
                    }
                }
            }
        }
    }
}