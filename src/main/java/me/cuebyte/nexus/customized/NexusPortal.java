package main.java.me.cuebyte.nexus.customized;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

public class NexusPortal {

	private String name;
	private String zone;
	private String warp;
	private String message;
	
	public NexusPortal(String name, String region, String warp, String message) {
		this.name = name;
		this.zone = region;
		this.warp = warp;
		this.message = message;
	}
	
	public void setName(String name) { this.name = name; }
	public void setZone(String zone) { this.zone = zone; }
	public void setWarp(String warp) { this.warp = warp; }
	public void setMessage(String message) { this.message = message; }
	
	public String getName() { return name; }
	public String getZone() { return zone; }
	public String getWarp() { return warp; }
	public String getMessage() { return message; }
	
	public void insert() {
		NexusDatabase.queue("INSERT INTO portals VALUES ('" + name + "', '" + zone + "', '" + warp + "', '" + message + "')");
		NexusDatabase.addPortal(name, this);
	}
	
	public void update() {
		NexusDatabase.queue("UPDATE portals SET zone = '" + zone + "', warp = '" + warp + "', message = '" + message + "' WHERE name = '" + name + "'");
		NexusDatabase.addPortal(name, this);
	}
	
	public void delete() {
		NexusDatabase.queue("DELETE FROM portals WHERE name = '" + name + "'");
		NexusDatabase.removePortal(name);
	}
	
	public void transport() {
		
		NexusZone z = NexusDatabase.getZone(zone); if(z == null) return;
		NexusWarp w = NexusDatabase.getWarp(warp); if(w == null) return;
		
		if(!Controller.getServer().getWorld(w.getWorld()).isPresent()) return;
		World world = Controller.getServer().getWorld(w.getWorld()).get();
		
		for(Player player : Controller.getPlayers()) {
			if(!z.isInside(player)) continue;
			if(!PermissionsUtils.has(player, "nexus.portal.teleport." + name)) continue;
			player.setLocation(new Location<World>(world, w.getX(), w.getY(), w.getZ()));
			player.sendMessage(TextUtils.color(message));
		}
		
	}
	
}
