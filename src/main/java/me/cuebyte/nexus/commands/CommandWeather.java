package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandWeather implements CommandCallable {

	private Game game;

	public CommandWeather(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(TextUtils.usage("Usage: /weather <sun|rain|storm> [world]")); return CommandResult.success(); }

		if(args[0].equalsIgnoreCase("sun")) { new CommandWeatherSun(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("rain")) { new CommandWeatherRain(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("storm")) { new CommandWeatherStorm(sender, args, game); return CommandResult.success(); }
		else {
			sender.sendMessage(TextUtils.usage("Usage: /weather <sun|rain|storm> [world]"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /weather <sun|rain|storm> [world]").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /weather <sun|rain|storm> [world]").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Time Command").color(TextColors.GOLD).build();
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
