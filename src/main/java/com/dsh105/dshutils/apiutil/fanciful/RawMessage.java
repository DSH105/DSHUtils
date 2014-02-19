package com.dsh105.dshutils.apiutil.fanciful;

import net.minecraft.server.v1_7_R1.ChatSerializer;
import net.minecraft.server.v1_7_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

/**
 * Fanciful, by mkremins: https://forums.bukkit.org/threads/195148/
 */

public class RawMessage {
	
	private final List<FancyMessage> messageParts;
	
	public RawMessage(final String firstPartText) {
		messageParts = new ArrayList<FancyMessage>();
		messageParts.add(new FancyMessage(firstPartText));
	}
	
	public RawMessage color(final ChatColor color) {
		if (!color.isColor()) {
			throw new IllegalArgumentException(color.name() + " is not a color");
		}
		latest().color = color;
		return this;
	}
	
	public RawMessage style(final ChatColor... styles) {
		for (final ChatColor style : styles) {
			if (!style.isFormat()) {
				throw new IllegalArgumentException(style.name() + " is not a style");
			}
		}
		latest().styles = styles;
		return this;
	}
	
	public RawMessage file(final String path) {
		onClick("open_file", path);
		return this;
	}
	
	public RawMessage link(final String url) {
		onClick("open_url", url);
		return this;
	}
	
	public RawMessage suggest(final String command) {
		onClick("suggest_command", command);
		return this;
	}
	
	public RawMessage command(final String command) {
		onClick("run_command", command);
		return this;
	}
	
	public RawMessage achievementTooltip(final String name) {
		onHover("show_achievement", "achievement." + name);
		return this;
	}
	
	public RawMessage itemTooltip(final String itemJSON) {
		onHover("show_item", itemJSON);
		return this;
	}
	
	public RawMessage tooltip(final String text) {
		onHover("show_text", text);
		return this;
	}
	
	public RawMessage then(final Object obj) {
		messageParts.add(new FancyMessage(obj.toString()));
		return this;
	}
	
	public String toJSONString() {
		final JSONStringer json = new JSONStringer();
		try {
			if (messageParts.size() == 1) {
				latest().writeJson(json);
			} else {
				json.object().key("text").value("").key("extra").array();
				for (final FancyMessage part : messageParts) {
					part.writeJson(json);
				}
				json.endArray().endObject();
			}
		} catch (final JSONException e) {
			throw new RuntimeException("invalid message");
		}
		return json.toString();
	}
	
	public void send(Player player){
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(toJSONString())));
	}
	
	private FancyMessage latest() {
		return messageParts.get(messageParts.size() - 1);
	}
	
	private void onClick(final String name, final String data) {
		final FancyMessage latest = latest();
		latest.clickActionName = name;
		latest.clickActionData = data;
	}
	
	private void onHover(final String name, final String data) {
		final FancyMessage latest = latest();
		latest.hoverActionName = name;
		latest.hoverActionData = data;
	}
}
