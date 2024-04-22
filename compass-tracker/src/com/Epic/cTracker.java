package com.Epic;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.Epic.listeners.Listeners;

public class cTracker extends JavaPlugin {
	private static cTracker instance;
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Integer> compassTargets = new ArrayList<Integer>();
	private static boolean pEnabled = true;

	public static cTracker getInstance() {
		return instance;
	}

	@Override
	public void onEnable () {
		instance = this;
		
		this.getCommand("cTracker").setExecutor(new cTrackerCMD());
		
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getServer().getOnlinePlayers().forEach(player -> {
			if (!players.contains(player)) {
				players.add(player);
				compassTargets.add(0);
			}
		});
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}

	public static ArrayList<Player> getPlayers() {
		return players;
	}

	public static void setPlayers(ArrayList<Player> players) {
		cTracker.players = players;
	}
	
	public static void addPlayer(Player player) {
		cTracker.players.add(player);
	}

	public static ArrayList<Integer> getCompassTargets() {
		return compassTargets;
	}

	public static void setCompassTargets(ArrayList<Integer> compassTargets) {
		cTracker.compassTargets = compassTargets;
	}

	public static boolean isPEnabled() {
		return pEnabled;
	}

	public static void setPEnabled(boolean pEnabled) {
		cTracker.pEnabled = pEnabled;
	}

}
