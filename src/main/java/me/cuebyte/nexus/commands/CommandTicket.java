package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandTicket implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandTicketCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandTicketList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("view")) { new CommandTicketView(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("tp")) { new CommandTicketTP(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("comment")) { new CommandTicketComment(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("assign")) { new CommandTicketAssign(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("priority")) { new CommandTicketPriority(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("open")) { new CommandTicketOpen(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("close")) { new CommandTicketClose(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("clear")) { new CommandTicketClear(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Ticket Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket create <message>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket view <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket list [player]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket tp <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket comment <id> <message>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket assign <id> <player>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket priority <id> <priority>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket <open|close> <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket clear"));
		}
		else {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Ticket Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket create <message>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket view <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket list [player]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket tp <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket comment <id> <message>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket assign <id> <player>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket priority <id> <priority>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket <open|close> <id>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/ticket clear"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Text.builder("Usage: /ticket help").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /ticket help").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Ticket Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource arg0, String arg1,	@Nullable Location<World> arg2) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
