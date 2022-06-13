package com.Endless.creators;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCreator {
    public final Inventory inventory;

    public InventoryCreator(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);
    }

    public void addItem(ItemStack stack, int slot) {
        this.inventory.setItem(slot, stack);
    }

    public void addItem(String displayName, int slot, Material material, int i, byte b, String[] strings) {
        ItemCreator.addItem(this.inventory, displayName, slot, material);
    }

    public void addItem(String displayName, byte id, int slot, Material material) {
        ItemCreator.addItem(this.inventory, displayName, id, slot, material);
    }

    public void addItem(String displayName, int slot, Material material, String[] lore) {
        ItemCreator.addItem(this.inventory, displayName, slot, material, lore);
    }

    public void addItem(String displayName, int slot, int amount, Material material, String[] lore) {
        ItemCreator.addItem(this.inventory, displayName, slot, amount, material, lore);
    }

    public void addItem(String displayName, byte id, int slot, Material material, String[] lore) {
        ItemCreator.addItem(this.inventory, displayName, id, slot, material, lore);
    }

    public void addItem(String displayName, byte id, int slot, Material material, String[] lore, boolean glow) {
        ItemCreator.addItem(this.inventory, displayName, id, slot, material, lore, glow);
    }

    public void show(Player player) {
        player.openInventory(this.inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
