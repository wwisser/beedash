package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(GameManager.warmup) {
            event.setCancelled(true);
        } else if (GameManager.inGame) {
            if (!event.getCause().equals(DamageCause.ENTITY_ATTACK) || !event.getCause().equals(DamageCause.PROJECTILE)) {
                event.setCancelled(true);
            }
        } else if (GameManager.ended) {
            event.setCancelled(true);
        }
    }

}
