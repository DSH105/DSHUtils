package io.github.dsh105.dshutils.logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static File logFile;
    private static boolean enabled = false;
    private static String logPrefix;
    private static String fileName;

    public static void initiate(JavaPlugin plugin, String name, String prefix) {
        logPrefix = prefix;
        fileName = name;
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }

        File log = new File(plugin.getDataFolder(), fileName + ".log");
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logFile = log;
        enabled = true;
    }

    public static void log(LogLevel logLevel, String message, boolean logToConsole) {
        if (enabled) {
            if (logToConsole) {
                ConsoleLogger.log(logLevel, message);
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(logFile, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter pw = new PrintWriter(fw);
            String date = new SimpleDateFormat("[dd/MM/yyyy]---[HH:mm:ss]").format(new Date());
            pw.println("\n" + date + logLevel.getPrefix() + " " + message + "\n");
            pw.flush();
            pw.close();
        }
    }

    public static void log(LogLevel logLevel, String message, Exception e, boolean logToConsole) {
        if (enabled) {
            if (logToConsole) {
                ConsoleLogger.stackTraceAlert(logLevel, message);
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(logFile, true);
            } catch (IOException ioe) {
                e.printStackTrace();
            }
            PrintWriter pw = new PrintWriter(fw);
            String date = new SimpleDateFormat("[dd/MM/yyyy]---[HH:mm:ss]").format(new Date());
            pw.println("\n" + date + logLevel.getPrefix() + " " + message);

            e.printStackTrace(pw);

            pw.println("\n");

            pw.flush();
            pw.close();
        }
    }

    public static String getFileName() {
        return fileName;
    }

    public enum LogLevel {
        NORMAL(ChatColor.GREEN + "[INFO]"),
        SEVERE(ChatColor.RED + "[SEVERE]"),
        WARNING(ChatColor.RED + "[WARNING]");

        private String prefix;

        LogLevel(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return Logger.logPrefix + " " + this.prefix;
        }
    }
}