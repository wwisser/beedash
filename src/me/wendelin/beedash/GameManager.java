package me.wendelin.beedash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import me.wendelin.beedash.util.ItemBuilder;
import me.wendelin.beedash.util.ItemEquipper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {

    public static String prefix = "§e[BeeDash] §a";

    public static Location SPAWN_GREEN = new Location(Bukkit.getWorld("world"), -213.588, 12, -224.422, -1.4F, 1.6F);
    public static Location SPAWN_ORANGE = new Location(Bukkit.getWorld("world"), -213.472, 12, -108.537, -179.9F, 0.8F);
    public static Location SPAWN_RED = new Location(Bukkit.getWorld("world"), -271.528, 12, -166.455, -90.3F, 0.8F);
    public static Location SPAWN_BLUE = new Location(Bukkit.getWorld("world"), -155.517, 12,
            -166.511, 88.0F, -0.7F);

    /**public static Location DROP_GREEN = new Location(Bukkit.getWorld("world"), -212.528, 4, -168.300);
    public static Location DROP_RED = new Location(Bukkit.getWorld("world"), -214.301, 4, -165.508);
    public static Location DROP_ORANGE = new Location(Bukkit.getWorld("world"), -212.418, 4, -164.400);
    public static Location DROP_BLUE = new Location(Bukkit.getWorld("world"), -210.700, 4, -166.464);**/

    private static Location DROP_SPAWN = new Location(Bukkit.getWorld("world"), -212.486, 12,
            -166.545);
    public static Location SPAWN = new Location(Bukkit.getWorld("world"), 24.500, 5, -1.4);

    public static boolean warmup = false;
    public static boolean inGame = false;
    public static boolean ended = false;

    public static HashMap<UUID, String> teams = new HashMap<>();

    public static HashMap<UUID, Integer> TEAM_GREEN = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_RED = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_ORANGE = new HashMap<>();
    public static HashMap<UUID, Integer> TEAM_BLUE = new HashMap<>();

    public static ArrayList<UUID> players = new ArrayList<>();

    public static int SCORE_GREEN = 0;
    public static int SCORE_RED = 0;
    public static int SCORE_ORANGE = 0;
    public static int SCORE_BLUE = 0;

    public static void startGame() {
        warmup = false;
        inGame = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            teleportPlayer(player);
            ItemEquipper.equipItems(player);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ended) {
                    cancel();
                }
                GameManager.dropRandomFlower();
            }
        }.runTaskTimer(BeeDash.instance, 0, 25L);
    }

    public static void endGame() {
        ended = true;

        Bukkit.broadcastMessage(prefix + "Der Server startet in 10 Sekunden neu.");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer(prefix + "Der Server startet jetzt neu!");
                }

                Bukkit.shutdown();
            }
        }.runTaskLater(BeeDash.instance, 200L);
    }


    public static void checkPlayers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() < 4) {
                    Bukkit.broadcastMessage(prefix + "Warte auf Spieler... (Minimum: 2)");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 0.5F, 0.5F);
                    }
                }
            }
        }.runTaskTimer(BeeDash.instance, 0L, 200L);
    }

    public static HashMap getTeamHashMap(Player player) {
        String teamColor = getTeamColor(player);

        switch (teamColor) {
            case "§2":
                return TEAM_GREEN;
            case "§4":
                return TEAM_RED;
            case "§6":
                return TEAM_ORANGE;
            case "§9":
                return TEAM_BLUE;
        }

        return null;
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

    private static void dropRandomFlower() {
        int i = new Random().nextInt(4);

        switch (i) {
            case 0:
                ItemStack nugget = new ItemBuilder(Material.GOLD_NUGGET).name(new Random().nextInt(500000) + "§c").build();
                DROP_SPAWN.getWorld().dropItem(DROP_SPAWN, nugget);
                break;
            case 1:
                ItemStack ingot = new ItemBuilder(Material.GOLD_INGOT).name(new Random().nextInt(500000) + "§c").build();
                DROP_SPAWN.getWorld().dropItem(DROP_SPAWN, ingot);
                break;
            case 2:
                ItemStack block = new ItemBuilder(Material.GOLD_BLOCK).name(new Random().nextInt(500000) + "§c").build();
                DROP_SPAWN.getWorld().dropItem(DROP_SPAWN, block);
                break;
        }
    }

    public static void teleportPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (TEAM_GREEN.containsKey(uuid)) {
            player.teleport(SPAWN_GREEN);
        }
        if (TEAM_RED.containsKey(uuid)) {
            player.teleport(SPAWN_RED);
        }
        if (TEAM_ORANGE.containsKey(uuid)) {
            player.teleport(SPAWN_ORANGE);
        }
        if (TEAM_BLUE.containsKey(uuid)) {
            player.teleport(SPAWN_BLUE);
        }
    }

}
