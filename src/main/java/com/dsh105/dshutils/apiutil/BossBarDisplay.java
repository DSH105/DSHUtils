package com.dsh105.dshutils.apiutil;

import com.dsh105.dshutils.DSHPlugin;
import com.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * https://forums.bukkit.org/threads/158018/
 */

public class BossBarDisplay {

    public static final int ENTITY_ID = 1234;

    private static HashMap<String, Boolean> hasHealthBar = new HashMap<String, Boolean>();

    //Accessing packets
    @SuppressWarnings("deprecation")
    public static PacketPlayOutSpawnEntityLiving getMobPacket(String text, Location loc) {
        PacketPlayOutSpawnEntityLiving mobPacket = new PacketPlayOutSpawnEntityLiving();

        try {
            Field a = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "a");
            a.setAccessible(true);
            a.set(mobPacket, (int) ENTITY_ID);

            Field b = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "b");
            b.setAccessible(true);
            b.set(mobPacket, (byte) EntityType.WITHER.getTypeId());

            Field c = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "c");
            c.setAccessible(true);
            c.set(mobPacket, (int) Math.floor(loc.getBlockX() * 32.0D));

            Field d = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "d");
            d.setAccessible(true);
            d.set(mobPacket, (int) Math.floor(loc.getBlockY() * 32.0D));

            Field e = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "e");
            e.setAccessible(true);
            e.set(mobPacket, (int) Math.floor(loc.getBlockZ() * 32.0D));

            Field f = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "f");
            f.setAccessible(true);
            f.set(mobPacket, (byte) 0);

            Field g = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "g");
            g.setAccessible(true);
            g.set(mobPacket, (byte) 0);

            Field h = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "h");
            h.setAccessible(true);
            h.set(mobPacket, (byte) 0);

            Field i = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "i");
            i.setAccessible(true);
            i.set(mobPacket, (byte) 0);

            Field j = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "j");
            j.setAccessible(true);
            j.set(mobPacket, (byte) 0);

            Field k = ReflectionUtil.getDeclaredField(mobPacket.getClass(), "k");
            k.setAccessible(true);
            k.set(mobPacket, (byte) 0);

        } catch (IllegalArgumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        DataWatcher watcher = getWatcher(text, 300);

        try {
            Field t = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("l");
            t.setAccessible(true);
            t.set(mobPacket, watcher);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mobPacket;
    }

    public static PacketPlayOutEntityDestroy getDestroyEntityPacket() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy();

        Field a = ReflectionUtil.getDeclaredField(packet.getClass(), "a");
        a.setAccessible(true);
        try {
            a.set(packet, new int[]{ENTITY_ID});
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static PacketPlayOutEntityMetadata getMetadataPacket(DataWatcher watcher) {
        PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata();

        Field a = ReflectionUtil.getDeclaredField(metaPacket.getClass(), "a");
        a.setAccessible(true);
        try {
            a.set(metaPacket, (int) ENTITY_ID);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }

        try {
            Field b = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
            b.setAccessible(true);
            b.set(metaPacket, watcher.c());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return metaPacket;
    }

    public static PacketPlayInClientCommand getRespawnPacket() {
        PacketPlayInClientCommand packet = new PacketPlayInClientCommand();

        Field a = ReflectionUtil.getDeclaredField(packet.getClass(), "a");
        a.setAccessible(true);
        try {
            a.set(packet, (int) 1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static DataWatcher getWatcher(String text, int health) {
        DataWatcher watcher = new DataWatcher(null);

        watcher.a(0, (Byte) (byte) 0x20); //Flags, 0x20 = invisible
        watcher.a(6, (Float) (float) health);
        watcher.a(10, (String) text); //Entity name
        watcher.a(11, (Byte) (byte) 1); //Show name, 1 = show, 0 = don't show
        //watcher.a(16, (Integer) (int) health); //Wither health, 300 = full health

        return watcher;
    }

    //Other methods
    public static void displayTextBar(String text, final Player player) {
        PacketPlayOutSpawnEntityLiving mobPacket = getMobPacket(text, player.getLocation());

        ReflectionUtil.sendPacket(player, mobPacket);
        hasHealthBar.put(player.getName(), true);

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutEntityDestroy destroyEntityPacket = getDestroyEntityPacket();

                ReflectionUtil.sendPacket(player, destroyEntityPacket);
                hasHealthBar.put(player.getName(), false);
            }
        }.runTaskLater(DSHPlugin.getInstance(), 120L);
    }

    public static void displayLoadingBar(final String text, final String completeText, final Player player, final int healthAdd, final long delay, final boolean loadUp) {
        PacketPlayOutSpawnEntityLiving mobPacket = getMobPacket(text, player.getLocation());

        ReflectionUtil.sendPacket(player, mobPacket);
        hasHealthBar.put(player.getName(), true);

        new BukkitRunnable() {
            int health = (loadUp ? 0 : 300);

            @Override
            public void run() {
                if ((loadUp ? health < 300 : health > 0)) {
                    DataWatcher watcher = getWatcher(text, health);
                    PacketPlayOutEntityMetadata metaPacket = getMetadataPacket(watcher);

                    ReflectionUtil.sendPacket(player, metaPacket);

                    if (loadUp) {
                        health += healthAdd;
                    } else {
                        health -= healthAdd;
                    }
                } else {
                    DataWatcher watcher = getWatcher(text, (loadUp ? 300 : 0));
                    PacketPlayOutEntityMetadata metaPacket = getMetadataPacket(watcher);
                    PacketPlayOutEntityDestroy destroyEntityPacket = getDestroyEntityPacket();

                    ReflectionUtil.sendPacket(player, metaPacket);
                    ReflectionUtil.sendPacket(player, destroyEntityPacket);
                    hasHealthBar.put(player.getName(), false);

                    //Complete text
                    PacketPlayOutSpawnEntityLiving mobPacket = getMobPacket(completeText, player.getLocation());

                    ReflectionUtil.sendPacket(player, mobPacket);
                    hasHealthBar.put(player.getName(), true);

                    DataWatcher watcher2 = getWatcher(completeText, 300);
                    PacketPlayOutEntityMetadata metaPacket2 = getMetadataPacket(watcher2);

                    ReflectionUtil.sendPacket(player, metaPacket2);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            PacketPlayOutEntityDestroy destroyEntityPacket = getDestroyEntityPacket();

                            ReflectionUtil.sendPacket(player, destroyEntityPacket);
                            hasHealthBar.put(player.getName(), false);
                        }
                    }.runTaskLater(DSHPlugin.getInstance(), 40L);

                    this.cancel();
                }
            }
        }.runTaskTimer(DSHPlugin.getInstance(), delay, delay);
    }

    public static void displayLoadingBar(final String text, final String completeText, final Player player, final int secondsDelay, final boolean loadUp) {
        final int healthChangePerSecond = 300 / secondsDelay;

        displayLoadingBar(text, completeText, player, healthChangePerSecond, 20L, loadUp);
    }
}