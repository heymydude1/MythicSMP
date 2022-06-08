/*package me.mattgd.startupcommands.Main;

import me.mattgd.startupcommands.Commands.MythicDaily.*;
import me.mattgd.startupcommands.Commands.MythicHourly.MythicHourlyAdminCommands;
import me.mattgd.startupcommands.Commands.MythicHourly.MythicHourlyRewardCommands;
import me.mattgd.startupcommands.Commands.MythicHourly.SilverDrachmaCommand;
import me.mattgd.startupcommands.Managers.*;
import me.mattgd.startupcommands.Managers.MythicDaily.MythicDailyJoinManager;
import me.mattgd.startupcommands.Managers.MythicDaily.MythicDailyPAPIExtensions;
import me.mattgd.startupcommands.Managers.MythicDaily.MythicDailySettingsManager;
import me.mattgd.startupcommands.Managers.MythicHourly.MythicHourlyJoinManager;
import me.mattgd.startupcommands.Managers.MythicHourly.MythicHourlyPAPIExtensions;
import me.mattgd.startupcommands.Managers.MythicHourly.MythicHourlySettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    public MythicDailySettingsManager settings = MythicDailySettingsManager.getInstance();
    public MythicHourlySettingsManager setting = MythicHourlySettingsManager.getInstance();
    public static boolean papi;
    public static Connection connection;
    public static String host;
    public static String database;
    public static String username;
    public static String password;
    public int port;


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

    }

    public static Main getInstance() {
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
        Main.connection = connection;
    }

}
*/
