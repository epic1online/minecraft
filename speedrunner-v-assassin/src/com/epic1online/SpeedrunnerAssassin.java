package com.epic1online;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.epic1online.listeners.*;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class SpeedrunnerAssassin extends JavaPlugin {
	private static SpeedrunnerAssassin instance;

	public static boolean enabled = false;
	public static boolean frozen = false;
	public static ArrayList<String> assassins = new ArrayList<>();
	public static ArrayList<String> speedrunners = new ArrayList<>();
	public static ArrayList<Integer> compassTarget = new ArrayList<>();
	public static Player frozenPlayer;

	public static SpeedrunnerAssassin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		this.getCommand("spdasnEnable").setExecutor(new Enable());
		this.getCommand("speedrunner").setExecutor(new Speedrunner());
		this.getCommand("assassin").setExecutor(new Assassin());
		getServer().getPluginManager().registerEvents(new Listeners(), this);

		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (enabled) {
					trace();
				}
			}
		}, 0L, 1L);

	}

	@Override
	public void onDisable() {
		instance = null;
	}

	private void trace() {
		frozen = false;
		for (int numSpeed = 0; numSpeed < speedrunners.size(); numSpeed++) {
			int range = 48;
			boolean hasPlayers = false;
			Player player = getInstance().getServer().getPlayer(speedrunners.get(numSpeed));
			List<Entity> nearEntities = player.getNearbyEntities(range, range, range);

			for (int i = 0; i < nearEntities.size(); i++) { // checking if players are in range
				if (nearEntities.get(i) instanceof Player) {
					hasPlayers = true;
				}
			}

			if (hasPlayers) {
				Location origin = player.getEyeLocation();
				Vector direction = player.getLocation().getDirection();
				Location sightLine = origin;
				boolean blocked = false;
				ArrayList<double[]> particleLoc = new ArrayList<double[]>();
				World world = player.getWorld();

				for (int i = 0; i < range; i++) { // moving in eye direction 1 block at time
					sightLine.add(direction.getX(), direction.getY(), direction.getZ());

					if (!sightLine.getBlock().isLiquid() && !sightLine.getBlock().getType().isAir()
							&& !sightLine.getBlock().isPassable()) { // check to see if line of sight is blocked
						blocked = true;
						break;
					}

					double temp[] = { sightLine.getX(), sightLine.getY(), sightLine.getZ() };
					particleLoc.add(temp); // add location to spawn particle

					for (int j = 0; j < sightLine.getBlock().getChunk().getEntities().length; j++) {
						Entity entity = sightLine.getBlock().getChunk().getEntities()[j]; // get an entity in the chunk

						if (entity.getBoundingBox().expand(0.5).contains(sightLine.toVector())
								/* && entity instanceof Player && (Player) entity != player */
								&& assassins.contains(((Player) entity).getName())) {
							frozenPlayer = (Player) entity; // // if the entity is in the line of sight and an assassin
							frozen = true; // sets as frozen

							spawnParticles(world, player, player.getEyeLocation().distance(sightLine), particleLoc);

						} else if (entity.getBoundingBox().expand(0.5).contains(sightLine.toVector())) {
							blocked = true;// checks if another entity blocked the line of sight
							break;
						}

					}

					if (blocked) {
						frozen = false;
						break;
					}

				}
			}
		}
	}

	public void spawnParticles(World world, Player player, double distance, ArrayList<double[]> particleLoc) {
		for (int f = 0; f < particleLoc.size() - 1; f++) { // spawns the particle on the line of sight
			if (distance > 4 && f == 0) {
				f++;
			}
			world.spawnParticle(Particle.REDSTONE, particleLoc.get(f)[0], particleLoc.get(f)[1], particleLoc.get(f)[2],
					1, new Particle.DustOptions(Color.RED, (float) 0.5));
		}

	}

}
