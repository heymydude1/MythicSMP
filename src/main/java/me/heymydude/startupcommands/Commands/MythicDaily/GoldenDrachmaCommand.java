package me.heymydude.startupcommands.Commands.MythicDaily;

import me.heymydude.startupcommands.Util.ItemCreator;
import me.heymydude.startupcommands.Util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GoldenDrachmaCommand implements CommandExecutor {

        @Override
        public boolean onCommand (CommandSender sender, Command command, String label, String[]args){

            //checking to see what command was run
            if (label.equalsIgnoreCase("dailyitem")) {

                System.out.println("I have been run by someone.");

                //Check to see if a Player ran the command or something else
                if (sender instanceof Player p) {

                    Msg.send(p, "&4&lDenied Access!!!");




                    ItemCreator item = new ItemCreator(Material.SUNFLOWER, (byte) 64444444);
                    item.setName("&6&lGolden Drachma");
                    item.addLore("&eTrade this to Al for cool items!");
                    item.addLore("");
                    item.addLore("&7&oDrachmas were a currency used by");
                    item.addLore("&7&othe Ancient Greeks long ago, first");
                    item.addLore("&7&ointroduced in 1832 by the Greek");
                    item.addLore("&7&oKing Otto.");
                    item.setUnbreakable(true);
                    p.getInventory().addItem(item.getItemStack());




                } else if (sender instanceof ConsoleCommandSender) {
                    if (args.length == 0) {
                        sender.sendMessage("No target");
                    } else {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (target == null) {
                            sender.sendMessage("Target not online");
                        } else {

                            ItemCreator item = new ItemCreator(Material.SUNFLOWER, (byte) 64444444);
                            item.setName("&6&lGolden Drachma");
                            item.addLore("&eTrade this to Al for cool items!");
                            item.addLore("");
                            item.addLore("&7&oDrachmas were a currency used by");
                            item.addLore("&7&othe Ancient Greeks long ago, first");
                            item.addLore("&7&ointroduced in 1832 by the Greek");
                            item.addLore("&7&oKing Otto.");
                            item.setUnbreakable(true);

                            target.getInventory().addItem(item.getItemStack());
                        }
                    }

                    System.out.println("(step)[it creating item/giving it to u ]");

                } else if (sender instanceof BlockCommandSender) {

                    System.out.println("The command was ran by a command block.");

                }

            }

            //return true if the command was used correctly, but i generally just return true no matter what
            return true;
        }
    }
