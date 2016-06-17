package main.java.me.cuebyte.nexus.commands;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandSpy implements CommandCallable {

	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.spy")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/spy")); return CommandResult.success(); }

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
			
		Player player = (Player)sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
		if(!p.getSpy()) {
			p.setSpy(true);
			sender.sendMessage(Text.of(TextColors.GRAY, "Spy: ", TextColors.GOLD, "enabled"));
		}
		else {
			p.setSpy(false);
			sender.sendMessage(Text.of(TextColors.GRAY, "Spy: ", TextColors.GOLD, "disabled"));
		}
		
		return CommandResult.success();

	}

	 public Text getUsage(CommandSource sender) { return null; }
	 public Optional<Text> getHelp(CommandSource sender) { return null; }
	 public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 public List<String> getSuggestions(CommandSource arg0, String arg1,	@Nullable Location<World> arg2) throws CommandException { return null; }
	 public boolean testPermission(CommandSource sender) { return false; }

}
