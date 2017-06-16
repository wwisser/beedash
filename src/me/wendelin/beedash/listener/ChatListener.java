package me.wendelin.beedash.listener;

import me.wendelin.beedash.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage().replace("%", "%%");

        if (GameManager.warmup) {
            event.setFormat("§8" + player.getName() + "§7: §r" + message);
        } else {
            event.setFormat(
                    GameManager.getTeamColor(player) + player.getName() + "§7: §r" + message);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        /*if(!event.getMessage().startsWith("/pl") || !event.getMessage().equalsIgnoreCase("/beedash")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(GameManager.prefix + "Hier brauchst du keine Commands!");
        }*/
    }

}
