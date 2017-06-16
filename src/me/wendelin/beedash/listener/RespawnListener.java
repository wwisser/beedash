package me.wendelin.beedash.listener;

import me.wendelin.beedash.BeeDash;
import me.wendelin.beedash.GameManager;
import me.wendelin.beedash.util.ItemEquipper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                GameManager.teleportPlayer(player);
                ItemEquipper.equipItems(player);
            }
        }.runTaskLater(BeeDash.instance, 1);
    }

}
