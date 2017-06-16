package me.wendelin.beedash.commands;

import me.wendelin.beedash.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BeeDashExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            player.sendMessage(GameManager.prefix + "");
            GameManager.startGame();
        } else {
            System.out.println("[BeeDash] Dieser Command ist nur fuer Spieler!");
        }
        return false;
    }

}
