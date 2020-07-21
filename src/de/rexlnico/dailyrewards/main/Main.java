package de.rexlnico.dailyrewards.main;

import de.rexlnico.dailyrewards.listeners.OpenRewards;
import de.rexlnico.dailyrewards.methodes.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main plugin;
    private PluginManager pm;
    private static MySQL mySQL;
    private File file = new File("plugins/DailyRewards/mysql.data");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @Override
    public void onEnable() {
        plugin = this;
        pm = Bukkit.getPluginManager();

        if (!cfg.contains("host")) {
            cfg.addDefault("host", "localhost");
            cfg.addDefault("port", 3306);
            cfg.addDefault("user", "root");
            cfg.addDefault("password", "password");
            cfg.addDefault("database", "database");
            cfg.options().copyDefaults();
        }

        try {
            mySQL = new MySQL(cfg.getString("host"), cfg.getInt("port"), cfg.getString("user"), cfg.getString("password"), cfg.getString("database"));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§4DailyRewards konnte keine verbindung zur Datenbank aufbauen!");
            pm.disablePlugin(this);
            e.printStackTrace();
        }

        pm.registerEvents(new OpenRewards(), this);


    }

    public static MySQL getMySQL() {
        return mySQL;
    }

    public static Main getPlugin() {
        return plugin;
    }
}
