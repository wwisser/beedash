package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import me.wendelin.beedash.util.Title;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (GameManager.warmup) {
            GameManager.players.add(player.getUniqueId());
            player.setPlayerListName("§7" + player.getName());
            player.teleport(GameManager.SPAWN);
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            new Title("§e[BeeDash]", "§aMinigame", 1, 3, 1).send(player);
            event.setJoinMessage(
                    GameManager.prefix + ChatColor.AQUA + player.getName() + " §awill spielen!");
        } else if (GameManager.inGame) {
            new Title("§e[BeeDash]", "§aWillkommen zurück!", 1, 3, 1).send(player);
            event.setJoinMessage(
                    GameManager.prefix + GameManager.getTeamColor(player) + player.getName()
                            + " §aist zurück!");
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (GameManager.inGame) {
            event.setKickMessage(GameManager.prefix + "\n§cSorry!\nDas Spiel läuft bereits :(");
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
        }
    }

}
