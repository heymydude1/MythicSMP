package me.heymydude.startupcommands;

import me.heymydude.startupcommands.Commands.MythicDaily.*;
import me.heymydude.startupcommands.Commands.MythicHourly.*;
import me.heymydude.startupcommands.Managers.*;
import me.heymydude.startupcommands.Managers.MythicDaily.*;
import me.heymydude.startupcommands.Managers.MythicHourly.*;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * StartupCommands plugin main class.
 * 
 * @author mattgd
 */
public class StartupCommands extends JavaPlugin implements Listener {

	private static StartupCommands instance;
	public MythicDailySettingsManager settings = MythicDailySettingsManager.getInstance();
	public MythicHourlySettingsManager setting = MythicHourlySettingsManager.getInstance();
	public static boolean papi;
	public static Connection connection;
	public static String host;
	public static String database;
	public static String username;
	public static String password;
	public int port;

    /** The CommandManager instance */
    private CommandManager cmdManager;

    public StartupCommands() {
		super();
	}

	/**
	 * This is used for unit testing.
	 * @param loader The PluginLoader to use.
	 * @param description The Description file to use.
	 * @param dataFolder The folder that other data files can be found in.
	 * @param file The location of the plugin.
	 */
	StartupCommands(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
	}

    /**
     * Enable the StartupCommand plugin.
     */
	@Override
	public void onEnable() {

		instance = this;
		Objects.requireNonNull(getCommand("mythicdailyadmin")).setExecutor((CommandExecutor) new MythicDailyAdminCommands(this));
		Objects.requireNonNull(getCommand("mythichourlyadmin")).setExecutor((CommandExecutor) new MythicHourlyAdminCommands(this));
		Objects.requireNonNull(getCommand("mythicdaily")).setExecutor((CommandExecutor) new MythicDailyRewardCommands());
		Objects.requireNonNull(getCommand("mythichourly")).setExecutor((CommandExecutor) new MythicHourlyRewardCommands());
		Objects.requireNonNull(getCommand("dailyitem")).setExecutor(new GoldenDrachmaCommand());
		Objects.requireNonNull(getCommand("hourlyitem")).setExecutor(new SilverDrachmaCommand());

		this.settings.setup((Plugin) this);
		registerEvents();
		if (MythicDailySettingsManager.getConfig().getBoolean("mysql.enabled")) {
			mysqlSetup();
			MySQLManager.createTable();
			for (Player player : Bukkit.getOnlinePlayers())
				MySQLManager.createPlayer(player);
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			papi = true;
			new MythicDailyPAPIExtensions().register();
		} else {
			papi = false;
		}

		this.setting.setup((Plugin) this);
		registerEvents();
		if (MythicHourlySettingsManager.getConfig().getBoolean("mysql.enabled")) {
			mysqlSetup();
			MySQLManager.createTable();
			for (Player player : Bukkit.getOnlinePlayers())
				MySQLManager.createPlayer(player);
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			papi = true;
			new MythicHourlyPAPIExtensions().register();
		} else {
			papi = false;
		}

		new UpdateChecker(this, 16708).getLatestVersion(version -> {
			if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
				getLogger().info("Plugin is up to date.");
			} else {
				getLogger().severe("*** Daily Rewards is Outdated! ***");
				getLogger().severe("*** You're on " + this.getDescription().getVersion() + " while " + version + " is available! ***");
				getLogger().severe("*** Update Here: https://www.spigotmc.org/resources/daily-rewards.16708/ ***");
			}
		});

 		saveDefaultConfig(); // Create default the configuration if config.yml doesn't exist
        cmdManager = new CommandManager(this);

		getCommand("startup").setExecutor(this); // Setup commands

        cmdManager.runStartupCommands(); // Run all startup commands
	}
	public static StartupCommands getInstance() {
		return instance;
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new MythicDailyJoinManager(), this);
		Bukkit.getPluginManager().registerEvents(new MythicHourlyJoinManager(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}



	public void mysqlSetup() {
		host = MythicDailySettingsManager.getConfig().getString("mysql.host-name");
		this.port = MythicDailySettingsManager.getConfig().getInt("mysql.port");
		database = MythicDailySettingsManager.getConfig().getString("mysql.database");
		username = MythicDailySettingsManager.getConfig().getString("mysql.username");
		password = MythicDailySettingsManager.getConfig().getString("mysql.password");

		host = MythicHourlySettingsManager.getConfig().getString("mysql.host-name");
		this.port = MythicHourlySettingsManager.getConfig().getInt("mysql.port");
		database = MythicHourlySettingsManager.getConfig().getString("mysql.database");
		username = MythicHourlySettingsManager.getConfig().getString("mysql.username");
		password = MythicHourlySettingsManager.getConfig().getString("mysql.password");
		try {
			synchronized (this) {
				if (getConnection() != null && !getConnection().isClosed())
					return;
				Class.forName("com.mysql.jdbc.Driver");
				setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + this.port + "/" + database,
						username, password));
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Daily Rewards MySQL: Successfully Connected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Daily Rewards MySQL: Failed To Connected");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Daily Rewards MySQL: Error 'SQLException'");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Daily Rewards MySQL: Your MySQL Configuration Information Is Invalid");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Daily Rewards MySQL: Failed To Connect");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Daily Rewards MySQL: Error 'ClassNotFoundException'");
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		StartupCommands.connection = connection;
	}




	/**
     * Disable the StartupCommands plugin.
     */
	@Override
	public void onDisable() {
        getServer().getScheduler().cancelTasks(this); // Cancel scheduled tasks
		getConfig().options().copyDefaults(true);
	}
	
	/**
	 * Call the appropriate command based on player command input, or
	 * show plugin information or reload the plugin if specified.
	 * @return true always.
	 */
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		MessageManager msg = MessageManager.getInstance();
		
		if (cmd.getName().equalsIgnoreCase("startup")) {
			if (args.length == 1) {
			    String subCmd = args[0];

				if (subCmd.equalsIgnoreCase("view")) {
					StringBuilder commandList = new StringBuilder();
					
					if (cmdManager.getCommands().isEmpty()) {
						commandList.append("There are currently no startup commands configured.");
					} else {
						commandList.append(msg.messageTitle("Startup Commands", ChatColor.AQUA, ChatColor.YELLOW));
						
						int index = 1;
						String cmdStr;
						for (Command command : cmdManager.getCommands()) {
							cmdStr = String.format("%n&e%s &7- &a%s &7(%ds delay)", index, command.getCommand(), command.getDelay());
							cmdStr = cmdStr.replaceAll("\\r", "");
							commandList.append(cmdStr);
							index++;
						}
						
						commandList.append(msg.messageTrail(ChatColor.YELLOW)); // Add message trail
					}
					
					MessageManager.getInstance().info(sender, commandList.toString()); // Send the message to the sender
				} else if (subCmd.equalsIgnoreCase("help") || subCmd.equalsIgnoreCase("?")) {
					msg.good(sender, helpMessage());
				} else if (subCmd.equalsIgnoreCase("run")) {
					cmdManager.runStartupCommands();
				} else {
					msg.severe(sender, "Invalid command usage. Type /startup help for proper usage information.");
				}
			} else if (args.length > 1) {
                String subCmd = args[0];

				if (subCmd.equalsIgnoreCase("add") || subCmd.equalsIgnoreCase("create")) {
					String cmdStr;
					int delay;
					boolean hasDelay = false;
					
					// Check if a delay was provided
                    if (isInteger(args[1])) {
                        delay = Integer.parseInt(args[1]);
                        hasDelay = true;
                    } else {
                        delay = Command.DEFAULT_DELAY;
                    }

                    // Calculate where the commandString should start to be assembled
                    final int start = 1 + (hasDelay ? 1 : 0);
					cmdStr = msg.assembleMessage(args, start, args.length);
					
					try {
						Command command = new Command(cmdStr, delay);
                        cmdManager.addCommand(command);
						msg.info(sender, "Added startup command with delay &7" + delay + "s&e: &a" + cmdStr);
					} catch (IllegalArgumentException e) {
						msg.severe(sender, e.getMessage());
					}
				} else if (subCmd.equalsIgnoreCase("remove") || subCmd.equalsIgnoreCase("delete")) {
					String removeStr = msg.assembleMessage(args, 1, args.length);
					
					try {
						msg.info(sender, "Removed startup command: &a" + cmdManager.removeCommand(removeStr));
					} catch (IllegalArgumentException e) {
						msg.severe(sender, e.getMessage());
					}
                } else if (subCmd.equalsIgnoreCase("setdelay")) {
                    // Check if an index and delay were provided
                    if (args.length != 3 || !(isInteger(args[1]) && isInteger(args[2]))) {
                        msg.severe(sender, "Usage: /sc setdelay <command ID> <delay in seconds>");
                    } else {
                        int index = Integer.parseInt(args[1]);
                        int delay = Integer.parseInt(args[2]);

                        try {
                            cmdManager.setCommandDelay(index, delay);
                            msg.info(sender, "Set the delay for command &7#" + index + " &eto &7" + delay + "s&e.");
                        } catch (IllegalArgumentException ex) {
                            msg.severe(sender, ex.getMessage());
                        }
                    }
                } else {
				    msg.severe(sender, "Invalid command usage. Type /startup help for proper usage information.");
                }
			} else {
				msg.good(sender, helpMessage());
			}
		}
		
		return true;
	}
	
	/**
	 * Returns a String with the StartupCommands help message.
	 * @return a String with the StartupCommands help message.
	 */
	private String helpMessage() {
		MessageManager msg = MessageManager.getInstance();
		return msg.messageTitle("StartupCommands Help", ChatColor.AQUA, ChatColor.YELLOW)
				+ "\n&a/sc view &7- &aview the active startup commands and their delay"
				+ "\n&a/sc add <command string> <delay> &7- &aadd a startup command"
				+ "\n&a/sc remove <command ID or exact command string> &7- &aremove a startup command"
                + "\n&a/sc setdelay <command ID> <delay in seconds> &7- &aset a startup command's delay"
				+ msg.messageTrail(ChatColor.YELLOW); // Add message trail
	}

    /**
     * Returns true if the String s can be parsed as an integer, false otherwise.
     * @param s The String to parse to an Integer.
     * @return true if the String s can be parsed as an integer, false otherwise.
     */
    static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
			return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
}
