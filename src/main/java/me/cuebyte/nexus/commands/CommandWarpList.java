package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusWarp;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpList {

	public CommandWarpList(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.warp.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/warp list [keyword]")); return; }

		if(NexusDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("There are currently no warps set.").color(TextColors.GOLD).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<NexusWarp> warps = new ArrayList<NexusWarp>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, NexusWarp> e : NexusDatabase.getWarps().entrySet()) {
				if(e.getValue().getName().contains(name)) warps.add(e.getValue());
			}
		}
		else {
			for(Entry<String, NexusWarp> e : NexusDatabase.getWarps().entrySet()) {
				warps.add(e.getValue());
			}
		}

		if(warps.isEmpty()) {
			sender.sendMessage(Text.builder("No warps found by using the specified keyword.").color(TextColors.GOLD).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(NexusWarp warp : warps) list.append(warp.getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(warps.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warp found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warp found by using keyword: " + name).color(TextColors.YELLOW).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warps found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warps found by using keyword: " + name).color(TextColors.YELLOW).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.GOLD).build());

	}

}
