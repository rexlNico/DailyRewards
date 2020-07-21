package de.rexlnico.dailyrewards.methodes;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum Ranks {

    SPIELER("Spieler", "§8» §7%NAME% §fBelohnung§8.", 1000, "/givecase %PLAYER% %NAME% 1 §8» §d%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    VIP("VIP", "§8» §7%NAME% §fBelohnung§8.", 1500, "/givecase %PLAYER% %NAME% 1 §8» §b%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    ULTRA("Ultra", "§8» §7%NAME% §fBelohnung§8.", 2000, "/givecase %PLAYER% %NAME% 1 §8» §b%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    KING("King", "§8» §7%NAME% §fBelohnung§8.", 2500, "/givecase %PLAYER% %NAME% 1 §8» §d%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    CLOUD("Cloud", "§8» §7%NAME% §fBelohnung§8.", 5000, "/givecase %PLAYER% %NAME% 1 §8» §d%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    YOUTUBER("YouTuber", "§8» §7%NAME% §fBelohnung§8.", 2500, "/givecase %PLAYER% %NAME% 1 §8» §d%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate"),
    TEAM("Team", "§8» §7%NAME% §fBelohnung§8.", 1000, "/givecase %PLAYER% %NAME% 1 §8» §d%NAME% §7Crate", "§6", "§8  • §e%MONEY% §7Dollar", "§8  • §d%NAME% §7Crate");

    private String name;
    private String title;
    private int money;
    private String command;
    private String[] lore;

    Ranks(String name, String title, int money, String command, String... lore) {
        this.name = name;
        this.title = title;
        this.money = money;
        this.command = command;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title.replace("%NAME%", getName());
    }

    public int getMoney() {
        return money;
    }

    public String getCommand(Player player) {
        return command.replace("%PLAYER%", player.getName()).replace("%NAME%", getName());
    }

    public ArrayList<String> getLore() {
        ArrayList<String> list = new ArrayList<>();
        for (String l : lore) {
            list.add(l.replace("%MONEY%", "" + getMoney()).replace("%NAME%", getName()));
        }
        return list;
    }

    public int getSlot() {
        if(this == SPIELER) return 21;
        if(this == VIP) return 22;
        if(this == ULTRA) return 23;
        if(this == KING) return 30;
        if(this == CLOUD) return 31;
        if(this == YOUTUBER) return 32;
        if(this == TEAM) return 40;
        return 0;
    }

    public ItemStack getItem(boolean claimed) {
        return new ItemBuilder(claimed ? Material.MINECART : Material.STORAGE_MINECART, 1).setLore(getLore()).setName(getTitle()).toItemStack();
    }

}
