package com.epic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Version implements CommandExecutor {
	
	final double version = 1.7;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("The current version of TNT Tag is: §c" + version);
		return true;
	}

}
