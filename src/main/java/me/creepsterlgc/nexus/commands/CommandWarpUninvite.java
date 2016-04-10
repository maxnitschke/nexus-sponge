package main.java.me.creepsterlgc.nexus.commands;

import java.util.List;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusWarp;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpUninvite {

	public CommandWarpUninvite(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.warp.uninvite")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 3 || args.length > 3) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp uninvite <name> <player>")); return; }

		String name = args[1].toLowerCase();
		NexusWarp warp = NexusDatabase.getWarp(name);
		String player = args[2].toLowerCase();

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "nexus.warp.uninvite-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		if(!warp.getInvited().contains(player)) {
			sender.sendMessage(Text.of(TextColors.RED, "This player is not invited!"));
			return;
		}

		List<String> invited = warp.getInvited(); invited.remove(player); warp.setInvited(invited);
		warp.update();

		sender.sendMessage(Text.of(TextColors.GOLD, player, TextColors.GRAY, " is no longer invited to warp ", TextColors.GOLD, warp.getName()));

	}

}
