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


public class CommandHome implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(args.length > 3) { sender.sendMessage(usage); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { new CommandHomeTeleport(sender, args); return CommandResult.success(); }

		if(args[0].equalsIgnoreCase("set")) { new CommandHomeSet(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("delete")) { new CommandHomeDelete(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandHomeList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("move")) { new CommandHomeMove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Home Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home [player] [name]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home set <name>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home delete <name>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home list [keyword]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home move [name]"));
		}
		else if(args.length < 3) {
			new CommandHomeTeleport(sender, args); return CommandResult.success();
		}
		else {
			sender.sendMessage(Text.of(TextColors.YELLOW, "Home Help"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home [player] [name]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home set <name>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home delete <name>"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home list [keyword]"));
			sender.sendMessage(Text.of(TextColors.GOLD, "/home move [name]"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /home help").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /home help").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Home Command").color(TextColors.GOLD).build();
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
