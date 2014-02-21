package com.dsh105.dshutils.apiutil;

import mkremins.fanciful.FancyMessage;
import org.bukkit.entity.Player;

import java.util.List;

public class Fanciful {

    public static void sendMessage(FancyMessage fancyMessage, Player player) {
        fancyMessage.send(player);
    }

    public static void sendMessage(FancyMessage fancyMessage, List<Player> players) {
        for (Player p : players) {
            sendMessage(fancyMessage, p);
        }
    }
}