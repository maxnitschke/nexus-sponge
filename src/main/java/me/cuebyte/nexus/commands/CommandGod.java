package main.java.me.cuebyte.nexus.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandGod implements CommandCallable {

	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.god")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/god [player]")); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) {
			
			if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
			
			Player player = (Player) sender;
			NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
			
			if(p.getGod() != 1) {
				
				p.setGod(1);
				p.update();
				
				player.sendMessage(Text.of(TextColors.GRAY, "Godmode: ", TextColors.GOLD, "enabled"));
				return CommandResult.success();
				
			}
			
			p.setGod(0);
			p.update();
			
			player.sendMessage(Text.of(TextColors.GRAY, "Godmode: ", TextColors.GOLD, "disabled"));
			return CommandResult.success();
			
		}

		if(!PermissionsUtils.has(sender, "nexus.god-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You can only change your own godmode!"));
			return CommandResult.success();
		}
			
		Player player = ServerUtils.getPlayer(args[0].toLowerCase());
		if(player == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
			
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
			
		if(p.getGod() != 1) {
				
			p.setGod(1);
			p.update();
				
			sender.sendMessage(Text.of(TextColors.GRAY, "Godmode ", TextColors.GOLD, "enabled", TextColors.GRAY, " for ", TextColors.GOLD, player.getName()));
			player.sendMessage(Text.of(TextColors.GRAY, "Godmode ", TextColors.GOLD, "enabled", TextColors.GRAY, " by ", TextColors.GOLD, sender.getName()));
			return CommandResult.success();
		}
			
		p.setGod(0);
		p.update();
			
		sender.sendMessage(Text.of(TextColors.GRAY, "Godmode ", TextColors.GOLD, "disabled", TextColors.GRAY, " for ", TextColors.GOLD, player.getName()));
		player.sendMessage(Text.of(TextColors.GRAY, "Godmode ", TextColors.GOLD, "disabled", TextColors.GRAY, " by ", TextColors.GOLD, sender.getName()));
		return CommandResult.success();

	}

	 public Text getUsage(CommandSource sender) { return null; }
	 public Optional<Text> getHelp(CommandSource sender) { return null; }
	 public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 public boolean testPermission(CommandSource sender) { return false; }

}
