package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GameManager.warmup || GameManager.ended) {
            event.setCancelled(true);
        }
    }

}
