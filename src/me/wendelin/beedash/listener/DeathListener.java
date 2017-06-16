package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 5));
        } else {
            event.setDeathMessage(
                    GameManager.prefix + GameManager.getTeamColor(victim) + victim.getName()
                            + " §awurde getötet");
        }
        victim.getWorld().playSound(victim.getLocation(), Sound.BLAZE_DEATH, 1.0F, 1.0F);
        victim.getWorld().playEffect(victim.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        event.getDrops().clear();
        GameManager.getTeamHashMap(victim).put(victim.getUniqueId(), 0);
    }

}
