package com.epic;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.epic.listeners.Listeners;

public class TNTTag extends JavaPlugin {

	private static TNTTag instance;
	private ArrayList<Player> tntList = new ArrayList<Player>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private Location spawn, spectate;
	public boolean ongoingGame = false;
	public PotionEffect tntSpeed = new PotionEffect(PotionEffectType.SPEED, 6000000, 2, true, false);
	public PotionEffect playerSpeed = new PotionEffect(PotionEffectType.SPEED, 6000000, 1, true, false);

	@Override
	public void onEnable() {
		instance = this;
		
		this.getCommand("setspawn").setExecutor(new SetSpawn());
		this.getCommand("setspec").setExecutor(new SetSpectate());
		this.getCommand("tntstart").setExecutor(new TNTStart());
		this.getCommand("addplayer").setExecutor(new AddPlayer());
		this.getCommand("removeplayer").setExecutor(new RemovePlayer());
		this.getCommand("tntstop").setExecutor(new TNTStop());
		this.getCommand("setspeed").setExecutor(new SetSpeed());
		this.getCommand("tntversion").setExecutor(new Version());
		getServer().getPluginManager().registerEvents(new Listeners(), this);

	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public static TNTTag getInstance() {
		return instance;
	}

	public ArrayList<Player> getTntList() {
		return tntList;
	}

	public void setTntList(ArrayList<Player> tnt) {
		this.tntList = tnt;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpectate() {
		return spectate;
	}

	public void setSpectate(Location spectate) {
		this.spectate = spectate;
	}
	
}
