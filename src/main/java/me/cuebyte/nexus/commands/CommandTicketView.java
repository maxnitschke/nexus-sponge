package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusTicket;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketView {

	public CommandTicketView(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.view")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ticket view <id>")); return; }
	
		Player player = (Player) sender;
		
		int id;
		try { id = Integer.parseInt(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Text.builder("<id> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		NexusTicket ticket = NexusDatabase.getTicket(id);
		
		if(ticket == null) {
			sender.sendMessage(Text.builder("Ticket with that ID does not exist!").color(TextColors.RED).build());
			return;
		}
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.view-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "nexus.ticket.view-assigned")) {
				
			}
			else {
				sender.sendMessage(Text.builder("You do not have permissions to view this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		Text p = Text.of(TextColors.DARK_GREEN, "Low");
		if(ticket.getPriority().equalsIgnoreCase("medium")) p = Text.of(TextColors.GOLD, "Medium");
		else if(ticket.getPriority().equalsIgnoreCase("high")) p = Text.of(TextColors.RED, "High");
		
		sender.sendMessage(Text.of(TextColors.YELLOW, "Information for Ticket ", TextColors.YELLOW, "#", id));
		sender.sendMessage(Text.of(TextColors.GRAY, "By: ", TextColors.GOLD, NexusDatabase.getPlayer(ticket.getUUID()).getName(), TextColors.GRAY, " | ", TextColors.GRAY, TimeUtils.toString(System.currentTimeMillis() - ticket.getTime()), TextColors.GOLD, " ago", TextColors.GRAY, " | Priority: ", p));
		String assigned = "- no -"; if(!ticket.getAssigned().equalsIgnoreCase("")) assigned = NexusDatabase.getPlayer(ticket.getAssigned()).getName();
		sender.sendMessage(Text.of(TextColors.GRAY, "Assigned: ", TextColors.GOLD, assigned, TextColors.GRAY, " | Status: ", TextColors.GOLD, ticket.getStatus()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Location: ", TextColors.GOLD, "world: ", ticket.getWorld(), " x:", Math.round(ticket.getX()), " y:", Math.round(ticket.getY()), " z:", Math.round(ticket.getZ())));
		sender.sendMessage(Text.of(TextColors.GRAY, "Message: ", TextColors.WHITE, ticket.getMessage()));
		if(ticket.getComments().isEmpty()) { sender.sendMessage(Text.of(TextColors.YELLOW, "Comments: ", TextColors.GRAY, "- none -")); }
		else {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Comments:"));
			for(String comment : ticket.getComments()) sender.sendMessage(Text.of(TextColors.YELLOW, "- ", TextColors.WHITE, comment));
		}
		
	}

}
