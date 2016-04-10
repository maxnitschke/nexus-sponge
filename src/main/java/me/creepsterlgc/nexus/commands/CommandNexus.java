package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandNexus implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(args.length > 4) { sender.sendMessage(usage); return CommandResult.success(); }

		if(args[0].equalsIgnoreCase("database")) { new CommandNexusDatabase(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("version")) { new CommandNexusVersion(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Nexus Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/nexus database"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/nexus version"));
		}
		else {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Nexus Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/nexus database"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/nexus version"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /nexus help").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /nexus help").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Nexus Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";

	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
