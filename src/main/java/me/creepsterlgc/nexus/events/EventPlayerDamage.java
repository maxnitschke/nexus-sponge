package main.java.me.creepsterlgc.nexus.events;

import java.util.HashMap;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerDamage {

    @Listener
    public void onEventDamageEntity(DamageEntityEvent event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "nexus.powertool")) {

    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

}
