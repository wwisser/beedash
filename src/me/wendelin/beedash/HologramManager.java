package me.wendelin.beedash;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class HologramManager {

    public static void startTask() {
        for (Hologram hg : HologramsAPI.getHolograms(BeeDash.instance)) {
            hg.delete();
        }
        Location SPAWN_GREEN = new Location(Bukkit.getWorld("world"), -213.588, 15, -224.422);
        Location SPAWN_ORANGE = new Location(Bukkit.getWorld("world"), -213.472, 15, -108.537);
        Location SPAWN_RED = new Location(Bukkit.getWorld("world"), -271.528, 15, -166.455);
        Location SPAWN_BLUE = new Location(Bukkit.getWorld("world"), -155.517, 15, -166.511);

        Hologram green = HologramsAPI.createHologram(BeeDash.instance, SPAWN_GREEN);
        green.appendTextLine("§2Honigtopf auffüllen:");
        green.appendTextLine("§7Auf die Wolle stehen & sneaken");

        Hologram orange = HologramsAPI.createHologram(BeeDash.instance, SPAWN_ORANGE);
        orange.appendTextLine("§6Honigtopf auffüllen:");
        orange.appendTextLine("§7Auf die Wolle stehen & sneaken");

        Hologram red = HologramsAPI.createHologram(BeeDash.instance, SPAWN_RED);
        red.appendTextLine("§4Honigtopf auffüllen:");
        red.appendTextLine("§7Auf die Wolle stehen & sneaken");

        Hologram blue = HologramsAPI.createHologram(BeeDash.instance, SPAWN_BLUE);
        blue.appendTextLine("§9Honigtopf auffüllen:");
        blue.appendTextLine("§7Auf die Wolle stehen & sneaken");
    }
}
