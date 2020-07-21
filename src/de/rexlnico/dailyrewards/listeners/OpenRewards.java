package de.rexlnico.dailyrewards.listeners;

import de.rexlnico.dailyrewards.methodes.ItemBuilder;
import de.rexlnico.dailyrewards.methodes.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OpenRewards implements Listener {

    private static final ItemStack is0 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§4").toItemStack();
    private static final ItemStack is1 = new ItemBuilder(Material.STONE_BUTTON, 1).setName("§4").toItemStack();
    private static final ItemStack is2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7).setName("§4").toItemStack();
    private static final ItemStack is3 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName("§4").toItemStack();

    @EventHandler
    public void on(PlayerInteractAtEntityEvent e) {
        //if (e.getRightClicked().getCustomName().contains("Daily Reward")) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§8* §e§lDaily Reward §8*");
        setInv(inventory);
        Ranks rankP = getRank(e.getPlayer());
        for (Ranks ranks : Ranks.values()) {
            if (rankP == ranks) {
                if (!PlayerJoinEvent.lastRewards.containsKey(e.getPlayer())) {
                    inventory.setItem(ranks.getSlot(), ranks.getItem(false));
                } else {
                    inventory.setItem(ranks.getSlot(), ranks.getItem(isSameDate(PlayerJoinEvent.lastRewards.get(e.getPlayer()))));
                }
            } else {
                inventory.setItem(ranks.getSlot(), ranks.getItem(true));
            }
        }
        e.getPlayer().openInventory(inventory);
        //}
    }

    private void setInv(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 0 || i == 8 || i == 53 || i == 45) {
                inventory.setItem(i, is0);
            } else if (i == 1 || i == 7 || i == 9 || i == 17 || i == 52 || i == 44 || i == 46 || i == 36) {
                inventory.setItem(i, is1);
            } else if ((i >= 12 && i <= 14) || (i >= 20 && i <= 24) || (i >= 29 && i <= 33) || (i >= 39 && i <= 41) || i == 49) {
                inventory.setItem(i, is3);
            } else {
                inventory.setItem(i, is2);
            }
        }
    }

    private Ranks getRank(Player player) {
        if (player.hasPermission("Reward.Team")) return Ranks.TEAM;
        if (player.hasPermission("Reward.YouTuber")) return Ranks.YOUTUBER;
        if (player.hasPermission("Reward.Cloud")) return Ranks.CLOUD;
        if (player.hasPermission("Reward.King")) return Ranks.KING;
        if (player.hasPermission("Reward.Ultra")) return Ranks.ULTRA;
        if (player.hasPermission("Reward.VIP")) return Ranks.VIP;
        return Ranks.SPIELER;
    }

    private boolean isSameDate(long lastTime) {
        Date oldDate = new Date(lastTime);
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String old = simpleDateFormat.format(oldDate);
        String New = simpleDateFormat.format(newDate);
        return old.equals(New);
    }

}
