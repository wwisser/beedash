package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class DropPickListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);
        event.getItem().remove();
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 50.0F, 50.0F);

        /**
        switch (GameManager.getTeam(player)) {
            case "GREEN":
                GameManager.TEAM_GREEN.put(player.getUniqueId(),
                        GameManager.TEAM_GREEN.get(player.getUniqueId()) + 1);
                break;
            case "RED":
                GameManager.TEAM_GREEN.put(player.getUniqueId(),
                        GameManager.TEAM_GREEN.get(player.getUniqueId()) + 1);
                break;
            case "ORANGE":
                GameManager.TEAM_GREEN.put(player.getUniqueId(),
                        GameManager.TEAM_GREEN.get(player.getUniqueId()) + 1);
                break;
            case "BLUE":
                GameManager.TEAM_GREEN.put(player.getUniqueId(),
                        GameManager.TEAM_GREEN.get(player.getUniqueId()) + 1);
                break;
        }**/
    }

}
