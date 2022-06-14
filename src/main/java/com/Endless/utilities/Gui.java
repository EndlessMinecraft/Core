package com.Endless.utilities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public abstract class Gui implements Listener {
    Inventory inventory;

    private String name;

    private int rows;

    private int size;

    private Plugin plugin;

    private Map<Integer, GuiItem> events = new HashMap<>();

    private InventoryType type;

    public Gui(String name, int rows, Plugin plugin) {
        this.name = name;
        this.rows = rows;
        this.size = rows * 9;
        this.plugin = plugin;
        create();
    }

    public Gui(InventoryType type, String name, int rows, Plugin plugin) {
        this.type = type;
        this.name = name;
        this.rows = rows;
        this.size = rows * 9 - 1;
        this.plugin = plugin;
        create();
    }

    private void create() {
        this.inventory = Bukkit.createInventory(null, 9 * this.rows, ChatColor.translateAlternateColorCodes('&', this.name));
        if (this.type != null)
            this.inventory = Bukkit.createInventory(null, this.type);
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
        init();
    }

    public abstract void init();
//
    protected void addItem(GuiItem guiItem) {
        if (getInventory().firstEmpty() == -1)
            return;
        setItem(guiItem, getInventory().firstEmpty(), new int[0]);
    }

    protected void removeItem(int slot, int... slots) {
        for (int query : slots) {
            if (this.events.containsKey(Integer.valueOf(query))) {
                this.events.remove(Integer.valueOf(query));
                this.inventory.setItem(query, new ItemStack(Material.AIR));
            }
        }
        if (this.events.containsKey(Integer.valueOf(slot))) {
            this.events.remove(Integer.valueOf(slot));
            this.inventory.setItem(slot, new ItemStack(Material.AIR));
        }
    }

    protected void setItem(GuiItem item, int slot, int... slots) {
        for (int query : slots) {
            if (!this.events.containsKey(Integer.valueOf(query))) {
                this.events.put(Integer.valueOf(query), item);
                this.inventory.setItem(query, item.get());
            }
        }
        if (this.events.containsKey(Integer.valueOf(slot)))
            return;
        this.events.put(Integer.valueOf(slot), item);
        this.inventory.setItem(slot, item.get());
    }

    protected void fillEmptySlots(GuiItem guiItem) {
        for (int i = 0; i < getInventory().getSize() - 1; i++)
            addItem(guiItem);
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getName() {
        return this.name;
    }

    public int getRows() {
        return this.rows;
    }

    public int getSize() {
        return this.size;
    }

    public GuiItem getItem(int slot) {
        if (this.events.containsKey(Integer.valueOf(slot)))
            return this.events.get(Integer.valueOf(slot));
        return null;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getInventory().equals(this.inventory)) {
            e.setCancelled(true);
            if (!this.events.containsKey(Integer.valueOf(e.getSlot())))
                return;
            if (e.getClickedInventory() == null)
                return;
            if (e.getClickedInventory().getItem(e.getSlot()) == null)
                return;
            if (e.getView().getBottomInventory() == null)
                return;
            if (e.getClickedInventory().getHolder() instanceof Player)
                return;
            GuiItem item = this.events.get(Integer.valueOf(e.getSlot()));
            item.onUse(player, e.getClick(), e.getSlot());
        }
    }

    public static class Item {
        private boolean glow;

        private Material material;

        private int data;

        private int amount = 1;

        private int durability = 0;

        private String name;

        private List<String> lore;

        private ItemStack item;

        private Map<Enchantment, Integer> enchantements;

        public Item(Material material) {
            this.material = material;
            this.lore = new ArrayList<>();
            this.enchantements = new HashMap<>();
            this.glow = false;
        }

        public Item(ItemStack item) {
            this.item = item;
            this.amount = item.getAmount();
            this.durability = item.getDurability();
            this.material = item.getType();
            this.lore = (item.hasItemMeta() && item.getItemMeta().hasLore()) ? item.getItemMeta().getLore() : new ArrayList<>();
            this.enchantements = item.getEnchantments();
            this.name = (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() : null;
            this.glow = false;
        }

        public Item setName(String name) {
            this.name = name;
            return this;
        }

        public Item setLore(List<String> lore) {
            List<String> newLore = new ArrayList<>();
            for (String line : lore)
                newLore.add(ChatColor.translateAlternateColorCodes('&', line));
            this.lore = newLore;
            return this;
        }

        public Item addLore(String lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
            return this;
        }

        public Item setData(int data) {
            this.data = data;
            return this;
        }

        public Item setDefaultLore(String... lore) {
            addLore("&8&m-------------------");
            addLore("&r");
            for (String query : lore)
                addLore(query);
            addLore("&r");
            addLore("&8&m-------------------");
            return this;
        }

        public Item setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Item setDurability(int durability) {
            this.durability = durability;
            return this;
        }

        public Item setGlow(boolean glow) {
            this.glow = glow;
            return this;
        }

        public boolean hasGlow() {
            return get().containsEnchantment(new Glow(255));
        }

        public Item addEnchantment(Enchantment enchantment, int level) {
            if (this.enchantements.containsKey(enchantment))
                this.enchantements.remove(enchantment);
            this.enchantements.put(enchantment, Integer.valueOf(level));
            return this;
        }

        public String getName() {
            return (this.name != null) ? Text.color(this.name) : Text.formatName(this.material.name());
        }

        public ItemStack get() {
            ItemStack item = null;
            if (this.item != null) {
                item = this.item.clone();
            } else {
                item = new ItemStack(this.material, this.amount, (short)(byte)this.durability);
                item.setData(new MaterialData(this.material, (byte)this.data));
            }
            ItemMeta meta = item.getItemMeta();
            if (this.name != null)
                meta.setDisplayName(Text.color(this.name));
            if (this.lore != null && !this.lore.isEmpty())
                meta.setLore(this.lore);
            if (this.glow) {
                Glow glow = new Glow(255);
                meta.addEnchant(glow, 1, true);
            }
            if (this.enchantements != null && !this.enchantements.isEmpty())
                for (Enchantment enchant : this.enchantements.keySet())
                    item.addEnchantment(enchant, ((Integer)this.enchantements.get(enchant)).intValue());
            item.setItemMeta(meta);
            return item;
        }
    }

    public static abstract class GuiItem {
        private ItemStack itemStack;

        private Gui.Item item;

        public GuiItem(Gui.Item item) {
            this.itemStack = item.get();
            this.item = item;
        }

        public GuiItem(ItemStack item) {
            this.itemStack = item;
            this.item = new Gui.Item(item.getType());
        }

        public ItemStack get() {
            return this.itemStack;
        }

        public Gui.Item getFromItem() {
            return this.item;
        }

        public abstract void onUse(Player param1Player, ClickType param1ClickType, int param1Int);
    }
}
