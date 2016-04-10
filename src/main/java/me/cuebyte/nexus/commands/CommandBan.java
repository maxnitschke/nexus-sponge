package main.java.me.cuebyte.nexus.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusBan;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.CommandUtils;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandBan implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.ban")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/ban <player> <reason>")); return CommandResult.success(); }

		NexusPlayer player = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(NexusDatabase.getBan(player.getUUID()) != null) {
			sender.sendMessage(Text.builder("Player is already banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		double duration = 0;

		String reason = CommandUtils.combineArgs(1, args);

		NexusBan ban = new NexusBan(player.getUUID(), sender.getName().toLowerCase(), reason, System.currentTimeMillis(), duration);
		ban.insert();

		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player p = Controller.getServer().getPlayer(player.getName()).get();
			p.kick(Text.of(TextColors.RED, "Banned: ", TextColors.GRAY, reason));
			Controller.broadcast(Text.of(TextColors.GOLD, p.getName(), TextColors.GRAY, " has been banned by ", TextColors.GOLD, sender.getName()));
			Controller.broadcast(Text.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
			return CommandResult.success();
		}

		Controller.broadcast(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " has been banned by ", TextColors.GOLD, sender.getName()));
		Controller.broadcast(Text.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));

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
