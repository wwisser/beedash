package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import me.wendelin.beedash.util.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
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
        if (!((int) GameManager.getTeamHashMap(player).get(player.getUniqueId()) >= 15)) {
            event.getItem().remove();
            GameManager.getTeamHashMap(player).put(player.getUniqueId(),
                    (int) GameManager.getTeamHashMap(player).get(player.getUniqueId())
                            + 1);
            player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 0.3F, 0.3F);
        } else {
            new Title("", "§cDu kannst nicht noch mehr tragen!").send(player);
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            arrow.remove();
        }
    }

}
