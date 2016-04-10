package main.java.me.cuebyte.nexus.events;

import main.java.me.cuebyte.nexus.customized.NexusBan;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.customized.NexusSpawn;
import main.java.me.cuebyte.nexus.utils.TimeUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class EventPlayerLogin {

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {

    	NexusBan ban = NexusDatabase.getBan(event.getProfile().getUniqueId().toString());

    	if(ban != null) {

    		if(ban.getDuration() != 0 && ban.getDuration() <= System.currentTimeMillis()) {
    			NexusDatabase.removeBan(event.getProfile().getUniqueId().toString());
    			ban.delete();
    		}
    		else {
	    		String time = TimeUtils.toString(ban.getDuration() - System.currentTimeMillis());
	    		event.setMessage(Text.of(TextColors.GRAY, "Banned for another: ", TextColors.RED, time, "\n\n", TextColors.RED, "Reason: ", TextColors.GRAY, ban.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}

    	}

		String uuid = event.getProfile().getUniqueId().toString();
		NexusPlayer player_uuid = NexusDatabase.getPlayer(uuid);

		if(player_uuid == null) {

			NexusSpawn spawn = NexusDatabase.getSpawn("default");
			if(spawn != null) {

				if(Sponge.getGame().getServer().getWorld(spawn.getWorld()).isPresent()) {
					Transform<World> t = event.getToTransform();
					t.setExtent(Sponge.getGame().getServer().getWorld(spawn.getWorld()).get());
					t.setPosition(new Vector3d(spawn.getX(), spawn.getY(), spawn.getZ()));
					event.setToTransform(t);
				}

			}

		}

    }

}
