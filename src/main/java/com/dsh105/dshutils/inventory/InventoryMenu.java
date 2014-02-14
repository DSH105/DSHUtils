package com.dsh105.dshutils.inventory;

import com.dsh105.dshutils.DSHPlugin;
import com.dsh105.dshutils.util.IDGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public class InventoryMenu implements InventoryHolder, Listener {

    private long id;
    private int size;
    private String title;
    private HashMap<Integer, MenuIcon> slots = new HashMap<Integer, MenuIcon>();

    public InventoryMenu(String title, int size) {
        this.id = IDGenerator.nextId();
        DSHPlugin.getPluginInstance().getServer().getPluginManager().registerEvents(this, DSHPlugin.getPluginInstance());

        if (size < 0) {
            size = 9;
        } else if (size % 9 != 0) {
            size += 9 - (size % 9);
        }
        this.title = title;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, this.getSize(), this.getTitle());
    }

    public InventoryMenu showTo(Player viewer) {
        Inventory inv = this.getInventory();
        for (Map.Entry<Integer, MenuIcon> entry : this.getSlots().entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue().getIcon(viewer));
        }
        viewer.openInventory(inv);
        return this;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<Integer, MenuIcon> getSlots() {
        return slots;
    }

    public InventoryMenu setSlot(int slot, MenuIcon icon) {
        this.slots.put(slot, icon);
        return this;
    }

    public MenuIcon getSlot(int slot) {
        return this.slots.get(slot);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        HumanEntity human = event.getWhoClicked();
        if (human instanceof Player) {
            Player player = (Player) human;
            Inventory inv = player.getOpenInventory().getTopInventory();
            if (inv.getHolder() != null && inv.getHolder() instanceof InventoryMenu && event.getRawSlot() >= 0 && event.getRawSlot() < this.getSize()) {
                InventoryMenu menu = (InventoryMenu) inv.getHolder();
                if (menu.getId() == this.getId()) {
                    event.setCancelled(true);
                    MenuIcon icon = slots.get(event.getSlot());
                    if (icon != null) {
                        icon.onClick(player);
                    }
                }
            }
        }
    }
}