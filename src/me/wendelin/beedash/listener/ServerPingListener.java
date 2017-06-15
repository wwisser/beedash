package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (GameManager.warmup) {
            event.setMotd(
                    "           §e[BeeDash] §aMinispiel-Server\n                 §7Warte auf Spieler...");
        } else if (GameManager.inGame) {
            event.setMotd(
                    "           §e[BeeDash] §aMinispiel-Server\n                   §cIm Spiel");
        }
    }

}
