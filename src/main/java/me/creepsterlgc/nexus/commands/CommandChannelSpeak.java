package main.java.me.creepsterlgc.nexus.commands;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.customized.NexusChannel;
import main.java.me.creepsterlgc.nexus.customized.NexusChannels;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.files.FileChat;
import main.java.me.creepsterlgc.nexus.utils.CommandUtils;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandChannelSpeak {

	public CommandChannelSpeak(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/channel <channel> <mesage>")); return; }
	
		String channel = args[0].toLowerCase();
		NexusChannel c = NexusChannels.get(channel);
		
		if(c == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PermissionsUtils.has(sender, "nexus.channel.leave." + channel)) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not have permissions to leave this channel!"));
			return;
		}
		
		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
    	String name = player.getName();
		String message = CommandUtils.combineArgs(1, args);
    	if(!p.getNick().equalsIgnoreCase("")) name = FileChat.NICKPREFIX() + p.getNick();
    	if(!PermissionsUtils.has(player, "nexus.chat.color")) { message = TextUtils.uncolor(message); }
    	
    	String prefix = PermissionsUtils.getPrefix(player);
    	String suffix = PermissionsUtils.getSuffix(player);
    		
    	if(!PermissionsUtils.has(player, "nexus.channel.speak." + c.getID())) {
    		player.sendMessage(Text.of(TextColors.RED, "You do not have permissions to speak in this channel!"));
    		return;
    	}
    		
    	String cprefix = c.getPrefix();
    	String csuffix = c.getSuffix();
    		
	    String format = c.getFormat();
	    	
	    format = format
	    		.replaceAll("%prefix", prefix)
	    		.replaceAll("%suffix", suffix)
	    		.replaceAll("%player", name)
	    		.replaceAll("%message", message)
	    		.replaceAll("%cprefix", cprefix)
				.replaceAll("%csuffix", csuffix)
	    		.replaceAll("%world", player.getWorld().getName());
	    	
	    Text total = TextUtils.color(format);
	    	
	    String range = c.getRange();
	    	
	    if(range.equalsIgnoreCase("global")) {
	    	for(Player t : Controller.getPlayers()) {
	    		if(!t.hasPermission("nexus.channel.receive." + channel)) continue;
	    		t.sendMessage(total);
	    	}
	    }
	    else if(range.equalsIgnoreCase("world")) {
	    	for(Player t : Controller.getPlayers()) {
	    		if(!t.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) continue;
	    		if(!t.hasPermission("nexus.channel.receive." + channel)) continue;
	    		t.sendMessage(total);
	    	}
	    }
	    else {
	    	int radius;
	    	try { radius = Integer.parseInt(c.getRange()); }
	    	catch(NumberFormatException e) {
	    		player.sendMessage(Text.of(TextColors.RED, "Invalid range in channels config!"));
	    		return;
	    	}
	    	for(Player t : Controller.getPlayers()) {
	    		if(!t.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) continue;
	    			
				Location<World> l = t.getLocation();
				double x = player.getLocation().getX();
				double z = player.getLocation().getZ();
				boolean hit_x = false;
				boolean hit_z = false;
				if(l.getX() <= x + radius && l.getX() >= x - radius) hit_x = true;
				if(l.getZ() <= z + radius && l.getZ() >= z - radius) hit_z = true;
				if(!hit_x || !hit_z) continue;
					
	    		if(!t.hasPermission("nexus.channel.receive." + channel)) continue;
	    		t.sendMessage(total);
	    	}
	    	
	    }
		
	}

}
