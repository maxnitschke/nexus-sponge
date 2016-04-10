package main.java.me.creepsterlgc.nexus.commands;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusSpawn;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandSpawnRemove {

	public CommandSpawnRemove(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.spawn.remove")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/spawn remove [name]")); return; }

		String name = "default";
		if(args.length == 2) name = args[1].toLowerCase();

		NexusSpawn spawn = NexusDatabase.getSpawn(name);

		if(spawn == null) { sender.sendMessage(Text.builder("Spawn does not exist!").color(TextColors.RED).build()); return; }

		spawn.delete();

		sender.sendMessage(Text.of(TextColors.GRAY, "Spawn ", TextColors.GOLD, spawn.getName(), TextColors.GRAY, " has been removed."));

	}

}
