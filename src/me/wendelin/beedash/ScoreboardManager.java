package me.wendelin.beedash;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("test", "dummy");

        obj.setDisplayName(GameManager.prefix);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore("§aWartelobby");
        score.setScore(Bukkit.getOnlinePlayers().size());

        player.setScoreboard(scoreboard);
    }

    public static void startTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(BeeDash.instance, 0L, 20L);
    }

}
