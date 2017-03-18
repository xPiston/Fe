package org.melonbrew.fe.util;

import java.io.ByteArrayOutputStream;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.melonbrew.fe.Fe;

public class PluginMessageTask extends BukkitRunnable {
	private final Fe plugin;
	private final Player player;
	private final ByteArrayOutputStream bytes;

	public PluginMessageTask(Fe plugin, Player player, ByteArrayOutputStream bytes)
	{
		this.plugin = plugin;
		this.player = player;
		this.bytes = bytes;
	}

	public PluginMessageTask(Fe plugin, ByteArrayOutputStream bytes) throws Exception {
		this.plugin = plugin;
		this.bytes = bytes;
		
		if (plugin.getServer().getOnlinePlayers().size() == 0)
			throw new Exception("PluginMessage requires an online player to be sent.");

		this.player = plugin.getServer().getOnlinePlayers().iterator().next();
	}

	public void run() {
		player.sendPluginMessage(plugin, plugin.outgoingChannel, bytes.toByteArray());
	}
}
