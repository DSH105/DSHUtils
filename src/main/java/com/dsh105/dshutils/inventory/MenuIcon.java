package com.dsh105.dshutils.inventory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuIcon {

    private int slot;
    private int materialId;
    private int materialData;
    private String name;
    private String[] lore;

    public MenuIcon(int slot, int materialId, int materialData, String name, String... lore) {
        this.slot = slot;
        this.materialId = materialId;
        this.materialData = materialData;
        this.name = name;
        this.lore = lore;
    }

    public int getSlot() {
        return slot;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getMaterialData() {
        return materialData;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public ItemStack getIcon(Player viewer) {
        ItemStack i = new ItemStack(this.getMaterialId(), 1, (short) this.getMaterialData());
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setLore(Arrays.asList(this.getLore()));
        i.setItemMeta(meta);
        return i;
    }

    public void onClick(Player viewer) {

    }
}