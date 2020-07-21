package de.rexlnico.dailyrewards.listeners;

import de.rexlnico.dailyrewards.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PlayerJoinEvent implements Listener {

    public static HashMap<Player, Long> lastRewards = new HashMap<>();

    @EventHandler
    public void on(org.bukkit.event.player.PlayerJoinEvent e) throws SQLException {
        Player player = e.getPlayer();
        ResultSet rs = Main.getMySQL().query("SELECT * FROM DailyRewards WHERE UUID='" + player.getUniqueId().toString() + "'");
        if (rs.next()) {
            lastRewards.put(player, rs.getLong("LastReward"));
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent e) throws SQLException {
        Player player = e.getPlayer();
        if (lastRewards.containsKey(player)) {
            ResultSet rs = Main.getMySQL().query("SELECT * FROM DailyRewards WHERE UUID='" + player.getUniqueId() + "'");
            if (rs.next()) {
                PreparedStatement ps = Main.getMySQL().getConnection().prepareStatement("UPDATE DailyRewards SET LastReward=? WHERE UUID=?");
                ps.setString(2, player.getUniqueId().toString());
                ps.setLong(1, lastRewards.get(player));
                ps.execute();
            } else {
                PreparedStatement ps = Main.getMySQL().getConnection().prepareStatement("INSERT INTO DailyRewards VALUES (?, ?)");
                ps.setString(1, player.getUniqueId().toString());
                ps.setLong(2, lastRewards.get(player));
                ps.executeQuery();
            }
        }
    }

}
