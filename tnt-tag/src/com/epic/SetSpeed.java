package com.epic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SetSpeed implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 2) {
			int customSpeed = -1;
			try {
				customSpeed += Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				return false;
			}
			if (args[1].equalsIgnoreCase("carrier")) {
				TNTTag.getInstance().tntSpeed = new PotionEffect(PotionEffectType.SPEED, 6000000, customSpeed, true, false);
				sender.sendMessage("§aThe tnt carrier speed has been set to " + (customSpeed + 1));
				return true;
			} else if (args[1].equalsIgnoreCase("player")) {
				TNTTag.getInstance().playerSpeed = new PotionEffect(PotionEffectType.SPEED, 6000000, customSpeed, true, false);
				sender.sendMessage("§aThe player speed has been set to " + (customSpeed + 1));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
