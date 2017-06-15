package me.wendelin.beedash.inventories;

import me.wendelin.beedash.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamSelector {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 2*9, "§3Team-Auswahl");

        inventory.setItem(1, new ItemBuilder(Material.WOOL).damage(5).name("§2Team Grün").build());
        inventory.setItem(3, new ItemBuilder(Material.WOOL).damage(14).name("§4Team Rot").build());
        inventory.setItem(5, new ItemBuilder(Material.WOOL).damage(1).name("§6Team Orange").build());
        inventory.setItem(7, new ItemBuilder(Material.WOOL).damage(9).name("§9Team Blau").build());
        inventory.setItem(17, new ItemBuilder(Material.BARRIER).name("§cSchliessen").build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.3F, 0.3F);
    }

}
