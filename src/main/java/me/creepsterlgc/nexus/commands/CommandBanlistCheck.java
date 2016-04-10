package main.java.me.creepsterlgc.nexus.commands;

import main.java.me.creepsterlgc.nexus.customized.NexusBan;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TimeUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandBanlistCheck {

	public CommandBanlistCheck(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.banlist.check")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/banlist check <name>")); return; }

		String uuid = NexusDatabase.getUUID(args[1].toLowerCase());

		if(uuid == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return;
		}

		NexusBan ban = NexusDatabase.getBan(uuid);

		if(ban == null) {
			sender.sendMessage(Text.of(TextColors.GOLD, args[1].toLowerCase(), TextColors.GRAY, " is not banned."));
			return;
		}

		String time = TimeUtils.toString(ban.getDuration() - System.currentTimeMillis());
		String ago = TimeUtils.toString(System.currentTimeMillis() - ban.getTime());

		sender.sendMessage(Text.of(TextColors.GOLD, args[1].toLowerCase(), TextColors.GRAY, " is banned for another ", TextColors.GOLD, time));
		sender.sendMessage(Text.of(TextColors.GOLD, "Reason: ", TextColors.GRAY, ban.getReason()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Banned by ", TextColors.GOLD, ban.getSender(), TextColors.GRAY, " | ", TextColors.GOLD, ago, TextColors.GRAY, " ago"));

	}

}
