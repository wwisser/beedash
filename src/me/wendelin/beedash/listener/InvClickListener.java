package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getTitle().contains("Team")) {
            event.setCancelled(true);

            ItemStack currentItem = event.getCurrentItem();

            if (currentItem.getItemMeta().getDisplayName().contains("Schliessen")) {
                player.closeInventory();
            } else if (currentItem.getItemMeta().getDisplayName().contains("Grün")) {

            } else if (currentItem.getItemMeta().getDisplayName().contains("Rot")) {

            } else if (currentItem.getItemMeta().getDisplayName().contains("Orange")) {

            } else if (currentItem.getItemMeta().getDisplayName().contains("Blau")) {

            }
        }
    }

    private static void selectTeam(Player player, String string) {
        if (!(GameManager.players.size() < 2)) {
            switch (string) {
                case "GREEN":
                    GameManager.TEAM_GREEN.put(player.getUniqueId(), 0);
                    break;
                case "RED":
                    GameManager.TEAM_RED.put(player.getUniqueId(), 0);
                    break;
                case "ORANGE":
                    GameManager.TEAM_ORANGE.put(player.getUniqueId(), 0);
                    break;
                case "BLUE":
                    GameManager.TEAM_BLUE.put(player.getUniqueId(), 0);
                    break;
                default:
                    player.sendMessage(GameManager.prefix + "Fehler: Falscher Team-Paramenter");
                    break;
            }
        } else {
            player.sendMessage(GameManager.prefix + "Fehler: Es sind zu wenige Spieler auf dem Server!");
        }
    }

}
