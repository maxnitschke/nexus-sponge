package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.files.FileMessages;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandFakejoin implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.fakejoin")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/fakejoin [name]")); return CommandResult.success(); }

		String name = sender.getName();
		if(!arguments.equalsIgnoreCase("")) name = arguments;

		Controller.broadcast(TextUtils.color(FileMessages.EVENTS_JOIN_MESSAGE().replaceAll("%player", name)));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /fakejoin [name]").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /fakejoin [name]").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Fakejoin Command").color(TextColors.GOLD).build();
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
