package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandVanish implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.vanish")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/vanish [player]")); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) {

			if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

			Player p = (Player) sender;
			boolean b = p.get(Keys.INVISIBLE).orElse(false);
			
			if(!b) {
				p.offer(Keys.INVISIBLE, true);
				sender.sendMessage(Text.of(TextColors.GOLD, "You ", TextColors.GRAY, "are now invisible."));
			}
			else {
				p.offer(Keys.INVISIBLE, false);
				sender.sendMessage(Text.of(TextColors.GOLD, "You ", TextColors.GRAY, "are no longer invisible."));
			}
			
		}
		else if(args.length == 1) {

			if(!PermissionsUtils.has(sender, "nexus.vanish-others")) {
				sender.sendMessage(Text.builder("You do not have permissions to vanish other players!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			Player p = (Player) sender;
			boolean b = p.get(Keys.INVISIBLE).orElse(false);
			
			if(!b) {
				p.offer(Keys.INVISIBLE, true);
				p.sendMessage(Text.of(TextColors.GRAY, "Invisibility enabled by ", TextColors.GOLD, sender.getName()));
				sender.sendMessage(Text.of(TextColors.GOLD, p.getName(), TextColors.GRAY, " is now invisible."));
			}
			else {
				p.offer(Keys.INVISIBLE, false);
				p.sendMessage(Text.of(TextColors.GRAY, "Invisibility disabled by ", TextColors.GOLD, sender.getName()));
				sender.sendMessage(Text.of(TextColors.GOLD, p.getName(), TextColors.GRAY, " is no longer invisible."));
			}

		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /vanish [player]").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /vanish [player]").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Vanish Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";

	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource arg0, String arg1,	@Nullable Location<World> arg2) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
