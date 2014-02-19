package com.dsh105.dshutils.apiutil;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

/**
 * @author DarkBlade12
 *         https://gist.github.com/DarkBlade12/9002495
 */

public class InventoryFactory {

    public static String toString(Inventory i) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("Title", i.getTitle());
        configuration.set("Size", i.getSize());
        for (int a = 0; a < i.getSize(); a++) {
            ItemStack s = i.getItem(a);
            if (s != null)
                configuration.set("Contents." + a, s);
        }
        return Base64Coder.encodeString(configuration.saveToString());
    }

    public static Inventory fromString(String s) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(Base64Coder.decodeString(s));
            Inventory i = Bukkit.createInventory(null, configuration.getInt("Size"), configuration.getString("Title"));
            ConfigurationSection contents = configuration.getConfigurationSection("Contents");
            for (String index : contents.getKeys(false))
                i.setItem(Integer.parseInt(index), contents.getItemStack(index));
            return i;
        } catch (InvalidConfigurationException e) {
            return null;
        }
    }
}