package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketClear {

	public CommandTicketClear(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.clear")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ticket clear")); return; }
	
		int size = NexusDatabase.getTickets().size();
		
		NexusDatabase.queue("DELETE FROM tickets WHERE id != 0");
		NexusDatabase.clearTickets();
		
		if(size == 1) {
			sender.sendMessage(Text.of(TextColors.GOLD, size, TextColors.GRAY," ticket has been removed!"));
		}
		else {
			sender.sendMessage(Text.of(TextColors.GOLD, size, TextColors.GRAY," tickets have been removed!"));
		}
		
		ServerUtils.broadcast("nexus.ticket.notify", Text.of(TextColors.GOLD, sender.getName(), TextColors.GRAY, " has cleared all tickets."));
		
	}

}
