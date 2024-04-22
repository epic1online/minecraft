package com.epic;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 3 || !(sender instanceof Player)) {
			return false;
		} else if (args.length == 3) {
			World world = ((Player) sender).getWorld();
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			TNTTag.getInstance().setSpawn(new Location(world, x, y, z));
			sender.sendMessage("§aThe game spawnpoint has been set to " + x + " " + y + " " + z);
			return true;
		} else if (args.length == 0) {
			TNTTag.getInstance().setSpawn(((Player) sender).getLocation());
			sender.sendMessage("§aThe game spawnpoint has been set to your current location");
			return true;
		} else {
			return false;
		}
	}

}
