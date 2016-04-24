package main.java.me.cuebyte.nexus.events;

import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusChannel;
import main.java.me.cuebyte.nexus.customized.NexusChannels;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.files.FileChat;
import main.java.me.cuebyte.nexus.files.FileMessages;
import main.java.me.cuebyte.nexus.files.FileMotd;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerJoin {
	
	@Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    	
    	Player player = event.getTargetEntity();
    	
		String uuid = player.getUniqueId().toString();
		String name = player.getName().toLowerCase();
		
		NexusPlayer player_uuid = NexusDatabase.getPlayer(uuid);
		NexusPlayer player_name = NexusDatabase.getPlayer(NexusDatabase.getUUID(name));
		
    	if(FileMessages.EVENTS_JOIN_ENABLE()) {
    		event.setMessage(TextUtils.color(FileMessages.EVENTS_JOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
		NexusPlayer pl = NexusDatabase.getPlayer(event.getTargetEntity().getUniqueId().toString());
		if(pl != null) {
			pl.setLastaction(System.currentTimeMillis());
			NexusDatabase.addPlayer(pl.getUUID(), pl);
		}
		
		if(player_uuid == null && player_name == null) {
			
			NexusPlayer p = new NexusPlayer(uuid, name, "", "", 0, 0, 0, 0, 0, "", "", "", System.currentTimeMillis(), System.currentTimeMillis());
			p.setLastaction(System.currentTimeMillis());
			p.insert();
			
	    	if(FileMessages.EVENTS_FIRSTJOIN_ENABLE()) {
	    		event.setMessage(TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
	    		if(FileMessages.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_SHOW()) {
		    		event.setMessage(Text.of(TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", player.getName())), "\n", TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_MESSAGE().replaceAll("%players", String.valueOf(NexusDatabase.getPlayers().size())))));
	    		}
	    	}
			
		}
		else if(player_uuid == null && player_name != null) {
			
			NexusDatabase.removePlayer(player_name.getUUID());
			NexusDatabase.removeUUID(player_name.getName());
			
			player_name.setName(player_name.getUUID());
			player_name.update();
			
			NexusPlayer p = new NexusPlayer(uuid, name, "", "", 0, 0, 0, 0, 0, "", "", "", System.currentTimeMillis(), System.currentTimeMillis());
			p.setLastaction(System.currentTimeMillis());
			p.insert();
			
	    	if(FileMessages.EVENTS_FIRSTJOIN_ENABLE()) {
	    		event.setMessage(TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
	    		if(FileMessages.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_SHOW()) {
		    		event.setMessage(Text.of(TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", player.getName())), "\n", TextUtils.color(FileMessages.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_MESSAGE().replaceAll("%players", String.valueOf(NexusDatabase.getPlayers().size())))));
	    		}
	    	}
			
		}
		else if(player_uuid != null && player_name == null) {
			
			NexusDatabase.removePlayer(player_uuid.getUUID());
			NexusDatabase.removeUUID(player_uuid.getName());
			
			player_uuid.setName(name);
			player_uuid.setLastaction(System.currentTimeMillis());
			player_uuid.update();
			
			ServerUtils.broadcast(Text.of(TextColors.YELLOW, player_uuid.getName(), " is now known as ", player.getName(), "!"));
			
		}
		else {
			
		}
    	
		if(FileMotd.SHOW_ON_JOIN()) {
			for(String s : FileMotd.MESSAGE()) {
				s = s.replaceAll("%player", player.getName());
				player.sendMessage(TextUtils.color(s));
			}
		}
		
		if(FileChat.CHANNELS()) {
			for(Entry<String, NexusChannel> e : NexusChannels.all().entrySet()) {
				NexusChannel c = e.getValue();
				if(PermissionsUtils.has(player, "nexus.channel.autojoin." + c.getName().toLowerCase())) {
					pl.setChannel(c.getName().toLowerCase());
					player.sendMessage(TextUtils.color("&7Automatically joined channel: " + c.getName()));
					break;
				}
			}
		}
		
    }

}
