package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandSearchitem implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.searchitem")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/searchitem <name>")); return CommandResult.success(); }

		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/searchitem <name>")); return CommandResult.success(); }

		String name = args[0].toLowerCase();

		StringBuilder sb = new StringBuilder();


		for(ItemType t : Controller.getGame().getRegistry().getAllOf(ItemType.class)) {
			if(!t.getName().contains(name)) continue;
			sb.append(t.getName() + ", ");
		}

		if(sb.toString().contains(", ")) {
			for(int i = 1; i <= 2; i++) sb.deleteCharAt(sb.length() - 1);
			sender.sendMessage(Text.of(TextColors.GOLD, "Found: ", TextColors.GRAY, sb.toString()));
		}
		else {
			sender.sendMessage(Text.of(TextColors.RED, "No items found!"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /searchitem <name>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /searchitem <name>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Searchitem Command").color(TextColors.GOLD).build();
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
