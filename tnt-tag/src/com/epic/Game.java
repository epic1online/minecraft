package com.epic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Game {

	private final TNTTag instance = TNTTag.getInstance();
	public BukkitTask endRound;
	public BukkitTask startRound;
	public BukkitTask timeDisplay;
	private int remainingTime;

	public void main() {
		instance.ongoingGame = true;
		startRound();
	}

	private void startRound() {
		ArrayList<Player> tntList = instance.getTntList();
		ArrayList<Player> playerList = instance.getPlayerList();
		if (playerList.size() <= 5) {
			playerList.forEach(player -> player.teleport(instance.getSpawn()));
		}

		if (playerList.size() <= 4) {
//			Bukkit.getLogger().info("1 tnt");
			tntList.add(playerList.get((int) (Math.random() * 2)));

		} else if (playerList.size() <= 6) {
//			Bukkit.getLogger().info("2 tnt");
			int temp = (int) (Math.random() * playerList.size() - 1), temp2;
			do {
				temp2 = (int) (Math.random() * playerList.size() - 1);
			} while (temp2 == temp);

			tntList.add(playerList.get(temp));
			tntList.add(playerList.get(temp2));

		} else if (playerList.size() <= 10) {
//			Bukkit.getLogger().info("3 tnt");
			int temp = (int) (Math.random() * playerList.size() - 1), temp2, temp3;
			do {
				temp2 = (int) (Math.random() * playerList.size() - 1);
			} while (temp2 == temp);
			do {
				temp3 = (int) (Math.random() * playerList.size() - 1);
			} while (temp3 == temp && temp3 == temp2);

			tntList.add(playerList.get(temp));
			tntList.add(playerList.get(temp2));
			tntList.add(playerList.get(temp3));

		} else if (playerList.size() <= 14) {
//			Bukkit.getLogger().info("4 tnt");
			int temp = (int) (Math.random() * playerList.size() - 1), temp2, temp3, temp4;
			do {
				temp2 = (int) (Math.random() * playerList.size() - 1);
			} while (temp2 == temp);
			do {
				temp3 = (int) (Math.random() * playerList.size() - 1);
			} while (temp3 == temp && temp3 == temp2);
			do {
				temp4 = (int) (Math.random() * playerList.size() - 1);
			} while (temp4 == temp && temp4 == temp2 && temp4 == temp3);

			tntList.add(playerList.get(temp));
			tntList.add(playerList.get(temp2));
			tntList.add(playerList.get(temp3));
			tntList.add(playerList.get(temp4));

		} else {
//			Bukkit.getLogger().info("5 tnt");
			int temp = (int) (Math.random() * playerList.size() - 1), temp2, temp3, temp4, temp5;
			do {
				temp2 = (int) (Math.random() * playerList.size() - 1);
			} while (temp2 == temp);
			do {
				temp3 = (int) (Math.random() * playerList.size() - 1);
			} while (temp3 == temp && temp3 == temp2);
			do {
				temp4 = (int) (Math.random() * playerList.size() - 1);
			} while (temp4 == temp && temp4 == temp2 && temp4 == temp3);
			do {
				temp5 = (int) (Math.random() * playerList.size() - 1);
			} while (temp5 == temp && temp5 == temp2 && temp5 == temp3 && temp5 == temp4);

			tntList.add(playerList.get(temp));
			tntList.add(playerList.get(temp2));
			tntList.add(playerList.get(temp3));
			tntList.add(playerList.get(temp4));
			tntList.add(playerList.get(temp5));

		}
		tntList.forEach(player -> {
			player.getEquipment().setHelmet(new ItemStack(Material.TNT));
			for (int i = 0; i < 9; i++) {
				player.getInventory().setItem(i, new ItemStack(Material.TNT));
			}
			instance.tntSpeed.apply(player);
			instance.getServer().broadcastMessage(ChatColor.YELLOW + player.getName() + " was given the TNT!");
		});
		playerList.forEach(player -> {
			if (!tntList.contains(player)) {
				instance.playerSpeed.apply(player);
			}
		});
		instance.setTntList(tntList);

		int roundLength = playerList.size() * 10;
		if (roundLength > 120) {
			roundLength = 120;
		}
		remainingTime = roundLength;
		endRound = new EndRound().runTaskLater(instance, roundLength * 20);
		timeDisplay = new DisplayTime().runTaskTimer(instance, 0, 1 * 20);
	}

	class EndRound extends BukkitRunnable {

		@Override
		public void run() {
			ArrayList<Player> tntList = instance.getTntList();
			ArrayList<Player> playerList = instance.getPlayerList();

			tntList.forEach(player -> {
				playerList.remove(player);
				player.getEquipment().clear();
				player.removePotionEffect(PotionEffectType.SPEED);
				player.teleport(instance.getSpectate());
				player.getServer().broadcastMessage(ChatColor.GREEN + player.getName() + " blew up!");
				Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.playSound(player.getLocation(),
						Sound.ENTITY_GENERIC_EXPLODE, 1, 1));
			});

			tntList.clear();
			instance.setPlayerList(playerList);
			instance.setTntList(tntList);

			if (playerList.size() == 1) {
				playerList.get(0).getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + playerList.get(0).getName() + " wins!");
				Bukkit.getOnlinePlayers().forEach(
						player -> player.sendTitle(ChatColor.LIGHT_PURPLE + playerList.get(0).getName() + " wins!", null, 10, 70, 20));
				TNTTag.getInstance().getPlayerList().forEach(player -> {
					player.getEquipment().clear();
					player.removePotionEffect(PotionEffectType.SPEED);
				});
				TNTTag.getInstance().getTntList().clear();
				TNTTag.getInstance().getPlayerList().clear();
				instance.ongoingGame = false;
				this.cancel();
				if (startRound != null) startRound.cancel();
			} else {
				startRound = new StartRound().runTaskLater(instance, 7 * 20);
			}
		}

	}

	class StartRound extends BukkitRunnable {

		@Override
		public void run() {
			startRound();
		}
	}

	class DisplayTime extends BukkitRunnable {

		@Override
		public void run() {
			String colour = "e";
			Boolean sendMessage = false;

			if (remainingTime >= 60) {
				if ((remainingTime % 30) == 0) {
					sendMessage = true;
				}
			} else if (remainingTime >= 15) {
				if ((remainingTime % 15) == 0) {
					if (remainingTime >= 30) {
						colour = "6";
					} else {
						colour = "c";
					}
					sendMessage = true;
				}
			} else if (remainingTime <= 5) {
				colour = "4";
				sendMessage = true;
			}

			if (sendMessage) {
				String temp = colour;
				Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent("§" + temp + remainingTime + " seconds remaining")));
			}

			if (remainingTime == 0) {
				this.cancel();
			}

			remainingTime--;
		}

	}

}
