package me.wendelin.beedash.inventories;

import me.wendelin.beedash.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamSelector {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§3Team-Auswahl");

        inventory.setItem(10, new ItemBuilder(Material.WOOL).damage(5).name("§2Team Grün").build());
        inventory.setItem(12, new ItemBuilder(Material.WOOL).damage(14).name("§4Team Rot").build());
        inventory.setItem(14, new ItemBuilder(Material.WOOL).damage(1).name("§6Team Orange").build());
        inventory.setItem(16, new ItemBuilder(Material.WOOL).damage(9).name("§9Team Blau").build());
        inventory.setItem(26, new ItemBuilder(Material.BARRIER).name("§cSchliessen").build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.3F, 0.3F);
    }

}
