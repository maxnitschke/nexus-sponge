package main.java.me.cuebyte.nexus.commands;

import java.util.List;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.CommandUtils;
import main.java.me.cuebyte.nexus.utils.DeserializeUtils;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.SerializeUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandMailSend {

	public CommandMailSend(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "nexus.mail.send")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 3) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/mail send <player> <message>")); return; }

		NexusPlayer player = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[1].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return; }

		String message = CommandUtils.combineArgs(2, args);

		if(message.contains("-;;")) {
			sender.sendMessage(Text.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}

		List<String> mails = DeserializeUtils.messages(player.getMails());
		mails.add(String.valueOf(System.currentTimeMillis()) + ":" + sender.getName() + ":" + message);
		player.setMails(SerializeUtils.messages(mails));
		player.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Mail has been sent to ", TextColors.GOLD, player.getName()));

		for(Player t : Controller.getPlayers()) {
			
			if(sender.getName().equalsIgnoreCase(t.getName())) continue;
			if(player.getName().equalsIgnoreCase(t.getName())) continue;
			
			if(!PermissionsUtils.has(t, "nexus.spy")) continue;
			NexusPlayer s = NexusDatabase.getPlayer(t.getUniqueId().toString());
			if(!s.getSpy()) continue;
			
			t.sendMessage(Text.of(TextColors.YELLOW, "Spy: ", TextColors.WHITE, sender.getName(), " -> ", player.getName(), ": ", TextColors.GRAY, message));
		}
		
	}

}
