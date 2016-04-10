package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusTicket;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketClose {

	public CommandTicketClose(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.close")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ticket close <id>")); return; }
	
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
		
		if(ticket.getStatus().equalsIgnoreCase("closed")) {
			sender.sendMessage(Text.builder("Ticket is already closed!").color(TextColors.RED).build());
			return;
		}
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.close-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "nexus.ticket.close-assigned")) {
				
			}
			else {
				sender.sendMessage(Text.builder("You do not have permissions to close this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		ticket.setStatus("closed");
		ticket.update();
		
		sender.sendMessage(Text.of(TextColors.GRAY, "Ticket ", TextColors.YELLOW, "#", id, TextColors.GRAY, " has been closed."));
		
		ServerUtils.broadcast("nexus.ticket.notify", Text.of(TextColors.GOLD, sender.getName(), TextColors.GRAY, " has closed ticket ", TextColors.YELLOW, "#", id));
		
	}

}
