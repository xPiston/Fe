package org.melonbrew.fe.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.melonbrew.fe.Fe;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class FeMessageListener implements PluginMessageListener {
	private Fe plugin;

	public FeMessageListener(Fe plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals(plugin.incomingChannel))
			return;
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		
		if (subchannel.equals("Fe")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			
			try {
				if (msgin.readUTF().equals("Transaction")) {
					String name = msgin.readUTF();
					String uuid = msgin.readUTF();
					double amount = msgin.readDouble();
					
					if (plugin.getServer().getPlayer(UUID.fromString(uuid)) == null)
						return;
					
					plugin.getAPI().getAccount(name, uuid).deposit(amount);
					
					if (plugin.getConfig().getBoolean("debug", false))
						plugin.log("Bungee transaction: name=" + name + ", uuid=" + uuid + ", amount=" + amount);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
