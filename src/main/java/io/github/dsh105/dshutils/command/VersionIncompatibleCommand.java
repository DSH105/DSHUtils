package io.github.dsh105.dshutils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VersionIncompatibleCommand implements CommandExecutor {

    private String cmdLabel;
    private String pluginPrefix;
    private String msg;

    public VersionIncompatibleCommand(String cmdLabel, String pluginPrefix, String msg) {
        this.cmdLabel = cmdLabel;
        this.pluginPrefix = pluginPrefix;
        this.msg = msg;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(this.pluginPrefix + " " + this.msg);
        return true;
    }
}