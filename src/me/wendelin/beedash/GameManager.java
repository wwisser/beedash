package me.wendelin.beedash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import me.wendelin.beedash.util.ActionBar;
import me.wendelin.beedash.util.ItemBuilder;
import me.wendelin.beedash.util.ItemEquipper;
import me.wendelin.beedash.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
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

    public static int WIN = 100;

    public static void startGame() {
        warmup = false;
        inGame = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            teleportPlayer(player);
            ItemEquipper.equipItems(player);
        }

        statusTask();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ended) {
                    cancel();
                }
                GameManager.dropRandomFlower();
                DROP_SPAWN.getWorld().playEffect(DROP_SPAWN, Effect.LAVA_POP, 10);
            }
        }.runTaskTimer(BeeDash.instance, 0, 25L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ended) {
                    cancel();
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()
                            .equals(Material.WOOL)) {
                        switch (player.getLocation().getBlock().getRelative(BlockFace.DOWN)
                                .getBiome()) {
                            case ROOFED_FOREST:
                                if (GameManager.getTeamHashMap(player)
                                        .equals(GameManager.TEAM_RED)) {
                                    if (player.isSneaking() &&
                                            (int) GameManager.getTeamHashMap(player)
                                                    .get(player.getUniqueId()) >= 1) {
                                        GameManager.getTeamHashMap(player).put(player.getUniqueId(),
                                                (int) GameManager.getTeamHashMap(player)
                                                        .get(player.getUniqueId()) - 1);
                                        GameManager.SCORE_RED++;
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP,
                                                10.3F, 10.3F);
                                        new Title("", "§e+1 Punkt").send(player);
                                        player.getWorld().playEffect(player.getLocation(),
                                                Effect.COLOURED_DUST, 50);
                                        if (SCORE_RED == WIN) {
                                            endGame();
                                            titleBroadcast("§4Team Rot hat gewonnen!",
                                                    "§eHerzlichen Glückwunsch!");
                                        }
                                    }
                                }
                                break;
                            case FOREST:
                                if (GameManager.getTeamHashMap(player)
                                        .equals(GameManager.TEAM_GREEN)) {
                                    if (player.isSneaking() &&
                                            (int) GameManager.getTeamHashMap(player)
                                                    .get(player.getUniqueId()) >= 1) {
                                        GameManager.getTeamHashMap(player).put(player.getUniqueId(),
                                                (int) GameManager.getTeamHashMap(player)
                                                        .get(player.getUniqueId()) - 1);
                                        GameManager.SCORE_GREEN++;
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP,
                                                10.3F, 10.3F);
                                        new Title("", "§e+1 Punkt").send(player);
                                        player.getWorld().playEffect(player.getLocation(),
                                                Effect.COLOURED_DUST, 50);
                                        if (SCORE_GREEN == WIN) {
                                            endGame();
                                            titleBroadcast("§2Team Grün hat gewonnen!",
                                                    "§eHerzlichen Glückwunsch!");
                                        }
                                    }
                                }
                                break;
                            case SAVANNA:
                                if (GameManager.getTeamHashMap(player)
                                        .equals(GameManager.TEAM_BLUE)) {
                                    if (player.isSneaking() &&
                                            (int) GameManager.getTeamHashMap(player)
                                                    .get(player.getUniqueId()) >= 1) {
                                        GameManager.getTeamHashMap(player).put(player.getUniqueId(),
                                                (int) GameManager.getTeamHashMap(player)
                                                        .get(player.getUniqueId()) - 1);
                                        GameManager.SCORE_BLUE++;
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP,
                                                10.3F, 10.3F);
                                        new Title("", "§e+1 Punkt").send(player);
                                        player.getWorld().playEffect(player.getLocation(),
                                                Effect.COLOURED_DUST, 50);
                                        if (SCORE_BLUE == WIN) {
                                            endGame();
                                            titleBroadcast("§9Team Blau hat gewonnen!",
                                                    "§eHerzlichen Glückwunsch!");
                                        }
                                    }
                                }
                                break;
                            case BEACH:
                                if (GameManager.getTeamHashMap(player)
                                        .equals(GameManager.TEAM_ORANGE)) {
                                    if (player.isSneaking() &&
                                            (int) GameManager.getTeamHashMap(player)
                                                    .get(player.getUniqueId()) >= 1) {
                                        GameManager.getTeamHashMap(player).put(player.getUniqueId(),
                                                (int) GameManager.getTeamHashMap(player)
                                                        .get(player.getUniqueId()) - 1);
                                        GameManager.SCORE_ORANGE++;
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP,
                                                10.3F, 10.3F);
                                        new Title("", "§e+1 Punkt").send(player);
                                        player.getWorld().playEffect(player.getLocation(),
                                                Effect.COLOURED_DUST, 50);
                                        if (SCORE_ORANGE == WIN) {
                                            endGame();
                                            titleBroadcast("§6Team Orange hat gewonnen!",
                                                    "§eHerzlichen Glückwunsch!");
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }.runTaskTimer(BeeDash.instance, 0, 3L);
    }

    public static void endGame() {
        ended = true;
        inGame = false;

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
                if (Bukkit.getOnlinePlayers().size() < 2) {
                    Bukkit.broadcastMessage(prefix + "Warte auf Spieler... (min. 2, max. 12)");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 0.5F, 0.5F);
                    }
                } else {
                    Bukkit.broadcastMessage(prefix + "Spiel-Start in einer Minute!");
                    tutorial();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Bukkit.getOnlinePlayers().size() >= 2) {
                                startGame();
                            } else {
                                Bukkit.broadcastMessage(
                                        prefix + "Es waren wieder zu wenige Spieler online!");
                                checkPlayers();
                            }
                        }
                    }.runTaskLater(BeeDash.instance, 20L * 60);
                    cancel();
                }
            }
        }.runTaskTimer(BeeDash.instance, 0L, 200L);
    }

    private static void tutorial() {
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                switch (i) {
                    case 20:
                        titleBroadcast("§aWende präsentiert:", "§eBeeDash!");
                        break;
                    case 25:
                        titleBroadcast("§aFliege in die Mitte,",
                                "§eum Honig aus der Blüte aufzusammeln!");
                        break;
                    case 30:
                        titleBroadcast("§aBringe den Honig zurück",
                                "§ein deine Team-Basis, um den Honigtopf zu füllen.");
                        break;
                    case 35:
                        titleBroadcast("§aTöte andere Spieler", "§eum deren Honig zu klauen!");
                        break;
                    case 40:
                        titleBroadcast("§aDas Team, dessen Honigtopf",
                                "§eals erstes voll ist, gewinnt!");
                        break;
                    case 45:
                        titleBroadcast("§aNoch 15 Sekunden", "§ebis zum Start!");
                        break;
                    case 55:
                        titleBroadcast("§aMach dich bereit!", "§4➎");
                        break;
                    case 56:
                        titleBroadcast("§aMach dich bereit!", "§c➍");
                        break;
                    case 57:
                        titleBroadcast("§aGleich gehts los!", "§6➌");
                        break;
                    case 58:
                        titleBroadcast("§aGleich gehts los!", "§e➋");
                        break;
                    case 59:
                        titleBroadcast("§aLetssss gooo!", "§2❶");
                        break;
                    case 60:
                        titleBroadcast("§aViel Erfolg!", "§eUnd jetzt fleißig sammeln!");
                        cancel();
                        break;
                }
                i++;
            }
        }.runTaskTimer(BeeDash.instance, 0L, 20L);
    }

    private static void titleBroadcast(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            new Title(title, subtitle, 1, 3, 1).send(player);
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.5F, 0.5F);
        }
    }

    public static void removeFromAllTeams(Player player) {
        if (TEAM_GREEN.containsKey(player.getUniqueId())) {
            TEAM_GREEN.remove(player.getUniqueId());
        }
        if (TEAM_RED.containsKey(player.getUniqueId())) {
            TEAM_RED.remove(player.getUniqueId());
        }
        if (TEAM_ORANGE.containsKey(player.getUniqueId())) {
            TEAM_ORANGE.remove(player.getUniqueId());
        }
        if (TEAM_BLUE.containsKey(player.getUniqueId())) {
            TEAM_BLUE.remove(player.getUniqueId());
        }
        teams.remove(player.getUniqueId());
    }

    private static void statusTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (inGame) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        new ActionBar("§aHonig-Punkte dabei: " + getTeamColor(player)
                                + getTeamHashMap(player).get(player.getUniqueId()))
                                .sendToPlayer(player);
                    }
                }
            }
        }.runTaskTimer(BeeDash.instance, 0L, 5L);
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

    public static int teamScoreFetcher(Player player) {
        String teamColor = getTeamColor(player);

        switch (teamColor) {
            case "§2":
                return SCORE_GREEN;
            case "§4":
                return SCORE_RED;
            case "§6":
                return SCORE_ORANGE;
            case "§9":
                return SCORE_BLUE;
        }

        return 0;
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
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

}