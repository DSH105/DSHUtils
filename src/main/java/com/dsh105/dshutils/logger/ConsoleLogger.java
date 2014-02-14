package com.dsh105.dshutils.logger;

import com.dsh105.dshutils.DSHPlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class ConsoleLogger {

    private static ConsoleCommandSender console;

    public static void initiate() {
        console = DSHPlugin.getPluginInstance().getServer().getConsoleSender();
    }

    public static void log(Logger.LogLevel logLevel, String message) {
        console.sendMessage(logLevel.getPrefix() + " " + message);
    }

    public static void log(String message) {
        log(Logger.LogLevel.NORMAL, message);
    }

    public static void stackTraceAlert(Logger.LogLevel logLevel, String message) {
        console.sendMessage(logLevel.getPrefix() + " " + message);
        console.sendMessage(logLevel.getPrefix() + " See the Log File for details [" + Logger.getFileName() + ".log].");
    }
}
