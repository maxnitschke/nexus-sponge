package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.customized.NexusTicket;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;


public class CommandTicketList {

	public CommandTicketList(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "nexus.ticket.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ticket list [player]")); return; }
	
		String uuid = "";
		
		if(args.length == 2) {
			
			uuid = NexusDatabase.getUUID(args[1]);
			
			if(uuid == null || uuid.equalsIgnoreCase("")) {
				sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
				return;
			}
			
		}
				
		HashMap<Integer, Text> tickets = new HashMap<Integer, Text>();
		HashMap<Integer, List<Text>> pages = new HashMap<Integer, List<Text>>();
		
		int counter = 1;
		for(Entry<Integer, NexusTicket> e : NexusDatabase.getTickets().entrySet()) {
			NexusTicket ticket = e.getValue();
			if(!uuid.equalsIgnoreCase("")) {
				if(!ticket.getUUID().equalsIgnoreCase(uuid)) continue;
			}
			else {
				if(!ticket.getStatus().equalsIgnoreCase("open")) continue;
			}
			Text p = Text.of(TextColors.YELLOW, "Low");
			if(ticket.getPriority().equalsIgnoreCase("medium")) p = Text.of(TextColors.GOLD, "Medium");
			else if(ticket.getPriority().equalsIgnoreCase("high")) p = Text.of(TextColors.RED, "High");
			Text s = Text.of(TextColors.DARK_GREEN, "Open");
			if(ticket.getStatus().equalsIgnoreCase("closed")) s = Text.of(TextColors.DARK_RED, "Closed");
			Text message = Text.of(TextColors.YELLOW, "#", ticket.getID(), TextColors.GRAY, " | ", p, TextColors.GRAY, " | ", s, TextColors.GRAY, " | ", TextColors.WHITE, NexusDatabase.getPlayer(ticket.getUUID()).getName(), TextColors.GRAY, " | ", TextColors.WHITE, ticket.getMessage());
			Text hover = Text.of(TextColors.GOLD, "Click", TextColors.GRAY, " to view information on Ticket ", TextColors.YELLOW, "#", ticket.getID());
			String command = "/ticket view " + ticket.getID();
			tickets.put(counter, Text.builder().append(message).onHover(TextActions.showText(hover)).onClick(TextActions.runCommand(command)).build());
			counter += 1;
		}
	
		if(counter == 1) {
			sender.sendMessage(Text.builder("No tickets found!").color(TextColors.RED).build());
			return;
		}
		
		counter = 0;
		while(!tickets.isEmpty()) {
			List<Text> c = new ArrayList<Text>();
			for(int i = 1; i <= 6; i++) {
				if(!tickets.containsKey(i + counter * 6)) break;
				c.add(tickets.get(i + counter * 6));
				tickets.remove(i  + counter * 6);
			}
			counter += 1;
			pages.put(counter, c);
		}
		
		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
		p.setPages(pages);
		
		p.setPageTitle(Text.of(TextColors.YELLOW, "Tickets"));
		p.setPageHeader(Text.of(TextColors.YELLOW, "ID", TextColors.GRAY, " | ", TextColors.WHITE, "Priority", TextColors.GRAY, " | ", TextColors.WHITE, "Status", TextColors.GRAY, " | ", TextColors.WHITE, "Issued by", TextColors.GRAY, " | ", TextColors.WHITE, "Message"));
		
		Controller.getGame().getCommandManager().process(sender.getCommandSource().get(), "page 1");
		
	}

}
