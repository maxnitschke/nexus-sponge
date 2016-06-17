package main.java.me.cuebyte.nexus.commands;

import java.util.Map.Entry;

import main.java.me.cuebyte.nexus.customized.NexusChannel;
import main.java.me.cuebyte.nexus.customized.NexusChannels;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandChannelList {

	public CommandChannelList(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "nexus.channel.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/channel list")); return; }

		sender.sendMessage(Text.of(TextColors.GRAY, "Found ", TextColors.GOLD, NexusChannels.all().size(), TextColors.GRAY, " channels:"));
		for(Entry<String, NexusChannel> e : NexusChannels.all().entrySet()) {
			sender.sendMessage(Text.of(TextColors.GRAY, "- ", TextColors.WHITE, TextUtils.color(e.getValue().getName()), TextColors.GRAY, " (", e.getKey(), ")"));
		}
		
	}

}
