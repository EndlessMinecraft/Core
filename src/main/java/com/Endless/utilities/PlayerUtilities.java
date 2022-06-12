package com.Endless.utilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PlayerUtilities {

    // Send player a Tab List header and footer.
        public static void sendTabList(Player player, String header, String footer) {
            try {
                if (NMSUtilities.compareMCVersionHigherOrEqual("1.8")) {
                    Object packet = NMSUtilities.getNMSClass("PacketPlayOutPlayerListHeaderFooter").newInstance();
                    if (header != null)
                        NMSUtilities.getField(packet.getClass().getDeclaredField("a")).set(packet, NMSUtilities.getChatSerial().getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\": \"" + header + "\"}"}));
                    if (footer != null)
                        NMSUtilities.getField(packet.getClass().getDeclaredField("b")).set(packet, NMSUtilities.getChatSerial().getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\": \"" + footer + "\"}"}));
                    NMSUtilities.sendPacket(player, packet);
                } else if (NMSUtilities.compareMCVersionHigherOrEqual("1.7.10")) {
                    Object packet = NMSUtilities.getProtocolInjectorClass("PacketTabHeader").newInstance();
                    if (header != null)
                        NMSUtilities.getField(packet.getClass().getDeclaredField("header")).set(packet, NMSUtilities.getChatSerial().getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\": \"" + header + "\"}"}));
                    if (footer != null)
                        NMSUtilities.getField(packet.getClass().getDeclaredField("footer")).set(packet, NMSUtilities.getChatSerial().getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\": \"" + footer + "\"}"}));
                    NMSUtilities.sendPacket(player, packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    // Action Bar
    public static void showActionBar(Player player, String message) {
        if (!player.isOnline())
            return;
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
        if (nmsVersion.startsWith("v1_12_")) {
            showActionBar112(player, message);
        } else {
            showActionBarPre112(player, message);
        }
    }

    public static void showActionBar112(Player player, String message) {
        if (!player.isOnline())
            return;
        try {
            String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
            nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Class<?> class4 = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> class5 = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            Class<?> class2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
            Class<?> class3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            Object[] arrayOfObject1;
            int j = (arrayOfObject1 = chatMessageTypes).length;
            for (int i = 0; i < j; i++) {
                Object obj = arrayOfObject1[i];
                if (obj.toString().equals("GAME_INFO"))
                    chatMessageType = obj;
            }
            Object o = class2.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
            Object ppoc = class4.getConstructor(new Class[] { class3, chatMessageTypeClass }).newInstance(new Object[] { o, chatMessageType });
            Method method1 = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
            Object h = method1.invoke(craftPlayer, new Object[0]);
            Field field1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = field1.get(h);
            Method method5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[] { class5 });
            method5.invoke(pc, new Object[] { ppoc });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showActionBarPre112(Player player, String message) {
        if (!player.isOnline())
            return;
        try {
            Object ppoc;
            String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
            nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Class<?> class4 = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> class5 = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            if (nmsVersion.equalsIgnoreCase("v1_8_R1") || nmsVersion.startsWith("v1_7_")) {
                Class<?> class2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
                Class<?> class3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Method method3 = class2.getDeclaredMethod("a", new Class[] { String.class });
                Object cbc = class3.cast(method3.invoke(class2, new Object[] { "{\"text\": \"" + message + "\"}" }));
                ppoc = class4.getConstructor(new Class[] { class3, byte.class }).newInstance(new Object[] { cbc, Byte.valueOf((byte)2) });
            } else {
                Class<?> class2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
                Class<?> class3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Object o = class2.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
                ppoc = class4.getConstructor(new Class[] { class3, byte.class }).newInstance(new Object[] { o, Byte.valueOf((byte)2) });
            }
            Method method1 = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
            Object h = method1.invoke(craftPlayer, new Object[0]);
            Field field1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = field1.get(h);
            Method method5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[] { class5 });
            method5.invoke(pc, new Object[] { ppoc });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        // Reset player.
    public static void reset(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1F);
        player.setSprinting(false);
        player.setSneaking(false);
        player.setFoodLevel(20);
        player.setSaturation(3.0F);
        player.setExhaustion(0.0F);
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.setFallDistance(0.0F);
        player.eject();
        player.leaveVehicle();
        player.setLevel(0);
        player.setExp(0.0F);
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
        for (PotionEffect potion : player.getActivePotionEffects())
            player.removePotionEffect(potion.getType());
    }
}