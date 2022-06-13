package com.Endless.creators;

import java.util.Arrays;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemCreator {
    public static void addItem(Player player, String displayName, int slot, Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        player.getInventory().setItem(slot, stack);
    }

    public static void addItem(Player player, String displayName, byte id, int slot, Material material) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        player.getInventory().setItem(slot, stack);
    }

    public static void addItem(Player player, String displayName, int slot, Material material, String[] lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        player.getInventory().setItem(slot, stack);
    }

    public static void addItem(Player player, String displayName, byte id, int slot, Material material, String[] lore) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        player.getInventory().setItem(slot, stack);
    }

    public static void addItem(Player player, String displayName, byte id, int slot, Material material, String[] lore, boolean glow) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        if (glow) {
            meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        }
        stack.setItemMeta(meta);
        player.getInventory().setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, int slot, Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, byte id, int slot, Material material) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, int slot, Material material, String[] lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, int slot, int amount, Material material, String[] lore) {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, byte id, int slot, Material material, String[] lore) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void addItem(Inventory inventory, String displayName, byte id, int slot, Material material, String[] lore, boolean glow) {
        ItemStack stack = new ItemStack(material, 1, (short)id);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        if (glow) {
            meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        }
        stack.setItemMeta(meta);
        inventory.setItem(slot, stack);
    }

    public static void leatherArmor(Player player, Color color) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta)helmet.getItemMeta();
        meta.setColor(color);
        helmet.setItemMeta((ItemMeta)meta);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta2 = (LeatherArmorMeta)chestplate.getItemMeta();
        meta2.setColor(color);
        chestplate.setItemMeta((ItemMeta)meta2);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta3 = (LeatherArmorMeta)leggings.getItemMeta();
        meta3.setColor(color);
        leggings.setItemMeta((ItemMeta)meta3);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta4 = (LeatherArmorMeta)boots.getItemMeta();
        meta4.setColor(color);
        boots.setItemMeta((ItemMeta)meta4);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
    }
}
