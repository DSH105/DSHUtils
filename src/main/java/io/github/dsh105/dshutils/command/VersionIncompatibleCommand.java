package io.github.dsh105.dshutils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VersionIncompatibleCommand implements CommandExecutor {

    private String cmdLabel;
    private String pluginPrefix;
    private String msg;
    private String perm;
    private String permMsg;

    public VersionIncompatibleCommand(String cmdLabel, String pluginPrefix, String msg, String perm, String permMsg) {
        this.cmdLabel = cmdLabel;
        this.pluginPrefix = pluginPrefix;
        this.msg = msg;
        this.perm = perm;
        this.permMsg = permMsg;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission(this.perm)) {
            commandSender.sendMessage(this.pluginPrefix + " " + this.msg);
        } else {
            commandSender.sendMessage(this.pluginPrefix + " " + this.permMsg);
        }
        return true;
    }
}