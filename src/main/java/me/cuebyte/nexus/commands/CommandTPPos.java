package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;

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


public class CommandTPPos implements CommandCallable {

	public Game game;

	public CommandTPPos(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.tppos")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tppos [player] <x> <y> <z>")); return CommandResult.success(); }

		if(args.length < 3 || args.length > 4) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tppos [player] <x> <y> <z>")); return CommandResult.success(); }

		if(args.length == 3) {

			if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

			Player player = (Player) sender;

			double x;
			double y;
			double z;

			try { x = Double.parseDouble(args[0]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<x> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			try { y = Double.parseDouble(args[1]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<y> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			try { z = Double.parseDouble(args[2]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<z> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			Location<World> loc = new Location<World>(player.getWorld(), x, y, z);
			player.setLocation(loc);

			player.sendMessage(Text.of(TextColors.GRAY, "Teleported to ", TextColors.GOLD, "x:", x, " y:", y, " z:", z));

		}
		else if(args.length == 4) {

			if(!PermissionsUtils.has(sender, "nexus.tppos-others")) {
				sender.sendMessage(Text.builder("You do not have permissions to teleport others!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			Player player = ServerUtils.getPlayer(args[0].toLowerCase());
			if(player == null) {
				sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			double x;
			double y;
			double z;

			try { x = Double.parseDouble(args[1]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<x> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			try { y = Double.parseDouble(args[2]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<y> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			try { z = Double.parseDouble(args[3]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("<z> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			Location<World> loc = new Location<World>(player.getWorld(), x, y, z);
			player.setLocation(loc);

			sender.sendMessage(Text.of(TextColors.GRAY, "Teleported ", TextColors.GOLD, player.getName(), TextColors.GRAY, " to ", TextColors.GOLD, "x:", x, " y:", y, " z:", z));

		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /tppos [player] <x> <y> <z>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /tppos [player] <x> <y> <z>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | TPPos Command").color(TextColors.GOLD).build();
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
