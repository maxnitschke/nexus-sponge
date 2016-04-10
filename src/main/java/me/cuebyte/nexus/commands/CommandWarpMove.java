package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusWarp;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpMove {

	public CommandWarpMove(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.warp.move")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp move <name>")); return; }

		Player player = (Player) sender;

		String name = args[1].toLowerCase();
		NexusWarp warp = NexusDatabase.getWarp(name);

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "nexus.warp.move-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		warp.setWorld(player.getWorld().getName());
		warp.setX(player.getLocation().getX());
		warp.setY(player.getLocation().getY());
		warp.setZ(player.getLocation().getZ());
		warp.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Warp ", TextColors.GOLD, name, TextColors.GRAY, " has been moved to your location."));

	}

}
