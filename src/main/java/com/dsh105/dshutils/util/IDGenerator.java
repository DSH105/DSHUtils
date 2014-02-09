package com.dsh105.dshutils.util;

import org.bukkit.plugin.java.JavaPlugin;

public class IDGenerator {

    private static long lastId = Long.MIN_VALUE;

    public static String nextId(JavaPlugin pl) {
        return pl.getName() + "-" + ++lastId;
    }

    public static long nextId() {
        return ++lastId;
    }
}