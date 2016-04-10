package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;


public class CommandNick implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PermissionsUtils.has(sender, "nexus.nick")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/nick [player] <name|clear>")); return CommandResult.success(); }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/nick [player] <name|clear>")); return CommandResult.success(); }
		
		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("clear")) {
				
				p.setNick("");
				p.update();
				
				sender.sendMessage(Text.of(TextColors.GRAY, "Your nickname has been cleared."));
				
				return CommandResult.success();
				
			}
			
			if(args[0].contains("&") && !PermissionsUtils.has(sender, "nexus.nick-color")) {
				sender.sendMessage(Text.of(TextColors.RED, "You are not permitted to use color codes!"));
				return CommandResult.success();
			}
			
			p.setNick(args[0]);
			p.update();
			
			sender.sendMessage(Text.of(TextColors.GRAY, "Your nickname has been set to: ", TextUtils.color(args[0])));
			
			return CommandResult.success();
			
		}
		
		if(!PermissionsUtils.has(sender, "nexus.nick-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You can only change your own nick!"));
			return CommandResult.success();
		}
		
		NexusPlayer t = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[0].toLowerCase()));
		if(t == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
		
		if(args[1].equalsIgnoreCase("clear")) {
			
			t.setNick("");
			t.update();
			
			sender.sendMessage(Text.of(TextColors.GRAY, t.getName(), "'s nickname has been cleared."));
			
			return CommandResult.success();
			
		}
		
		if(args[1].contains("&") && !PermissionsUtils.has(sender, "nexus.nick-color")) {
			sender.sendMessage(Text.of(TextColors.RED, "You are not permitted to use color codes!"));
			return CommandResult.success();
		}
		
		t.setNick(args[1]);
		t.update();
		
		sender.sendMessage(Text.of(TextColors.GRAY, t.getName(), "'s nickname has been set to: ", TextUtils.color(args[1])));
		
		return CommandResult.success();
		
	}

	private final Text usage = Text.builder("Usage: /nick [player] <nick|clear>").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /nick [player] <nick|clear>").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Nick Command").color(TextColors.GOLD).build();
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
