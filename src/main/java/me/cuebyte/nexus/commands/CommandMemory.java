package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandMemory implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		if(!PermissionsUtils.has(sender, "nexus.memory")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		Runtime runtime = Runtime.getRuntime();

		double memory_max = runtime.maxMemory() / (1024 * 1024);
		double memory_allocated = runtime.totalMemory() / (1024 * 1024);
		double memory_free = runtime.freeMemory() / (1024 * 1024);

		sender.sendMessage(Text.of(TextColors.GRAY, "Memory Usage: ", TextColors.GOLD, memory_allocated, " MB / ", memory_max, " MB ", TextColors.GRAY, "Free: ", TextColors.GOLD, memory_free, " MB"));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /memory").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /memory").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Memory Command").color(TextColors.GOLD).build();
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
