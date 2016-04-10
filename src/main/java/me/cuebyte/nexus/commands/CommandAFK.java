package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.files.FileConfig;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandAFK implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "nexus.afk")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

		double time = System.currentTimeMillis();

		if(p.getAFK()) {
			Controller.broadcast(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " is no longer afk."));
			p.setAFK(false);
			p.setLastaction(time);
		}
		else if(!p.getAFK()) {
			Controller.broadcast(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " is now afk."));
			p.setAFK(true);
			p.setLastaction(time - FileConfig.AFK_TIMER_IN_SECONDS() * 1000);
		}

		NexusDatabase.addPlayer(p.getUUID(), p);

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /afk").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /afk").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | AFK Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";

	@Override public Text getUsage(CommandSource sender) { return usage; }
	@Override public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
