package io.github.dsh105.dshutils.util;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    public static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods()) if (m.getName().equals(method)) return m;
        return null;
    }

    public static Field getField(Class<?> cl, String field) {
        for (Field f : cl.getFields()) if (f.getName().equals(field)) return f;
        return null;
    }

    public static String getVersionString(JavaPlugin plugin) {
        String packageName = plugin.getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        String version = packageSplit[packageSplit.length - 1];
        return version;
    }

    public static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    public static void sendPacket(Location l, Object packet)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchFieldException {
        sendPacket(l, packet, 20);
    }

    public static void sendPacket(Location l, Object packet, int radius)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchFieldException {
        if (!GeometryUtil.getNearbyEntities(l, radius).isEmpty()) {
            for (Entity e : GeometryUtil.getNearbyEntities(l, 20)) {
                if (e != null && e instanceof Player) {
                    Player p = (Player) e;
                    sendPacket(p, packet);
                }
            }
        }
    }

    public static void sendPacket(Player p, Object packet) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Object nmsPlayer = getMethod(p.getClass(), "getHandle").invoke(p);
        Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
        getMethod(con.getClass(), "sendPacket").invoke(con, packet);
    }

    public static void spawnFirework(Location l, FireworkEffect fe) {
        Firework fw = l.getWorld().spawn(l, Firework.class);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.clearEffects();
        fwm.addEffect(fe);
        try {
            Field f = fwm.getClass().getDeclaredField("power");
            f.setAccessible(true);
            f.set(fwm, Integer.valueOf(-2));
        } catch (Exception e) {
        }
        fw.setFireworkMeta(fwm);
    }
}
