package me.wendelin.beedash;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import me.wendelin.beedash.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {

    public static String prefix = "§e[BeeDash] §a";

    public static Location drop = new Location(Bukkit.getWorld("world"), 318.453, 82, 250.543);

    public static boolean warmup = false;
    public static boolean inGame = false;
    public static boolean ended = false;

    public static HashMap<UUID, String> teams = new HashMap<>();

    public static HashMap<UUID, Integer> TEAM_GREEN = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_RED = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_ORANGE = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_BLUE = new HashMap<>();

    public static int SCORE_GREEN = 0;
    public static int SCORE_RED = 0;
    public static int SCORE_ORANGE = 0;
    public static int SCORE_BLUE = 0;

    public static void startGame() {
        warmup = false;
        inGame = true;

        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(BeeDash.instance, 0L, 3L);
    }

    public static void checkPlayers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() < 4) {
                    Bukkit.broadcastMessage(prefix + "Warte auf Spieler...");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 0.5F, 0.5F);
                    }
                }
            }
        }.runTaskTimer(BeeDash.instance, 0L, 5L);
    }

    public static String getTeamColor(Player player) {
        if (TEAM_GREEN.containsKey(player.getUniqueId())) {
            return "§2";
        } else if (TEAM_RED.containsKey(player.getUniqueId())) {
            return "§4";
        } else if (TEAM_ORANGE.containsKey(player.getUniqueId())) {
            return "§6";
        } else if (TEAM_BLUE.containsKey(player.getUniqueId())) {
            return "§9";
        }
        return "§b";
    }

    public static String getTeam(Player player) {
        if (teams.containsKey(player.getUniqueId())) {
            return teams.get(player.getUniqueId());
        }
        return "Fehler";
    }

    public static int getTeamPoints(String string) {
        int amount = 0;
        switch (string) {
            case "GREEN":
                for (UUID uuid : TEAM_GREEN.keySet()) {
                    amount += TEAM_GREEN.get(uuid);
                }
                return amount;
            case "RED":
                for (UUID uuid : TEAM_RED.keySet()) {
                    amount += TEAM_RED.get(uuid);
                }
                return amount;
            case "ORANGE":
                for (UUID uuid : TEAM_ORANGE.keySet()) {
                    amount += TEAM_ORANGE.get(uuid);
                }
                return amount;
            case "BLUE":
                for (UUID uuid : TEAM_BLUE.keySet()) {
                    amount += TEAM_BLUE.get(uuid);
                }
                return amount;
        }
        return 0;
    }

    public static void dropRandomFlower() {
        int i = new Random().nextInt(6);

        switch (i) {
            case 0:
                ItemStack flower1 = new ItemBuilder(Material.RED_ROSE).damage(1).build();
                drop.getWorld().dropItemNaturally(drop, flower1);
                break;
            case 1:
                ItemStack flower2 = new ItemBuilder(Material.RED_ROSE).damage(2).build();
                drop.getWorld().dropItemNaturally(drop, flower2);
                break;
            case 2:
                ItemStack flower3 = new ItemBuilder(Material.RED_ROSE).damage(4).build();
                drop.getWorld().dropItemNaturally(drop, flower3);
                break;
            case 3:
                ItemStack flower4 = new ItemBuilder(Material.RED_ROSE).build();
                drop.getWorld().dropItemNaturally(drop, flower4);
                break;
            case 4:
                ItemStack flower5 = new ItemBuilder(Material.YELLOW_FLOWER).build();
                drop.getWorld().dropItemNaturally(drop, flower5);
                break;
            case 5:
                ItemStack flower6 = new ItemBuilder(Material.RED_ROSE).damage(8).build();
                drop.getWorld().dropItemNaturally(drop, flower6);
                break;
        }
    }

}
