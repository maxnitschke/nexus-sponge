package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusWarp;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpCreate {

	public CommandWarpCreate(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp create <name>")); return; }

		Player player = (Player)sender;

		String name = args[1].toLowerCase();

		if(NexusDatabase.getWarp(name) != null) { sender.sendMessage(Text.builder("Warp already exists!").color(TextColors.RED).build()); return; }

		int warps = 0;
		for(Entry<String, NexusWarp> e : NexusDatabase.getWarps().entrySet()) {
			NexusWarp warp = e.getValue();
			if(!warp.getOwner().equalsIgnoreCase(player.getUniqueId().toString())) continue;
			warps += 1;
		}

		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PermissionsUtils.has(player, "nexus.warp.create." + i)) possible = i;
		}

		if(!PermissionsUtils.has(player, "nexus.warp.create-unlimited") && possible <= warps) {
			if(possible == 1) sender.sendMessage(Text.builder("You are only allowed to own " + possible + " warp!").color(TextColors.RED).build());
			else sender.sendMessage(Text.builder("You are only allowed to own " + possible + " warps!").color(TextColors.RED).build());
			return;
		}

		String world = player.getWorld().getName();

		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		String owner = player.getUniqueId().toString();
		List<String> invited = new ArrayList<String>();
		String priv = "no";
		String message = "";

		NexusWarp warp = new NexusWarp(name, world, x, y, z, yaw, pitch, owner, invited, priv, message);
		warp.insert();


		sender.sendMessage(Text.of(TextColors.GRAY, "Warp ", TextColors.GOLD, name, TextColors.GRAY, " has been created."));
		if(!PermissionsUtils.has(player, "nexus.warp.create-unlimited")) sender.sendMessage(Text.of(TextColors.GRAY, "You currently own ", TextColors.YELLOW, warps + 1, TextColors.GRAY, " / ", TextColors.YELLOW, possible, TextColors.GRAY, " possible warps."));
		else sender.sendMessage(Text.of(TextColors.GRAY, "You currently own ", TextColors.YELLOW, warps + 1, TextColors.GRAY, " / ", TextColors.YELLOW, "oo", TextColors.GRAY, " possible warps."));
	}

}
