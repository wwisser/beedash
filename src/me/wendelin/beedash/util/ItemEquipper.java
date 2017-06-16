package me.wendelin.beedash.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemEquipper {

    public static void equipItems(Player player) {
        player.getInventory().clear();

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(0.08F);

        player.getInventory().setItem(0, new ItemBuilder(Material.STONE_SWORD).enchantment(
                Enchantment.DURABILITY, 5).build());
        player.getInventory().setItem(1, new ItemBuilder(Material.BOW).build());
        player.getInventory().setItem(2,
                new ItemBuilder(Material.ARROW).enchantment(Enchantment.ARROW_KNOCKBACK, 1)
                        .amount(8).build());

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 2));
    }

}
