package com.dsh105.dshutils.util;

import com.dsh105.dshutils.DSHPlugin;
import com.dsh105.dshutils.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class VersionUtil {

    public static boolean b = false;
    private static String PLUGIN_VERSION;
    private static String MINECRAFT_VERSION;
    private static String CRAFTBUKKIT_VERSION;
    private static String NMS_PACKAGE;

    private static void updateVersions() {
        try {
            String path = VersionUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            Attributes a = getManifest(path).getMainAttributes();
            if (a.getValue("Plugin-Version") != null) {
                PLUGIN_VERSION = a.getValue("Plugin-Version");
            }

            if (a.getValue("Minecraft-Version") != null) {
                MINECRAFT_VERSION = a.getValue("Minecraft-Version");
            }
            if (a.getValue("CraftBukkit-Version") != null) {
                CRAFTBUKKIT_VERSION = a.getValue("CraftBukkit-Version");
            }
            if (a.getValue("NMS-Package") != null) {
                NMS_PACKAGE = a.getValue("NMS-Package");
            }
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to obtain Minecraft Server version.", e, true);
        }
    }

    private static Manifest getManifest(String path) throws IOException {
        File jar = new File(path);
        JarFile jf = new JarFile(new File(path));
        Manifest mf = new JarFile(jar).getManifest();
        jf.close();
        return mf;
    }

    public static String getServerVersion() {
        String packageName = DSHPlugin.getPluginInstance().getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        String version = packageSplit[packageSplit.length - 1];
        return version;
    }

    public static boolean compareVersions() {
        return getNMSPackage().equalsIgnoreCase(getServerVersion());
    }

    public static String getPluginVersion() {
        if (!b) {
            updateVersions();
            b = true;
        }
        return PLUGIN_VERSION;
    }

    public static String getMinecraftVersion() {
        if (!b) {
            updateVersions();
            b = true;
        }
        return MINECRAFT_VERSION;
    }

    public static String getCraftBukkitVersion() {
        if (!b) {
            updateVersions();
            b = true;
        }
        return CRAFTBUKKIT_VERSION;
    }

    public static String getNMSPackage() {
        if (!b) {
            updateVersions();
            b = true;
        }
        return NMS_PACKAGE;
    }
}