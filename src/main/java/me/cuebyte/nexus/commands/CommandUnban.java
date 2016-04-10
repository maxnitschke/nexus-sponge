package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.customized.NexusBan;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandUnban implements CommandCallable {

	public Game game;

	public CommandUnban(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.unban")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(usage); return CommandResult.success(); }
		if(args.length != 1) { sender.sendMessage(usage); return CommandResult.success(); }

		NexusPlayer player = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		NexusBan ban = NexusDatabase.getBan(player.getUUID());

		if(ban == null) {
			sender.sendMessage(Text.builder("Player is not banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		ban.delete();
		NexusDatabase.removeBan(player.getUUID());

		Text t1 = Text.builder(player.getName()).color(TextColors.GOLD).build();
		Text t2 = Text.builder(" has been unbanned by ").color(TextColors.GRAY).build();
		Text t3 = Text.builder(sender.getName()).color(TextColors.GOLD).build();

		Text total = Text.builder().append(t1).append(t2).append(t3).build();

		game.getServer().getBroadcastChannel().send(total);

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /unban <player>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /unban <player>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Unban Command").color(TextColors.GOLD).build();
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
