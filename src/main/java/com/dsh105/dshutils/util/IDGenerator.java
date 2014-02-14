package com.dsh105.dshutils.util;

public class IDGenerator {

    private static long lastId = Long.MIN_VALUE;

    public static long nextId() {
        return ++lastId;
    }
}