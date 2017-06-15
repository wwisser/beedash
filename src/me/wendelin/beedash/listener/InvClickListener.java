package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import me.wendelin.beedash.inventories.TeamSelector;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InvClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();

        if (GameManager.warmup) {
            event.setCancelled(true);
        } else if (GameManager.ended) {
            event.setCancelled(true);
        }

        if (event.getInventory().getTitle().contains("Team-Auswahl")) {
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            if(currentItem.hasItemMeta()) {
                if (currentItem.getItemMeta().getDisplayName().contains("Schliessen")) {
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                } else if (currentItem.getItemMeta().getDisplayName().contains("Grün")) {
                    selectTeam(player, "GREEN");
                } else if (currentItem.getItemMeta().getDisplayName().contains("Rot")) {
                    selectTeam(player, "RED");
                } else if (currentItem.getItemMeta().getDisplayName().contains("Orange")) {
                    selectTeam(player, "ORANGE");
                } else if (currentItem.getItemMeta().getDisplayName().contains("Blau")) {
                    selectTeam(player, "BLUE");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && player.getItemInHand().getType().equals(
                Material.NETHER_STAR)) {
            TeamSelector.open(player);
        }
    }

    private static void selectTeam(Player player, String string) {
        if (!(GameManager.players.size() < 2)) {
            switch (string) {
                case "GREEN":
                    if (!(GameManager.TEAM_GREEN.size() > 4)) {
                        GameManager.TEAM_GREEN.put(player.getUniqueId(), 0);
                    } else {
                        player.sendMessage(GameManager.prefix + "Dieses Team ist schon voll!");
                    }
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                    break;
                case "RED":
                    if (!(GameManager.TEAM_RED.size() > 4)) {
                        GameManager.TEAM_RED.put(player.getUniqueId(), 0);
                    } else {
                        player.sendMessage(GameManager.prefix + "Dieses Team ist schon voll!");
                    }
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                    break;
                case "ORANGE":
                    if (!(GameManager.TEAM_ORANGE.size() > 4)) {
                        GameManager.TEAM_ORANGE.put(player.getUniqueId(), 0);
                    } else {
                        player.sendMessage(GameManager.prefix + "Dieses Team ist schon voll!");
                    }
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                    break;
                case "BLUE":
                    if (!(GameManager.TEAM_BLUE.size() > 4)) {
                        GameManager.TEAM_BLUE.put(player.getUniqueId(), 0);
                    } else {
                        player.sendMessage(GameManager.prefix + "Dieses Team ist schon voll!");
                    }
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                    break;
                default:
                    player.sendMessage(GameManager.prefix + "Fehler: Falscher Team-Paramenter");
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
                    player.closeInventory();
                    break;
            }
        } else {
            player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.3F, 0.3F);
            player.closeInventory();
            player.sendMessage(
                    GameManager.prefix + "Fehler: Es sind zu wenige Spieler auf dem Server!");
        }
    }

}
