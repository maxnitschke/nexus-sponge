package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.utils.CommandUtils;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
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


public class CommandKick implements CommandCallable {

	public Game game;

	public CommandKick(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.kick")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(TextUtils.usage("/kick <player> [reason]")); return CommandResult.success(); }

		if(!game.getServer().getPlayer(args[0]).isPresent()) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		String reason = "You have been kicked!"; if(args.length > 1) reason = CommandUtils.combineArgs(1, args);

		Player player = game.getServer().getPlayer(args[0]).get();
		player.kick(Text.of(TextColors.RED, reason));

		Controller.broadcast(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " has been kicked by ", TextColors.GOLD, sender.getName()));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /kick <player> [reason]").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /kick <player> [reason]").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Kick Command").color(TextColors.GOLD).build();
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
