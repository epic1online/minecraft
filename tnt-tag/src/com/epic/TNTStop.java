package com.epic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

public class TNTStop implements CommandExecutor {

	Game game = TNTStart.game;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 0) {
			return false;
		} else {
			if (TNTTag.getInstance().ongoingGame) {
				TNTTag.getInstance().ongoingGame = false;
				if (game.endRound != null) {
					game.endRound.cancel();
				}
				if (game.startRound != null) {
					game.startRound.cancel();
				}

				TNTTag.getInstance().getPlayerList().forEach(player -> {
					player.getInventory().clear();
					player.removePotionEffect(PotionEffectType.SPEED);
				});
				
				TNTTag.getInstance().getTntList().clear();
				TNTTag.getInstance().getPlayerList().clear();
				sender.sendMessage("§4The game has been cancelled");
				
			} else {
				sender.sendMessage("§4There is no ongoing game to cancel");
			}
			return true;
		}
	}

}
