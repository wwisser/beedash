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
        Objective obj1 = scoreboard.registerNewObjective("info", "dummy");

        if (GameManager.warmup) {
            obj.setDisplayName(GameManager.prefix);
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score = obj.getScore("§aWartelobby");
            score.setScore(1);

            Score score1 = obj.getScore("§aDein Team: " + GameManager.getTeamColor(player) + "●");
            score1.setScore(0);
        }

        if (GameManager.inGame) {
            obj1.setDisplaySlot(DisplaySlot.BELOW_NAME);
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                obj1.setDisplayName("§bPunkte §7| " + GameManager.getTeamColor(player1) + "●●●");
                obj1.getScore(player1).setScore(
                        (int) GameManager.getTeamHashMap(player1).get(player1.getUniqueId()));
            }

            obj.setDisplayName(GameManager.prefix);
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score blue = obj.getScore("§9Team Blau");
            Score blue_score = obj
                    .getScore("§b" + GameManager.SCORE_BLUE + "§7/§b" + GameManager.WIN());
            blue.setScore(1);
            blue_score.setScore(0);

            Score placeholder3 = obj.getScore("§c");
            placeholder3.setScore(2);

            Score orange = obj.getScore("§6Team Orange");
            Score orange_score = obj
                    .getScore("§e" + GameManager.SCORE_ORANGE + "§7/§e" + GameManager.WIN());
            orange.setScore(4);
            orange_score.setScore(3);

            Score placeholder2 = obj.getScore("§b ");
            placeholder2.setScore(5);

            Score red = obj.getScore("§4Team Rot");
            Score red_score = obj
                    .getScore("§c" + GameManager.SCORE_RED + "§7/§c" + GameManager.WIN());
            red.setScore(7);
            red_score.setScore(6);

            Score placeholder1 = obj.getScore("§3  ");
            placeholder1.setScore(8);

            Score green = obj.getScore("§2Team Grün");
            Score green_score = obj
                    .getScore("§a" + GameManager.SCORE_GREEN + "§7/§a" + GameManager.WIN());
            green.setScore(10);
            green_score.setScore(9);
        }

        if (GameManager.ended) {
            obj.setDisplayName(GameManager.prefix);
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score = obj.getScore("§aSpielende");
            score.setScore(0);
        }

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
        }.runTaskTimer(BeeDash.instance, 0L, 10L);
    }

}
