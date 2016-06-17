package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusSpawn;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandSpawnList {

	public CommandSpawnList(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.spawn.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/spawn list [keyword]")); return; }

		if(NexusDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("There are currently no spawns set.").color(TextColors.GOLD).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<NexusSpawn> spawns = new ArrayList<NexusSpawn>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, NexusSpawn> e : NexusDatabase.getSpawns().entrySet()) {
				if(e.getValue().getName().contains(name)) spawns.add(e.getValue());
			}
		}
		else {
			for(Entry<String, NexusSpawn> e : NexusDatabase.getSpawns().entrySet()) {
				spawns.add(e.getValue());
			}
		}

		if(spawns.isEmpty()) {
			sender.sendMessage(Text.builder("No spawns found by using the specified keyword.").color(TextColors.GOLD).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(NexusSpawn spawn : spawns) list.append(spawn.getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(spawns.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawn found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawn found by using keyword: " + name).color(TextColors.YELLOW).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawns found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawns found by using keyword: " + name).color(TextColors.YELLOW).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.GOLD).build());

	}

}
