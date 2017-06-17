package me.wendelin.beedash.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import me.wendelin.beedash.BeeDash;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SkinUtil implements Listener {

    public static HashMap<UUID, String> usedSkins = new HashMap<>();
    public static ArrayList<String> usableSkins = new ArrayList<>();

    public static void resetSkins() {
        usedSkins.clear();
        usableSkins.clear();

        usableSkins.add("977721a135d441d99737ca453815c651");
        usableSkins.add("c48b345a06b24a848a1bef82eeed5f38");
        usableSkins.add("868a3499851a41b680decc882674b33e");
        usableSkins.add("46ac0d3ac3d84862846d2f4477d12b7f");
        usableSkins.add("33b0897b8e5441cca3deb25f5e78127c");
        usableSkins.add("94b121a389864ff3bae581ff93fe0771");
        usableSkins.add("e607734b815e4313ae9c141d2269fe07");
        usableSkins.add("4f1ca7f84cc7484495a74e4768690f61");
        usableSkins.add("d3f6a9d5cea0461c9355e63d9a9fcc8f");
        usableSkins.add("9c901cf070024996a7e42c2c3aa19d88");
        usableSkins.add("05aecbbbadaa491c93425ef178968fdb");
        usableSkins.add("306c9bdcff9043388a7bdc8fe3fab9f6");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String skinUUID = usableSkins.get(new Random().nextInt(usableSkins.size()));
        usableSkins.remove(skinUUID);
        usedSkins.put(uuid, skinUUID);

        skinLoad(player, skinUUID);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String skinUUID = usedSkins.get(uuid);
        usedSkins.remove(uuid);
        usableSkins.add(skinUUID);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String skinUUID = usedSkins.get(uuid);
        usedSkins.remove(uuid);
        usableSkins.add(skinUUID);
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
