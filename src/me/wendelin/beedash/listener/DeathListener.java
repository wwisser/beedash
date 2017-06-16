package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        victim.spigot().respawn();

        if (killer != null) {
            event.setDeathMessage(
                    GameManager.prefix + GameManager.getTeamColor(victim) + victim.getName()
                            + " §awurde von "
                            + GameManager.getTeamColor(killer) + killer.getName() + " §agetötet!");
            int killerPoints = (int) GameManager.getTeamHashMap(killer).get(killer.getUniqueId())
                    + (int) GameManager.getTeamHashMap(victim).get(victim.getUniqueId());
            GameManager.getTeamHashMap(killer).put(killer.getUniqueId(), killerPoints);
        } else {
            event.setDeathMessage(
                    GameManager.prefix + GameManager.getTeamColor(victim) + victim.getName()
                            + " §awurde getötet");
        }

        event.getDrops().clear();
        GameManager.getTeamHashMap(victim).put(victim.getUniqueId(), 0);
    }

}
