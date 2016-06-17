package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandNexusVersion {

	public CommandNexusVersion(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.nexus.version")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		sender.sendMessage(Text.of(TextColors.WHITE, "Using Nexus ", TextColors.YELLOW, "v1.8.4a"));

	}

}
