package main.java.me.cuebyte.nexus.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.files.FileMessages;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.SerializeUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;


public class EventPlayerQuit {

	@Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event) {
		
    	if(FileMessages.EVENTS_LEAVE_ENABLE()) {
    		event.setMessage(TextUtils.color(FileMessages.EVENTS_LEAVE_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
    	Player player = event.getTargetEntity();
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
    	
    	String world = player.getWorld().getName();
    	double x = player.getLocation().getX();
    	double y = player.getLocation().getY();
    	double z = player.getLocation().getZ();
    	double yaw = 0;
    	double pitch = 0;
    	String location = SerializeUtils.location(world, x, y, z, yaw, pitch);
    	
    	p.setLastlocation(location);
    	p.setLastseen(System.currentTimeMillis());
    	p.update();
    	
    	if(p.getGod() != 0) {
    		if(!PermissionsUtils.has(player, "nexus.god-rejoin")) {
    			p.setGod(0);
    			p.update();
    		}
    	}
    	
    }
	
}
