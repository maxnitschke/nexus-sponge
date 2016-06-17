package main.java.me.cuebyte.nexus.events;

import java.util.HashMap;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerInteractEntity {

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Primary event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && PermissionsUtils.has(player, "nexus.powertool")) {

    		ItemStack i = player.getItemInHand(HandTypes.MAIN_HAND).get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Secondary event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && PermissionsUtils.has(player, "nexus.powertool")) {

    		ItemStack i = player.getItemInHand(HandTypes.MAIN_HAND).get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

}
