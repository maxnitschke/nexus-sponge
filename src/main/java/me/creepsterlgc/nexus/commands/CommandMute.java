package main.java.me.creepsterlgc.nexus.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusMute;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.files.FileConfig;
import main.java.me.creepsterlgc.nexus.utils.CommandUtils;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TimeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandMute implements CommandCallable {

	public Game game;

	public CommandMute(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.mute")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 3) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/mute <player> <time> <unit> [reason]")); return CommandResult.success(); }

		NexusPlayer player = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(NexusDatabase.getBan(player.getUUID()) != null) {
			sender.sendMessage(Text.builder("Player is already banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		double duration = 0;

		try { duration = Double.parseDouble(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Text.builder("<time> has to be a number!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		duration = TimeUtils.toMilliseconds(duration, args[2].toLowerCase());

		if(duration == 0) {
			sender.sendMessage(Text.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
			return CommandResult.success();
		}

		int limit = FileConfig.LIMITS_MAX_MUTE_TIME_IN_SECONDS();
		limit *= 1000;

		if(!PermissionsUtils.has(sender, "nexus.mute-unlimited") && duration > limit) {
			sender.sendMessage(Text.of(TextColors.RED, "You can only mute for a maximum time of ", limit /= 1000, " seconds!"));
			return CommandResult.success();
		}

		duration += System.currentTimeMillis();

		String reason = "You got muted!"; if(args.length > 3) reason = CommandUtils.combineArgs(3, args);;

		NexusMute mute = new NexusMute(player.getUUID(), duration, reason);
		mute.insert();

		sender.sendMessage(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " has been muted for ", TextColors.GOLD, args[1], " ", args[2].toLowerCase()));
		sender.sendMessage(Text.of(TextColors.GOLD, "Reason: ", TextColors.GRAY, reason));

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
