package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandTPA implements CommandCallable {

	public Game game;

	public CommandTPA(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "nexus.tpa")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tpa <player>")); return CommandResult.success(); }
		if(args.length < 1 || args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/tpa <player>")); return CommandResult.success(); }

		Player s = (Player) sender;
		String uuid = s.getUniqueId().toString();

		Player player = ServerUtils.getPlayer(args[0]);

		if(player == null) {
			sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}

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
			else {
				sender.sendMessage(Text.builder("You already requested a teleport from that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}
		}

		duration = 0;

		if(tpahere.containsKey(uuid)) duration = tpahere.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpahere.remove(uuid);
				p.setTPAHere(tpahere);
			}
			else {
				sender.sendMessage(Text.builder("You already requested a teleport from that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}
		}

		duration = System.currentTimeMillis() + 30 * 1000;

		tpa.put(uuid, duration);
		p.setTPA(tpa);

		sender.sendMessage(Text.of(TextColors.GRAY, "Teleport request has been sent to ", TextColors.GOLD, player.getName()));
		player.sendMessage(Text.of(TextColors.GOLD, sender.getName(), TextColors.GRAY, " requested to teleport to you."));
		player.sendMessage(Text.of(TextColors.GRAY, "Type ", TextColors.GOLD, "/tpaccept ", sender.getName(), TextColors.GRAY, " or", TextColors.GOLD, " /tpdeny ", sender.getName()));
		player.sendMessage(Text.of(TextColors.GRAY, "to accept/decline the request."));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /tpa <player>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /tpa <player>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | TPA Command").color(TextColors.GOLD).build();
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
