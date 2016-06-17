package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
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

public class CommandTPAccept implements CommandCallable {

	public Game game;

	public CommandTPAccept(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "nexus.tpaccept")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tpaccept <player>")); return CommandResult.success(); }

		if(args.length < 1 || args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tpaccept <player>")); return CommandResult.success(); }

		Player player = (Player) sender;
		Player target = ServerUtils.getPlayer(args[0]);

		if(target == null) {
			sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		String uuid = target.getUniqueId().toString();

		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		HashMap<String, Double> tpa = p.getTPA();
		HashMap<String, Double> tpahere = p.getTPAHere();

		double duration = 0;
		if(tpa.containsKey(uuid)) duration = tpa.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpa.remove(uuid);
				p.setTPA(tpa);
			}
		}

		duration = 0;

		if(tpahere.containsKey(uuid)) duration = tpahere.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpahere.remove(uuid);
				p.setTPAHere(tpahere);
			}
		}

		if(!tpa.containsKey(uuid) && !tpahere.containsKey(uuid)) {
			sender.sendMessage(Text.builder("There is no pending request from that player!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		sender.sendMessage(Text.of(TextColors.GOLD, target.getName(), "'s ", TextColors.GRAY, "teleport request has been accepted."));

		if(tpa.containsKey(uuid)) {

			target.setLocation(player.getLocation());

			tpa.remove(uuid);
			p.setTPA(tpa);
		}

		if(tpahere.containsKey(uuid)) {

			player.setLocation(target.getLocation());

			tpahere.remove(uuid);
			p.setTPA(tpahere);
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /tpaccept <player>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /tpaccept <player>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | TPAccept Command").color(TextColors.GOLD).build();
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
