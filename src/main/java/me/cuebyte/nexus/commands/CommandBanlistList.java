package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusBan;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandBanlistList {

	public CommandBanlistList(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.banlist.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/banlist list [keyword]")); return; }

		if(NexusDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("The banlist is empty.").color(TextColors.GOLD).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<NexusBan> bans = new ArrayList<NexusBan>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, NexusBan> e : NexusDatabase.getBans().entrySet()) {
				if(NexusDatabase.getPlayer(e.getValue().getUUID()).getName().contains(name)) bans.add(e.getValue());
			}
		}
		else {
			for(Entry<String, NexusBan> e : NexusDatabase.getBans().entrySet()) {
				bans.add(e.getValue());
			}
		}

		if(bans.isEmpty()) {
			sender.sendMessage(Text.builder("No bans found by using the specified keyword.").color(TextColors.GOLD).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(NexusBan ban : bans) list.append(NexusDatabase.getPlayer(ban.getUUID()).getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(bans.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(bans.size()) + " Ban found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(bans.size()) + " Ban found by using keyword: " + name).color(TextColors.YELLOW).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(bans.size()) + " Bans found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(bans.size()) + " Bans found by using keyword: " + name).color(TextColors.YELLOW).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.GOLD).build());

	}

}
