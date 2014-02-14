package com.dsh105.dshutils.util;

import org.bukkit.ChatColor;

import java.util.Random;

public class StringUtil {

    private static Random r;

    public static Random r() {
        if (r == null) {
            r = new Random();
        }
        return r;
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static String capitalise(String s) {
        String finalString = "";
        if (s.contains(" ")) {
            StringBuilder builder = new StringBuilder();
            String[] sp = s.split(" ");
            for (String string : sp) {
                string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
                builder.append(string);
                builder.append(" ");
            }
            builder.deleteCharAt(builder.length() - 1);
            finalString = builder.toString();
        } else {
            finalString = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return finalString;
    }

    public static String combineSplit(int startIndex, String[] string, String separator) {
        if (string == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = startIndex; i < string.length; i++) {
                builder.append(string[i]);
                builder.append(separator);
            }
            builder.deleteCharAt(builder.length() - separator.length());
            return builder.toString();
        }
    }

    // WHY...? ._.
    public static String replaceColoursWithString(String whyAmIDoingThis) {
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.BLACK + "", "&0");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_BLUE + "", "&1");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_GREEN + "", "&2");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_AQUA + "", "&3");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_RED + "", "&4");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_PURPLE + "", "&5");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.GOLD + "", "&6");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.GRAY + "", "&7");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.DARK_GRAY + "", "&8");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.BLUE + "", "&9");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.GREEN + "", "&a");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.AQUA + "", "&b");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.RED + "", "&c");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.LIGHT_PURPLE + "", "&d");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.YELLOW + "", "&e");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.WHITE + "", "&f");

        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.MAGIC + "", "&k");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.BOLD + "", "&l");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.STRIKETHROUGH + "", "&m");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.UNDERLINE + "", "&n");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.ITALIC + "", "&o");
        whyAmIDoingThis = whyAmIDoingThis.replace(ChatColor.RESET + "", "&r");
        return whyAmIDoingThis;
    }
}