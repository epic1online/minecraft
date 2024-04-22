package com.epic1online;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Enable implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		SpeedrunnerAssassin.enabled = !SpeedrunnerAssassin.enabled; 
		if (SpeedrunnerAssassin.enabled) {
			sender.sendMessage("enabled");
		} else {
			sender.sendMessage("disabled");
		}

		return true;
	}

}
