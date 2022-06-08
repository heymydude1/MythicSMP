package me.heymydude.startupcommands.Managers.MythicDaily;

import com.google.common.base.Splitter;
import me.heymydude.startupcommands.Managers.MySQLManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.heymydude.startupcommands.StartupCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MythicDailyRewardManager {
    private static Random r = new Random();

    static StartupCommands plugin = (StartupCommands) StartupCommands.getPlugin(StartupCommands.class);

    public static void noReward(Player player) {
        String sound = MythicDailySettingsManager.getConfig().getString("noreward.sound.type");
        int volume = MythicDailySettingsManager.getConfig().getInt("noreward.sound.volume");
        int pitch = MythicDailySettingsManager.getConfig().getInt("noreward.sound.pitch");
        if (MythicDailySettingsManager.getConfig().getBoolean("noreward.sound.enabled"))
            player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
    }

    public static void setReward(final Player player) {
        String sound = MythicDailySettingsManager.getConfig().getString("claim.sound.type");
        int volume = MythicDailySettingsManager.getConfig().getInt("claim.sound.volume");
        int pitch = MythicDailySettingsManager.getConfig().getInt("claim.sound.pitch");
        if (MythicDailySettingsManager.getConfig().getBoolean("claim.sound.enabled"))
            player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
        for (String prize : MythicDailySettingsManager.getConfig().getConfigurationSection("rewards").getKeys(false)) {
            String ip = player.getAddress().getAddress().getHostAddress();
            ip = ip.replace(".", "-");
            long toSet = Math.abs(System.currentTimeMillis())
                    + Math.abs(MythicDailySettingsManager.getConfig().getInt("cooldown"));
            MythicDailySettingsManager.getData().set(String.valueOf(ip) + ".millis", Long.valueOf(toSet));
            MythicDailySettingsManager.getData().set(player.getUniqueId() + ".millis", Long.valueOf(toSet));
            if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled")) {
                MySQLManager.updateCooldownIP(ip, Long.valueOf(toSet).longValue());
                MySQLManager.updateCooldownUUID(player.getUniqueId(), Long.valueOf(toSet).longValue());
            }
            MythicDailySettingsManager.saveData();
            if (!MythicDailySettingsManager.getConfig().getBoolean("rewards." + prize + ".permission")) {
                String claim = MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".claim-message");
                if (!claim.equalsIgnoreCase("")) {
                    if (StartupCommands.papi)
                        claim = PlaceholderAPI.setPlaceholders(player, claim);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', claim));
                }
                if (!MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".broadcast").equalsIgnoreCase("")) {
                    String msg = MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".broadcast");
                    msg = msg.replace("%player", player.getName());
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
                (new BukkitRunnable() {
                    public void run() {
                        if (MythicDailySettingsManager.getConfig().getBoolean("rewards." + prize + ".random")) {
                            List<String> commandList = MythicDailySettingsManager.getConfig()
                                    .getStringList("rewards." + prize + ".commands");
                            int index = MythicDailyRewardManager.r.nextInt(commandList.size());
                            String selectedCommand = commandList.get(index);
                            selectedCommand = selectedCommand.replace("%player", player.getName());
                            if (StartupCommands.papi)
                                selectedCommand = PlaceholderAPI.setPlaceholders(player, selectedCommand);
                            if (selectedCommand.contains(";")) {
                                List<String> split = Splitter.on(";").splitToList(selectedCommand);
                                for (String finalcommand : split)
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), finalcommand);
                            } else {
                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), selectedCommand);
                            }
                        } else {
                            Iterator<String> iterator = MythicDailySettingsManager.getConfig()
                                    .getStringList("rewards." + prize + ".commands").iterator();
                            while (iterator.hasNext()) {
                                String selectedCommand = iterator.next();
                                selectedCommand = selectedCommand.replace("%player", player.getName());
                                if (StartupCommands.papi)
                                    selectedCommand = PlaceholderAPI.setPlaceholders(player, selectedCommand);
                                if (selectedCommand.contains(";")) {
                                    List<String> split = Splitter.on(";").splitToList(selectedCommand);
                                    for (String finalcommand : split)
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), finalcommand);
                                    continue;
                                }
                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), selectedCommand);
                            }
                        }
                    }
                }).runTaskLater((Plugin) plugin, 3L);
                continue;
            }
            if (player.hasPermission("dr." + MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".name"))) {
                String claim = MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".claim-message");
                if (!claim.equalsIgnoreCase("")) {
                    if (StartupCommands.papi)
                        claim = PlaceholderAPI.setPlaceholders(player, claim);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', claim));
                }
                if (!MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".broadcast").equalsIgnoreCase("")) {
                    String msg = MythicDailySettingsManager.getConfig().getString("rewards." + prize + ".broadcast");
                    msg = msg.replace("%player", player.getName());
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
                (new BukkitRunnable() {
                    public void run() {
                        if (MythicDailySettingsManager.getConfig().getBoolean("rewards." + prize + ".random")) {
                            List<String> commandList = MythicDailySettingsManager.getConfig()
                                    .getStringList("rewards." + prize + ".commands");
                            int index = MythicDailyRewardManager.r.nextInt(commandList.size());
                            String selectedCommand = commandList.get(index);
                            selectedCommand = selectedCommand.replace("%player", player.getName());
                            if (StartupCommands.papi)
                                selectedCommand = PlaceholderAPI.setPlaceholders(player, selectedCommand);
                            if (selectedCommand.contains(";")) {
                                List<String> split = Splitter.on(";").splitToList(selectedCommand);
                                for (String finalcommand : split)
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), finalcommand);
                            } else {
                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), selectedCommand);
                            }
                        } else {
                            Iterator<String> iterator = MythicDailySettingsManager.getConfig()
                                    .getStringList("rewards." + prize + ".commands").iterator();
                            while (iterator.hasNext()) {
                                String selectedCommand = iterator.next();
                                selectedCommand = selectedCommand.replace("%player", player.getName());
                                if (StartupCommands.papi)
                                    selectedCommand = PlaceholderAPI.setPlaceholders(player, selectedCommand);
                                if (selectedCommand.contains(";")) {
                                    List<String> split = Splitter.on(";").splitToList(selectedCommand);
                                    for (String finalcommand : split)
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), finalcommand);
                                    continue;
                                }
                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), selectedCommand);
                            }
                        }
                    }
                }).runTaskLater((Plugin) plugin, 3L);
            }
        }
    }
}

