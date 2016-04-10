package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandMailClear {

	public CommandMailClear(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.mail.clear")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/mail clear")); return; }

		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());

		p.setMails("");
		p.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Your inbox has been cleared."));

	}

}
