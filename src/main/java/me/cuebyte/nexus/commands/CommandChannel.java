package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;


public class CommandChannel implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Channel Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/c <channel> <message>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/c join <channel>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/c leave <channel>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/c info <channel>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/c list"));
			return CommandResult.success();
		}
		
		if(args[0].equalsIgnoreCase("join")) { new CommandChannelJoin(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("leave")) { new CommandChannelLeave(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("info")) { new CommandChannelInfo(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandChannelList(sender, args); return CommandResult.success(); }
		else {
			new CommandChannelSpeak(sender, args); return CommandResult.success();
		}
		
	}

	private final Text usage = Text.builder("Usage: /channel help").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /channel help").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Channel Command").color(TextColors.GOLD).build();
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
