package com.Endless.utilities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtilities {
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