package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusHome;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandHomeTeleport {

	public CommandHomeTeleport(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.home.teleport")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/home [name]")); return; }

		Player player = (Player)sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

		String name = "default"; if(!args[0].equalsIgnoreCase("")) name = args[0].toLowerCase();
		NexusHome home = p.getHome(name);

		if(home == null) { sender.sendMessage(Text.builder("Home does not exist!").color(TextColors.RED).build()); return; }

		Location<World> loc = new Location<World>(Controller.getServer().getWorld(home.getWorld()).get(), home.getX(), home.getY(), home.getZ());
		player.setLocation(loc);

		sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to home: ", TextColors.GOLD, name));

	}

}
