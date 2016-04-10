package main.java.me.creepsterlgc.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.Controller;
import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;


public class CommandRealname implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "nexus.realname")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/realname <player>")); return CommandResult.success(); }
		
		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/realname <player>")); return CommandResult.success(); }
		
		String s = args[0].toLowerCase();
		
		boolean found = false;
		
		for(Player player : Controller.getServer().getOnlinePlayers())	{
			NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
			if(p.getNick().equalsIgnoreCase("")) continue;
			
			String f = p.getNick();
			f = f
			.replaceAll("&a", "")
			.replaceAll("&b", "")
			.replaceAll("&c", "")
			.replaceAll("&d", "")
			.replaceAll("&e", "")
			.replaceAll("&f", "")
			.replaceAll("&0", "")
			.replaceAll("&1", "")
			.replaceAll("&2", "")
			.replaceAll("&3", "")
			.replaceAll("&4", "")
			.replaceAll("&5", "")
			.replaceAll("&6", "")
			.replaceAll("&7", "")
			.replaceAll("&8", "")
			.replaceAll("&9", "")
			.replaceAll("&l", "")
			.replaceAll("&o", "")
			.replaceAll("&m", "")
			.replaceAll("&n", "")
			.replaceAll("&k", "")
			.toLowerCase();
			
			if(s.equalsIgnoreCase(f)) {
				sender.sendMessage(Text.of(TextColors.GOLD, TextUtils.color(p.getNick()), TextColors.GRAY, " is ", player.getName()));
				found = true;
			}
			
		}
		
		if(found == false) sender.sendMessage(Text.of(TextColors.RED, "No match found!"));
		
		return CommandResult.success();
		
	}

	private final Text usage = Text.builder("Usage: /realname <player>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /realname <player>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Realname Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
