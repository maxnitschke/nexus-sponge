package main.java.me.creepsterlgc.nexus.events;

import java.util.HashMap;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerInteractBlock {

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Primary event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
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

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Secondary event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
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
