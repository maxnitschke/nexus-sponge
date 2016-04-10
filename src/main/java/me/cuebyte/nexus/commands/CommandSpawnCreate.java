package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusSpawn;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandSpawnCreate {

	public CommandSpawnCreate(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.spawn.create")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/spawn create [name]")); return; }

		Player player = (Player)sender;

		String name = "default";
		if(args.length == 2) name = args[1].toLowerCase();

		if(NexusDatabase.getSpawn(name) != null) { sender.sendMessage(Text.builder("Spawn already exists!").color(TextColors.RED).build()); return; }

		String world = player.getWorld().getName();

		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		String message = "";

		NexusSpawn spawn = new NexusSpawn(name, world, x, y, z, yaw, pitch, message);
		spawn.insert();

		sender.sendMessage(Text.of(TextColors.GRAY, "Spawn ", TextColors.GOLD, spawn.getName(), TextColors.GRAY, " has been created."));

	}

}
