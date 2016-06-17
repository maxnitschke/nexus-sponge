package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusChannel;
import main.java.me.cuebyte.nexus.customized.NexusChannels;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandChannelInfo {

	public CommandChannelInfo(CommandSource sender, String[] args) {
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/channel info <channel>")); return; }
	
		String channel = args[1].toLowerCase();
		NexusChannel c = NexusChannels.get(channel);
		
		if(c == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PermissionsUtils.has(sender, "nexus.channel.info." + channel)) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not have permissions to view this channel!"));
			return;
		}
		
		sender.sendMessage(Text.of(TextColors.WHITE, "Channel: ", TextColors.GRAY, TextUtils.color(c.getName())));
		sender.sendMessage(Text.of(TextColors.WHITE, "ID: ", TextColors.GRAY, c.getID()));
		sender.sendMessage(Text.of(TextColors.WHITE, "Prefix: ", TextColors.GRAY, TextUtils.color(c.getPrefix())));
		sender.sendMessage(Text.of(TextColors.WHITE, "Suffix: ", TextColors.GRAY, TextUtils.color(c.getSuffix())));
		sender.sendMessage(Text.of(TextColors.WHITE, "Format: ", TextColors.GRAY, c.getFormat()));
		sender.sendMessage(Text.of(TextColors.WHITE, "Range: ", TextColors.GRAY, c.getRange()));
		
	}

}
