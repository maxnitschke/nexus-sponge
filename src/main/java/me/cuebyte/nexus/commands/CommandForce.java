package main.java.me.cuebyte.nexus.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.utils.CommandUtils;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandForce implements CommandCallable {

	public Game game;

	public CommandForce(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.force")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/force <player> <command>")); return CommandResult.success(); }

		Player player = ServerUtils.getPlayer(args[0]);
		if(player == null) {
			sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		if(PermissionsUtils.has(player, "nexus.force.except") && !PermissionsUtils.has(sender, "nexus.force.override")) {
			sender.sendMessage(Text.builder("You cannot force this player!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		String command = CommandUtils.combineArgs(1, args);

		game.getCommandManager().process(player.getCommandSource().get(), command);
		sender.sendMessage(Text.of(TextColors.GRAY, "Forcing ", TextColors.GOLD, player.getName(), TextColors.GRAY, " to enter command: ", TextColors.GOLD, command));

		return CommandResult.success();

	}

	 @Override
	public Text getUsage(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getHelp(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 @Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 @Override
	public boolean testPermission(CommandSource sender) { return false; }

}
