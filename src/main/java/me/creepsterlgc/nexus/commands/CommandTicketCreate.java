package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusTicket;
import main.java.me.creepsterlgc.nexus.utils.CommandUtils;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketCreate {

	public CommandTicketCreate(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ticket create <message>")); return; }
	
		Player player = (Player) sender;
		String message = CommandUtils.combineArgs(1, args);
		
		int tickets = 0;
		for(Entry<Integer, NexusTicket> e : NexusDatabase.getTickets().entrySet()) {
			NexusTicket ticket = e.getValue();
			if(!ticket.getStatus().equalsIgnoreCase("open")) continue;
			if(!ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) continue;
			tickets += 1;
		}
		
		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PermissionsUtils.has(player, "nexus.ticket.create." + i)) possible = i;
		}
		
		if(!PermissionsUtils.has(player, "nexus.ticket.create-unlimited") && possible <= tickets) {
			if(possible == 1) sender.sendMessage(Text.builder("You are only allowed to own " + possible + " open ticket!").color(TextColors.RED).build());
			else sender.sendMessage(Text.builder("You are only allowed to own " + possible + " open tickets!").color(TextColors.RED).build());
			return;
		}
		
		int id = NexusDatabase.getTickets().size() + 1;
		
		String world = player.getWorld().getName();
		
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		
		double time = System.currentTimeMillis();
		
		NexusTicket ticket = new NexusTicket(id, player.getUniqueId().toString(), message, time, new ArrayList<String>(), world, x, y, z, yaw, pitch, "", "medium", "open");
		ticket.insert();
		
		sender.sendMessage(Text.of(TextColors.GRAY, "Ticket ", TextColors.YELLOW, "#", id, TextColors.GRAY, " has been created!"));
		
		ServerUtils.broadcast("nexus.ticket.notify", Text.of(TextColors.GOLD, sender.getName(), TextColors.GRAY, " has submitted ticket ", TextColors.YELLOW, "#", id));
		
	}

}
