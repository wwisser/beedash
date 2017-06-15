package me.wendelin.beedash.util;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private Material material;
    private int amount;
    private String name;
    private int damage;
    private HashMap<Enchantment, Integer> enchantments;
    private String ownerName;

    public ItemBuilder(Material material) {
        this.material = material;
        this.amount = 1;
        this.damage = 0;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = "§r" + name;
        return this;
    }

    public ItemBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int enchantmentLevel) {
        if(this.enchantments == null) this.enchantments = new HashMap<>();
        this.enchantments.put(enchantment, enchantmentLevel);
        return this;
    }

    public ItemBuilder head(String ownerName){
        this.ownerName = ownerName;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(this.material, this.amount, (byte)this.damage);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(this.name != null) {
            itemMeta.setDisplayName(name);
        }

        if(this.enchantments != null){
            for(Map.Entry<Enchantment, Integer> entry : this.enchantments.entrySet())
                itemStack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }

        if(this.ownerName != null){
            SkullMeta skullMeta = (SkullMeta)itemMeta;
            skullMeta.setOwner(ownerName);
            itemStack.setItemMeta(skullMeta);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
