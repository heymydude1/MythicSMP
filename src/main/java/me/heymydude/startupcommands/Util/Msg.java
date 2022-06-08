package me.heymydude.startupcommands.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {
    public static void send(CommandSender sender, String message) {
        send(sender, message, "&a");
    }

    public static void send(CommandSender sender, String message, String prefix) {
        sender.sendMessage(color(prefix + message));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
