package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusWarp;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.SerializeUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandWarpInfo {

	public CommandWarpInfo(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.warp.info")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp info <name>")); return; }

		String name = args[1].toLowerCase();
		NexusWarp warp = NexusDatabase.getWarp(name);

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "nexus.warp.info-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		String invited = SerializeUtils.list(warp.getInvited());
		invited = invited.replaceAll(",", ", ");
		if(invited.equalsIgnoreCase("")) invited = "- none -";

		double x = warp.getX(); x *= 100; x = Math.round(x); x /= 100;
		double y = warp.getY(); y *= 100; y = Math.round(y); y /= 100;
		double z = warp.getZ(); z *= 100; z = Math.round(z); z /= 100;

		sender.sendMessage(Text.of(TextColors.GRAY, "Information on Warp: ", TextColors.YELLOW, warp.getName()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Owner: ", TextColors.GOLD, NexusDatabase.getPlayer(warp.getOwner()).getName()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Location: ", TextColors.GOLD, warp.getWorld(), " | x:", x, " y:", y, " z:", z));
		sender.sendMessage(Text.of(TextColors.GRAY, "Private: ", TextColors.GOLD, warp.getPrivate()));
		if(invited.equalsIgnoreCase("- none -")) sender.sendMessage(Text.of(TextColors.GRAY, "Invited: ", TextColors.GOLD, invited));
		else sender.sendMessage(Text.of(TextColors.GRAY, "Invited: ", TextColors.GOLD, "(", warp.getInvited().size(), ") ", invited));

	}

}
