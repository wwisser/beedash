package me.wendelin.beedash;

import me.wendelin.beedash.commands.BeeDashExecutor;
import me.wendelin.beedash.listener.BuildBreakListener;
import me.wendelin.beedash.listener.ChatListener;
import me.wendelin.beedash.listener.DeathListener;
import me.wendelin.beedash.listener.DropPickListener;
import me.wendelin.beedash.listener.EntityDamageListener;
import me.wendelin.beedash.listener.FoodLevelChangeListener;
import me.wendelin.beedash.listener.InvClickListener;
import me.wendelin.beedash.listener.JoinQuitListener;
import me.wendelin.beedash.listener.RespawnListener;
import me.wendelin.beedash.listener.ServerPingListener;
import me.wendelin.beedash.util.SkinUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BeeDash extends JavaPlugin {

    public static BeeDash instance;

    @Override
    public void onEnable() {
        instance = this;

        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity.getType() == EntityType.DROPPED_ITEM) {
                entity.remove();
            }
        }

        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new JoinQuitListener(), this);
        pm.registerEvents(new ServerPingListener(), this);
        pm.registerEvents(new DropPickListener(), this);
        pm.registerEvents(new BuildBreakListener(), this);
        pm.registerEvents(new InvClickListener(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new RespawnListener(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new SkinUtil(), this);

        getCommand("beedash").setExecutor(new BeeDashExecutor());

        GameManager.warmup = true;

        GameManager.checkPlayers();
        ScoreboardManager.startTask();
        HologramManager.startTask();
        SkinUtil.resetSkins();
    }

    @Override
    public void onDisable() {

    }

}
