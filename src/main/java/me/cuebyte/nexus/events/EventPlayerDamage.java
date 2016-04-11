package main.java.me.cuebyte.nexus.events;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;


public class EventPlayerDamage {

    @Listener
    public void onEventDamageEntity(DamageEntityEvent event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

    	if(p.getGod() == 1) {
        	event.setBaseDamage(0);
        	event.setCancelled(true);
    	}
    	
    }

}
