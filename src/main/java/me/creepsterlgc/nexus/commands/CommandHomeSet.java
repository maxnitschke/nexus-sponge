package main.java.me.creepsterlgc.nexus.commands;

import java.util.HashMap;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusHome;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandHomeSet {

	public CommandHomeSet(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.builder("Usage: /home set [name]").color(TextColors.GOLD).build()); return; }

		Player player = (Player)sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

		String name = "default"; if(args.length == 2) name = args[1].toLowerCase();

		HashMap<String, NexusHome> homes = p.getHomes();
		if(homes.containsKey(name)) {
			sender.sendMessage(Text.builder("Home does already exist!").color(TextColors.RED).build());
			return;
		}

		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PermissionsUtils.has(player, "nexus.home.set." + i)) possible = i;
		}

		if(!PermissionsUtils.has(player, "nexus.home.set-unlimited") && possible <= homes.size()) {
			if(possible == 1) sender.sendMessage(Text.builder("You are only allowed to own " + possible + " home!").color(TextColors.RED).build());
			else sender.sendMessage(Text.builder("You are only allowed to own " + possible + " homes!").color(TextColors.RED).build());
			return;
		}

		String world = player.getWorld().getName();

		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();

		NexusHome home = new NexusHome(p.getUUID(), name, world, x, y, z, yaw, pitch);
		home.insert();

		p.setHome(name, home);

		sender.sendMessage(Text.of(TextColors.GRAY, "Home ", TextColors.GOLD, name, TextColors.GRAY, " has been set."));
		if(!PermissionsUtils.has(player, "nexus.home.set-unlimited")) sender.sendMessage(Text.of(TextColors.GRAY, "You currently own ", TextColors.YELLOW, homes.size(), TextColors.GRAY, " / ", TextColors.YELLOW, possible, TextColors.GRAY, " possible homes."));
		else sender.sendMessage(Text.of(TextColors.GRAY, "You currently own ", TextColors.YELLOW, homes.size(), TextColors.GRAY, " / ", TextColors.YELLOW, "oo", TextColors.GRAY, " possible homes."));

	}

}
