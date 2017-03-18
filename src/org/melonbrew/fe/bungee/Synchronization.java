package org.melonbrew.fe.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.melonbrew.fe.Fe;
import org.melonbrew.fe.database.Account;
import org.melonbrew.fe.util.PluginMessageTask;

public class Synchronization {
	public Fe plugin;
	
	public Synchronization(Fe plugin) {
		this.plugin = plugin;
	}
	
	public void castTransaction(Account account, double amount) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
	
			out.writeUTF("Forward");
			out.writeUTF("ALL");
			out.writeUTF("Fe");
	
			ByteArrayOutputStream msgbytesos = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytesos);
			
			msgout.writeUTF("Transaction");
			msgout.writeUTF(account.getName());
			msgout.writeUTF(account.getUUID());
			msgout.writeDouble(amount);
	
			out.writeShort(msgbytesos.toByteArray().length);
			out.write(msgbytesos.toByteArray());
			new PluginMessageTask(plugin, b).runTaskAsynchronously(plugin);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
