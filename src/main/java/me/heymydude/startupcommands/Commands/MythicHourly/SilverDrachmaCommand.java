package me.heymydude.startupcommands.Commands.MythicHourly;

import me.heymydude.startupcommands.Util.ItemCreator;
import me.heymydude.startupcommands.Util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SilverDrachmaCommand implements CommandExecutor {

        @Override
        public boolean onCommand (CommandSender sender, Command command, String label, String[]args){

            //checking to see what command was run
            if (label.equalsIgnoreCase("hourlyitem")) {

                System.out.println("I have been run by someone.");

                //Check to see if a Player ran the command or something else
                if (sender instanceof Player p) {

                    Msg.send(p, "&4&lDenied Access!!!");



                   /* ItemCreator item = new ItemCreator(Material.SUNFLOWER, (byte) 64444444);
                    item.setName("&f&lSilver Drachma");
                    item.addLore("&o&7Trade this to Al for cool items!");
                    item.addLore("");
                    item.addLore("&8&oDrachmas were a currency used by");
                    item.addLore("&8&othe Ancient Greeks long ago, first");
                    item.addLore("&8&ointroduced in 1832 by the Greek");
                    item.addLore("&8&oKing Otto.");
                    item.setUnbreakable(true);
                    p.getInventory().addItem(item.getItemStack());*/




                } else if (sender instanceof ConsoleCommandSender) {
                    if (args.length == 0) {
                        sender.sendMessage("No target");
                    } else {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (target == null) {
                            sender.sendMessage("Target not online");
                        } else {

                            ItemCreator item = new ItemCreator(Material.SUNFLOWER, (byte) 64444444);
                            item.setName("&f&lSilver Drachma");
                            item.addLore("&o&7Trade this to Al for cool items!");
                            item.addLore("");
                            item.addLore("&8&oDrachmas were a currency used by");
                            item.addLore("&8&othe Ancient Greeks long ago, first");
                            item.addLore("&8&ointroduced in 1832 by the Greek");
                            item.addLore("&8&oKing Otto.");
                            item.setUnbreakable(true);


                            target.getInventory().addItem(item.getItemStack());
                        }
                    }

                    System.out.println("/hourlyitem");

                } else if (sender instanceof BlockCommandSender) {

                    System.out.println("The command was ran by a command block.");

                }

            }

            //return true if the command was used correctly, but i generally just return true no matter what
            return true;
        }
    }
