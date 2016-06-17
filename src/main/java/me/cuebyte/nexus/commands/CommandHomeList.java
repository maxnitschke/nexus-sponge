package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusHome;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandHomeList {

	public CommandHomeList(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "nexus.home.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/home list [keyword]")); return; }

		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
		if(p.getHomes().isEmpty()) { sender.sendMessage(Text.builder("There are currently no homes set.").color(TextColors.GOLD).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<NexusHome> homes = new ArrayList<NexusHome>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, NexusHome> e : p.getHomes().entrySet()) {
				if(e.getValue().getName().contains(name)) homes.add(e.getValue());
			}
		}
		else {
			for(Entry<String, NexusHome> e : p.getHomes().entrySet()) {
				homes.add(e.getValue());
			}
		}

		if(homes.isEmpty()) {
			sender.sendMessage(Text.builder("No homes found by using the specified keyword.").color(TextColors.GOLD).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(NexusHome home : homes) list.append(home.getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(homes.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(homes.size()) + " Home found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(homes.size()) + " Home found by using keyword: " + name).color(TextColors.YELLOW).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(homes.size()) + " Homes found:").color(TextColors.YELLOW).build());
			else sender.sendMessage(Text.builder(String.valueOf(homes.size()) + " Homes found by using keyword: " + name).color(TextColors.YELLOW).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.GOLD).build());

	}

}
