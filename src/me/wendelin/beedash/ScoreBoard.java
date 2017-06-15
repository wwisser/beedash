package me.wendelin.beedash;

import org.bukkit.scheduler.BukkitRunnable;

public class ScoreBoard {

    public static void startTask() {
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(BeeDash.instance, 0L, 3L);
    }

}
