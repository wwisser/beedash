package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.ChatColor;
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
            event.setJoinMessage(
                    GameManager.prefix + ChatColor.AQUA + player.getName() + " §awill spielen!");
        } else if (GameManager.inGame) {
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

    }

}
