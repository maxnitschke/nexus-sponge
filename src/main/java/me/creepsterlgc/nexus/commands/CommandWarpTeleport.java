package main.java.me.creepsterlgc.nexus.commands;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusWarp;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class CommandWarpTeleport {

	public CommandWarpTeleport(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp <name>")); return; }

		Player player = (Player)sender;

		String name = args[0].toLowerCase();
		NexusWarp warp = NexusDatabase.getWarp(name);

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.warp.teleport." + name)) { sender.sendMessage(TextUtils.permissions()); return; }

		if(PermissionsUtils.has(sender, "nexus.warp.teleport-unlimited")) {
			if(!player.transferToWorld(warp.getWorld(), new Vector3d(warp.getX(), warp.getY(), warp.getZ()))) { sender.sendMessage(Text.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return; }
			sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to warp: ", TextColors.GOLD, name));
			return;
		}

		if(!warp.getOwner().equalsIgnoreCase(sender.getName().toLowerCase())) {

			if(warp.getPrivate().equalsIgnoreCase("yes") && !PermissionsUtils.has(sender, "nexus.warp.teleport-invited")) {
				sender.sendMessage(Text.of(TextColors.RED, "You do not have permissions to teleport to private warps!"));
				return;
			}

			if(warp.getPrivate().equalsIgnoreCase("yes") && !warp.getInvited().contains(sender.getName().toLowerCase())) {
				sender.sendMessage(Text.of(TextColors.RED, "You are not invited to this warp!"));
				return;
			}

		}

		Location<World> loc = new Location<World>(Controller.getServer().getWorld(warp.getWorld()).get(), warp.getX(), warp.getY(), warp.getZ());
		player.setLocation(loc);

		sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to warp: ", TextColors.GOLD, name));

	}

}
