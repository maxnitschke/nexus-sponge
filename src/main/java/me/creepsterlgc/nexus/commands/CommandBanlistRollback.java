package main.java.me.creepsterlgc.nexus.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.nexus.customized.NexusBan;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TimeUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandBanlistRollback {

	public CommandBanlistRollback(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.banlist.rollback")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 4 || args.length > 4) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/banlist rollback <sender> <time> <unit>")); return; }

		if(NexusDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("The banlist is empty.").color(TextColors.GOLD).build()); return; }

		String name = args[1].toLowerCase();

		double duration = 0;

		try { duration = Double.parseDouble(args[2]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Text.builder("<time> has to be a number!").color(TextColors.RED).build());
			return;
		}

		duration = TimeUtils.toMilliseconds(duration, args[3].toLowerCase());

		if(duration == 0) {
			sender.sendMessage(Text.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
			return;
		}

		double time = System.currentTimeMillis() - duration;

		int counter = 0;
		HashMap<String, NexusBan> bans = NexusDatabase.getBans();

		for(Entry<String, NexusBan> e : bans.entrySet()) {
			NexusBan ban = e.getValue();
			if(ban.getSender().equalsIgnoreCase(name) && ban.getTime() >= time) { ban.delete(); NexusDatabase.removeBan(ban.getUUID()); counter += 1; }
		}

		sender.sendMessage(Text.of(TextColors.GOLD, counter, TextColors.GRAY, " bans have been removed."));

	}

}
