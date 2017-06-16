package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GameManager.warmup || GameManager.ended) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                Player damager = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();

                if (GameManager.getTeamHashMap(damager)
                        .equals(GameManager.getTeamHashMap(victim))) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
