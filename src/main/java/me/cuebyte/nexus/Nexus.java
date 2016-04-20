package main.java.me.cuebyte.nexus;

import java.io.File;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import main.java.me.cuebyte.nexus.api.NexusAPI;
import main.java.me.cuebyte.nexus.commands.CommandAFK;
import main.java.me.cuebyte.nexus.commands.CommandBan;
import main.java.me.cuebyte.nexus.commands.CommandBanlist;
import main.java.me.cuebyte.nexus.commands.CommandBroadcast;
import main.java.me.cuebyte.nexus.commands.CommandButcher;
import main.java.me.cuebyte.nexus.commands.CommandChannel;
import main.java.me.cuebyte.nexus.commands.CommandEnchant;
import main.java.me.cuebyte.nexus.commands.CommandFakejoin;
import main.java.me.cuebyte.nexus.commands.CommandFakeleave;
import main.java.me.cuebyte.nexus.commands.CommandFeed;
import main.java.me.cuebyte.nexus.commands.CommandFly;
import main.java.me.cuebyte.nexus.commands.CommandForce;
import main.java.me.cuebyte.nexus.commands.CommandGamemode;
import main.java.me.cuebyte.nexus.commands.CommandGive;
import main.java.me.cuebyte.nexus.commands.CommandGod;
import main.java.me.cuebyte.nexus.commands.CommandHeal;
import main.java.me.cuebyte.nexus.commands.CommandHome;
import main.java.me.cuebyte.nexus.commands.CommandItem;
import main.java.me.cuebyte.nexus.commands.CommandJump;
import main.java.me.cuebyte.nexus.commands.CommandKick;
import main.java.me.cuebyte.nexus.commands.CommandKill;
import main.java.me.cuebyte.nexus.commands.CommandList;
import main.java.me.cuebyte.nexus.commands.CommandMail;
import main.java.me.cuebyte.nexus.commands.CommandMemory;
import main.java.me.cuebyte.nexus.commands.CommandMessage;
import main.java.me.cuebyte.nexus.commands.CommandMotd;
import main.java.me.cuebyte.nexus.commands.CommandMute;
import main.java.me.cuebyte.nexus.commands.CommandNexus;
import main.java.me.cuebyte.nexus.commands.CommandNick;
import main.java.me.cuebyte.nexus.commands.CommandOnlinetime;
import main.java.me.cuebyte.nexus.commands.CommandPage;
import main.java.me.cuebyte.nexus.commands.CommandPing;
import main.java.me.cuebyte.nexus.commands.CommandPowertool;
import main.java.me.cuebyte.nexus.commands.CommandRealname;
import main.java.me.cuebyte.nexus.commands.CommandReply;
import main.java.me.cuebyte.nexus.commands.CommandRules;
import main.java.me.cuebyte.nexus.commands.CommandSearchitem;
import main.java.me.cuebyte.nexus.commands.CommandSeen;
import main.java.me.cuebyte.nexus.commands.CommandSpawn;
import main.java.me.cuebyte.nexus.commands.CommandSpeed;
import main.java.me.cuebyte.nexus.commands.CommandTP;
import main.java.me.cuebyte.nexus.commands.CommandTPA;
import main.java.me.cuebyte.nexus.commands.CommandTPAHere;
import main.java.me.cuebyte.nexus.commands.CommandTPAccept;
import main.java.me.cuebyte.nexus.commands.CommandTPDeath;
import main.java.me.cuebyte.nexus.commands.CommandTPDeny;
import main.java.me.cuebyte.nexus.commands.CommandTPHere;
import main.java.me.cuebyte.nexus.commands.CommandTPPos;
import main.java.me.cuebyte.nexus.commands.CommandTPSwap;
import main.java.me.cuebyte.nexus.commands.CommandTPWorld;
import main.java.me.cuebyte.nexus.commands.CommandTempban;
import main.java.me.cuebyte.nexus.commands.CommandTicket;
import main.java.me.cuebyte.nexus.commands.CommandTime;
import main.java.me.cuebyte.nexus.commands.CommandUnban;
import main.java.me.cuebyte.nexus.commands.CommandUnmute;
import main.java.me.cuebyte.nexus.commands.CommandVanish;
import main.java.me.cuebyte.nexus.commands.CommandWarp;
import main.java.me.cuebyte.nexus.commands.CommandWeather;
import main.java.me.cuebyte.nexus.commands.CommandWhois;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPortal;
import main.java.me.cuebyte.nexus.events.EventPlayerChat;
import main.java.me.cuebyte.nexus.events.EventPlayerDamage;
import main.java.me.cuebyte.nexus.events.EventPlayerDeath;
import main.java.me.cuebyte.nexus.events.EventPlayerInteractBlock;
import main.java.me.cuebyte.nexus.events.EventPlayerInteractEntity;
import main.java.me.cuebyte.nexus.events.EventPlayerJoin;
import main.java.me.cuebyte.nexus.events.EventPlayerKick;
import main.java.me.cuebyte.nexus.events.EventPlayerLogin;
import main.java.me.cuebyte.nexus.events.EventPlayerMove;
import main.java.me.cuebyte.nexus.events.EventPlayerQuit;
import main.java.me.cuebyte.nexus.events.EventPlayerRespawn;
import main.java.me.cuebyte.nexus.events.EventSignChange;
import main.java.me.cuebyte.nexus.files.FileChat;
import main.java.me.cuebyte.nexus.files.FileCommands;
import main.java.me.cuebyte.nexus.files.FileConfig;
import main.java.me.cuebyte.nexus.files.FileMessages;
import main.java.me.cuebyte.nexus.files.FileMotd;
import main.java.me.cuebyte.nexus.files.FileRules;
import main.java.me.cuebyte.nexus.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "nexus", name = "Nexus", version = "1.7.6a")

public class Nexus {

	@Inject
	private Game game;

	@Inject
	Logger logger;

	public static Nexus nexus;

	public static Nexus getInstance() { return nexus; }
	public Game getGame() { return game; }

    @Listener
    public void onEnable(GameStartingServerEvent event) {

    	File folder = new File("config/nexus");
    	if(!folder.exists()) folder.mkdir();

    	Controller.game = game;
    	ServerUtils.sink = game.getServer().getBroadcastChannel();

    	FileConfig.setup();
    	FileChat.setup();
    	FileCommands.setup();
    	FileMessages.setup();
    	FileMotd.setup();
    	FileRules.setup();

    	NexusDatabase.setup(game);
    	NexusDatabase.load(game);

        if (!game.getServiceManager().provide(NexusAPI.class).isPresent()) {
            try {
                game.getServiceManager().setProvider(this, NexusAPI.class, new NexusAPI());
            } catch (Exception e) {
                logger.warning("Error while registering NexusAPI!");
            }
        }

    	game.getEventManager().registerListeners(this, new EventPlayerLogin());
    	game.getEventManager().registerListeners(this, new EventPlayerChat());
    	game.getEventManager().registerListeners(this, new EventPlayerDamage());
    	game.getEventManager().registerListeners(this, new EventPlayerDeath());
    	game.getEventManager().registerListeners(this, new EventPlayerInteractBlock());
    	game.getEventManager().registerListeners(this, new EventPlayerInteractEntity());
    	game.getEventManager().registerListeners(this, new EventPlayerJoin());
    	game.getEventManager().registerListeners(this, new EventPlayerKick());
    	game.getEventManager().registerListeners(this, new EventPlayerMove());
    	game.getEventManager().registerListeners(this, new EventPlayerRespawn());
    	game.getEventManager().registerListeners(this, new EventPlayerQuit());
    	game.getEventManager().registerListeners(this, new EventSignChange());

    	if(FileCommands.AFK()) game.getCommandManager().register(this, new CommandAFK(), "afk");
    	if(FileCommands.BAN()) game.getCommandManager().register(this, new CommandBan(), "ban");
    	if(FileCommands.BANLIST()) game.getCommandManager().register(this, new CommandBanlist(), "banlist");
    	if(FileCommands.BROADCAST()) game.getCommandManager().register(this, new CommandBroadcast(), "broadcast");
    	if(FileCommands.BUTCHER()) game.getCommandManager().register(this, new CommandButcher(), "butcher");
    	if(FileCommands.CHANNEL()) game.getCommandManager().register(this, new CommandChannel(), "channel", "ch", "c");
    	if(FileCommands.ENCHANT()) game.getCommandManager().register(this, new CommandEnchant(game), "enchant");
    	if(FileCommands.FAKEJOIN()) game.getCommandManager().register(this, new CommandFakejoin(), "fakejoin");
    	if(FileCommands.FAKELEAVE()) game.getCommandManager().register(this, new CommandFakeleave(), "fakeleave");
    	if(FileCommands.FEED()) game.getCommandManager().register(this, new CommandFeed(), "feed");
    	if(FileCommands.FLY()) game.getCommandManager().register(this, new CommandFly(), "fly");
    	if(FileCommands.FORCE()) game.getCommandManager().register(this, new CommandForce(game), "force", "sudo");
    	if(FileCommands.GAMEMODE()) game.getCommandManager().register(this, new CommandGamemode(), "gamemode", "gm");
    	if(FileCommands.GIVE()) game.getCommandManager().register(this, new CommandGive(), "give", "g");
    	if(FileCommands.GOD()) game.getCommandManager().register(this, new CommandGod(), "god");
    	if(FileCommands.HEAL()) game.getCommandManager().register(this, new CommandHeal(game), "heal");
    	if(FileCommands.HOME()) game.getCommandManager().register(this, new CommandHome(), "home");
    	if(FileCommands.ITEM()) game.getCommandManager().register(this, new CommandItem(), "item", "i");
    	if(FileCommands.JUMP()) game.getCommandManager().register(this, new CommandJump(), "jump", "j");
    	if(FileCommands.KICK()) game.getCommandManager().register(this, new CommandKick(game), "kick");
    	if(FileCommands.KILL()) game.getCommandManager().register(this, new CommandKill(game), "kill");
    	if(FileCommands.LIST()) game.getCommandManager().register(this, new CommandList(game), "list", "who");
    	if(FileCommands.MAIL()) game.getCommandManager().register(this, new CommandMail(), "mail");
    	if(FileCommands.MEMORY()) game.getCommandManager().register(this, new CommandMemory(), "memory");
    	if(FileCommands.MSG()) game.getCommandManager().register(this, new CommandMessage(), "m", "msg", "message", "w", "whisper", "tell");
    	if(FileCommands.MOTD()) game.getCommandManager().register(this, new CommandMotd(), "motd");
    	if(FileCommands.MUTE()) game.getCommandManager().register(this, new CommandMute(game), "mute");
    	if(FileCommands.NEXUS()) game.getCommandManager().register(this, new CommandNexus(), "nexus");
    	if(FileCommands.NICK()) game.getCommandManager().register(this, new CommandNick(), "nick");
    	if(FileCommands.ONLINETIME()) game.getCommandManager().register(this, new CommandOnlinetime(game), "onlinetime");
    	if(FileCommands.PING()) game.getCommandManager().register(this, new CommandPing(), "ping");
    	if(FileCommands.POWERTOOL()) game.getCommandManager().register(this, new CommandPowertool(game), "powertool");
    	if(FileCommands.REALNAME()) game.getCommandManager().register(this, new CommandRealname(), "realname");
    	if(FileCommands.REPLY()) game.getCommandManager().register(this, new CommandReply(), "r", "reply");
    	if(FileCommands.RULES()) game.getCommandManager().register(this, new CommandRules(), "rules");
    	if(FileCommands.SEARCHITEM()) game.getCommandManager().register(this, new CommandSearchitem(), "searchitem", "si", "search");
    	if(FileCommands.SEEN()) game.getCommandManager().register(this, new CommandSeen(game), "seen");
    	if(FileCommands.SPAWN()) game.getCommandManager().register(this, new CommandSpawn(), "spawn");
    	if(FileCommands.SPEED()) game.getCommandManager().register(this, new CommandSpeed(), "speed");
    	if(FileCommands.TEMPBAN()) game.getCommandManager().register(this, new CommandTempban(game), "tempban");
    	if(FileCommands.TICKET()) game.getCommandManager().register(this, new CommandTicket(), "ticket");
    	if(FileCommands.TIME()) game.getCommandManager().register(this, new CommandTime(game), "time");
    	if(FileCommands.TP()) game.getCommandManager().register(this, new CommandTP(game), "tp", "teleport");
    	if(FileCommands.TPA()) game.getCommandManager().register(this, new CommandTPA(game), "tpa");
    	if(FileCommands.TPACCEPT()) game.getCommandManager().register(this, new CommandTPAccept(game), "tpaccept");
    	if(FileCommands.TPAHERE()) game.getCommandManager().register(this, new CommandTPAHere(game), "tpahere");
    	if(FileCommands.TPDEATH()) game.getCommandManager().register(this, new CommandTPDeath(game), "tpdeath", "back");
    	if(FileCommands.TPDENY()) game.getCommandManager().register(this, new CommandTPDeny(game), "tpdeny");
    	if(FileCommands.TPHERE()) game.getCommandManager().register(this, new CommandTPHere(game), "tphere");
    	if(FileCommands.TPPOS()) game.getCommandManager().register(this, new CommandTPPos(game), "tppos");
    	if(FileCommands.TPSWAP()) game.getCommandManager().register(this, new CommandTPSwap(game), "tpswap");
    	if(FileCommands.TPWORLD()) game.getCommandManager().register(this, new CommandTPWorld(game), "tpworld");
    	if(FileCommands.UNBAN()) game.getCommandManager().register(this, new CommandUnban(game), "unban");
    	if(FileCommands.UNMUTE()) game.getCommandManager().register(this, new CommandUnmute(game), "unmute");
    	if(FileCommands.VANISH()) game.getCommandManager().register(this, new CommandVanish(), "vanish", "v");
    	if(FileCommands.WARP()) game.getCommandManager().register(this, new CommandWarp(), "warp");
    	if(FileCommands.WEATHER()) game.getCommandManager().register(this, new CommandWeather(game), "weather");
    	if(FileCommands.WHOIS()) game.getCommandManager().register(this, new CommandWhois(), "whois", "check");

    	game.getCommandManager().register(this, new CommandPage(), "page");

    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			NexusDatabase.commit();
    		}
    	}).submit(this);

    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			for(Entry<String, NexusPortal> e : NexusDatabase.getPortals().entrySet()) e.getValue().transport();
    		}
    	}).submit(this);

    	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			ServerUtils.heartbeat();
    		}
    	}).submit(this);

    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {
    	NexusDatabase.commit();
    }

}
