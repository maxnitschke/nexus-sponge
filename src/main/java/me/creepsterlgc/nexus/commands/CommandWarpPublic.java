package main.java.me.creepsterlgc.nexus.commands;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusWarp;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpPublic {

	public CommandWarpPublic(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.warp.public")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp public <name>")); return; }

		String name = args[1].toLowerCase();
		NexusWarp warp = NexusDatabase.getWarp(name);

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "nexus.warp.public-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		warp.setPrivate("no");
		warp.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Warp ", TextColors.GOLD, name, TextColors.GRAY, " is now public."));

	}

}
