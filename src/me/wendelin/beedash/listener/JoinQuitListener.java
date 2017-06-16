package me.wendelin.beedash.listener;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.wendelin.beedash.BeeDash;
import me.wendelin.beedash.GameManager;
import me.wendelin.beedash.ScoreboardManager;
import me.wendelin.beedash.util.ItemBuilder;
import me.wendelin.beedash.util.Skin;
import me.wendelin.beedash.util.TabTitleManager;
import me.wendelin.beedash.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinQuitListener implements Listener {

    private static int i = 0;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setLevel(0);

        TabTitleManager.sendHeaderAndFooter(player, GameManager.prefix + " §aMinispiel\n§c",
                "§c\n§7Beta v1.0");

        ScoreboardManager.updateScoreboard(player);

        if (GameManager.warmup) {
            GameManager.players.add(player.getUniqueId());

            selectTeam(player);

            /**new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR).name("§3Wähle dein Team!").build());
                }
            }.runTaskLater(BeeDash.instance, 1);**/
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.setPlayerListName("§8" + player.getName());
            player.teleport(GameManager.SPAWN);
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            new Title("§e[BeeDash]", "§aMinigame", 1, 3, 1).send(player);
            event.setJoinMessage(null);
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (onlinePlayers.getName() != player.getName()) {
                    onlinePlayers.sendMessage(GameManager.prefix + ChatColor.AQUA + player.getName()
                            + " §awill spielen!");
                }
            }
        } else if (GameManager.inGame) {
            player.setPlayerListName(GameManager.getTeamColor(player) + player.getName());
            new Title("§e[BeeDash]", "§aWillkommen zurück!", 1, 3, 1).send(player);
            event.setJoinMessage(
                    GameManager.prefix + GameManager.getTeamColor(player) + player.getName()
                            + " §aist zurück!");
            player.getInventory().clear();

            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0.08F);

            player.getInventory().setItem(0, new ItemBuilder(Material.STONE_SWORD).enchantment(
                    Enchantment.DURABILITY, 5).build());
            player.getInventory().setItem(1, new ItemBuilder(Material.BOW).build());
            player.getInventory().setItem(2,
                    new ItemBuilder(Material.ARROW).enchantment(Enchantment.ARROW_KNOCKBACK, 1)
                            .amount(8).build());

            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 2));
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if(GameManager.inGame || GameManager.ended) {
            if(!GameManager.players.contains(event.getPlayer().getUniqueId())) {
                event.disallow(Result.KICK_OTHER, GameManager.prefix + "\n§cSorry!\n§aDas Spiel läuft bereits :(");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (GameManager.warmup) {
            GameManager.players.remove(player.getUniqueId());
            event.setQuitMessage(GameManager.prefix + ChatColor.AQUA + player.getName()
                    + " §ahat doch keine Lust!");
        } else if (GameManager.inGame) {
            event.setQuitMessage(
                    GameManager.prefix + GameManager.getTeamColor(player) + player.getName()
                            + " §ahat keine Lust mehr!");
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        if (GameManager.warmup) {
            GameManager.players.remove(player.getUniqueId());
            event.setLeaveMessage(GameManager.prefix + ChatColor.AQUA + player.getName()
                    + " §ahat doch keine Lust!");
        } else if (GameManager.inGame) {
            event.setLeaveMessage(
                    GameManager.prefix + GameManager.getTeamColor(player) + player.getName()
                            + " §ahat keine Lust mehr!");
        } else if (GameManager.ended) {
            event.setLeaveMessage(
                    GameManager.prefix + GameManager.getTeamColor(player) + player.getName()
                            + " §ahat das Spiel verlassen!");
        }
    }

    private static void selectTeam(Player player) {
        switch (GameManager.players.size()) {
            case 1:
                GameManager.TEAM_GREEN.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "GREEN");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §2Grün §aeingeteilt!");
                skinLoad(player, "977721a135d441d99737ca453815c651");
                break;
            case 2:
                GameManager.TEAM_RED.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "RED");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §4Rot §aeingeteilt!");
                skinLoad(player, "c48b345a06b24a848a1bef82eeed5f38");
                break;
            case 3:
                GameManager.TEAM_ORANGE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "ORANGE");
                player.sendMessage(
                        GameManager.prefix + "Du wurdest in Team §6Orange §aeingeteilt!");
                skinLoad(player, "868a3499851a41b680decc882674b33e");
                break;
            case 4:
                GameManager.TEAM_BLUE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "BLUE");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §9Blau §aeingeteilt!");
                skinLoad(player, "bd15b004500446da8007ce724a97fe77");
                break;
            case 5:
                GameManager.TEAM_GREEN.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "GREEN");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §2Grün §aeingeteilt!");
                skinLoad(player, "d98ba36aa7b14e8eaa8cbb9004647950");
                break;
            case 6:
                GameManager.TEAM_RED.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "RED");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §4Rot §aeingeteilt!");
                skinLoad(player, "33b0897b8e5441cca3deb25f5e78127c");
                break;
            case 7:
                GameManager.TEAM_ORANGE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "ORANGE");
                player.sendMessage(
                        GameManager.prefix + "Du wurdest in Team §6Orange §aeingeteilt!");
                skinLoad(player, "e607734b815e4313ae9c141d2269fe07");
                break;
            case 8:
                GameManager.TEAM_BLUE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "BLUE");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §9Blau §aeingeteilt!");
                skinLoad(player, "4f1ca7f84cc7484495a74e4768690f61");
                break;
            case 9:
                GameManager.TEAM_GREEN.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "GREEN");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §2Grün §aeingeteilt!");
                skinLoad(player, "d3f6a9d5cea0461c9355e63d9a9fcc8f");
                break;
            case 10:
                GameManager.TEAM_RED.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "RED");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §4Rot §aeingeteilt!");
                skinLoad(player, "9c901cf070024996a7e42c2c3aa19d88");
                break;
            case 11:
                GameManager.TEAM_ORANGE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "ORANGE");
                player.sendMessage(
                        GameManager.prefix + "Du wurdest in Team §6Orange §aeingeteilt!");
                skinLoad(player, "05aecbbbadaa491c93425ef178968fdb");
                break;
            case 12:
                GameManager.TEAM_BLUE.put(player.getUniqueId(), 0);
                GameManager.teams.put(player.getUniqueId(), "BLUE");
                player.sendMessage(GameManager.prefix + "Du wurdest in Team §9Blau §aeingeteilt!");
                skinLoad(player, "46ac0d3ac3d84862846d2f4477d12b7f");
                break;
        }
    }

    private static void skinLoad(Player player, String uuid) {
            GameProfile gp = ((CraftPlayer) player).getProfile();
            gp.getProperties().clear();
            Skin skin = new Skin(uuid);

            gp.getProperties().put(skin.getSkinName(),
                    new Property(skin.getSkinName(), skin.getSkinValue(), skin.getSkinSignatur()));

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.hidePlayer(player);
                    }
                }
            }.runTaskLater(BeeDash.instance, 1);

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.showPlayer(player);
                    }
                }
            }.runTaskLater(BeeDash.instance, 10);
    }

}
