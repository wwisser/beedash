package me.wendelin.beedash;

import me.wendelin.beedash.commands.BeeDashExecutor;
import me.wendelin.beedash.listener.BuildBreakListener;
import me.wendelin.beedash.listener.DropPickListener;
import me.wendelin.beedash.listener.JoinQuitListener;
import me.wendelin.beedash.listener.ServerPingListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BeeDash extends JavaPlugin {

    public static BeeDash instance;

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new JoinQuitListener(), this);
        pm.registerEvents(new ServerPingListener(), this);
        pm.registerEvents(new DropPickListener(), this);
        pm.registerEvents(new BuildBreakListener(), this);

        getCommand("beedash").setExecutor(new BeeDashExecutor());

        GameManager.warmup = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                GameManager.dropRandomFlower();
            }
        }.runTaskTimer(this, 0, 10L);
    }

    @Override
    public void onDisable() {

    }

}
