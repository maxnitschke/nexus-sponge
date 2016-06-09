package main.java.me.cuebyte.nexus.customized;

import java.util.HashMap;
import java.util.List;

import org.spongepowered.api.text.Text;

public class NexusPlayer {
	
	private String uuid;
	private String name;
	private String nick;
	private String channel;
	private double god;
	private double fly;
	private double tptoggle;
	private double invisible;
	private double onlinetime;
	private String mails;
	private String lastlocation;
	private String lastdeath;
	private double firstseen;
	private double lastseen;
	
	private double lastaction;
	private boolean afk;
	private HashMap<String, NexusHome> homes;
	private String reply;
	private boolean spy;
	private HashMap<String, Double> tpa;
	private HashMap<String, Double> tpahere;
	private HashMap<String, String> powertools;
	private HashMap<Integer, List<Text>> pages;
	private Text page_title;
	private Text page_header;
	
	public NexusPlayer(String uuid, String name, String nick, String channel, double god, double fly, double tptoggle, double invisible, double onlinetime, String mails, String lastlocation, String lastdeath, double firstseen, double lastseen) {
		
		this.uuid = uuid;
		this.name = name;
		this.nick = nick;
		this.channel = channel;
		this.god = god;
		this.fly = fly;
		this.tptoggle = tptoggle;
		this.invisible = invisible;
		this.onlinetime = onlinetime;
		this.mails = mails;
		this.lastlocation = lastlocation;
		this.lastdeath = lastdeath;
		this.firstseen = firstseen;
		this.lastseen = lastseen;
		
		lastaction = 0;
		afk = false;
		homes = new HashMap<String, NexusHome>();
		reply = "";
		spy = false;
		tpa = new HashMap<String, Double>();
		tpahere = new HashMap<String, Double>();
		powertools = new HashMap<String, String>();
		pages = new HashMap<Integer, List<Text>>();
		
	}
	
	public void insert() {
		NexusDatabase.queue("INSERT INTO players VALUES ('" + uuid + "', '" + name + "', '" + nick + "', '" + channel + "', " + god + ", " + fly + ", " + tptoggle + ", " + invisible + ", " + onlinetime + ", '" + mails + "', '" + lastlocation + "', '" + lastdeath + "', " + firstseen + ", " + lastseen + ")");
		NexusDatabase.addPlayer(uuid, this);
		NexusDatabase.addUUID(name, uuid);
	}
	
	public void update() {
		NexusDatabase.queue("UPDATE players SET name = '" + name + "', nick = '" + nick + "', channel = '" + channel + "', god = " + god + ", fly = " + fly + ", tptoggle = " + tptoggle + ", invisible = " + invisible + ", onlinetime = " + onlinetime + ", mails = '" + mails + "', lastlocation = '" + lastlocation + "', lastdeath = '" + lastdeath + "', firstseen = " + firstseen + ", lastseen = " + lastseen + " WHERE uuid = '" + uuid + "'");
		NexusDatabase.removePlayer(uuid);
		NexusDatabase.removeUUID(name);
		NexusDatabase.addPlayer(uuid, this);
		NexusDatabase.addUUID(name, uuid);
	}
	
	public void delete() {
		NexusDatabase.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		NexusDatabase.removePlayer(uuid);
		NexusDatabase.removeUUID(name);
	}

	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setNick(String nick) { this.nick = nick; }
	public void setChannel(String channel) { this.channel = channel; }
	public void setGod(double god) { this.god = god; }
	public void setFly(double fly) { this.fly = fly; }
	public void setTPToggle(double tptoggle) { this.tptoggle = tptoggle; }
	public void setInvisible(double invisible) { this.invisible = invisible; }
	public void setOnlinetime(double onlinetime) { this.onlinetime = onlinetime; }
	public void setMails(String mails) { this.mails = mails; }
	public void setLastlocation(String lastlocation) { this.lastlocation = lastlocation; }
	public void setLastdeath(String lastdeath) { this.lastdeath = lastdeath; }
	public void setFirstseen(double firstseen) { this.firstseen = firstseen; }
	public void setLastseen(double lastseen) { this.lastseen = lastseen; }

	public void setLastaction(double lastaction) { this.lastaction = lastaction; }
	public void setAFK(boolean afk) { this.afk = afk; }
	public void setHome(String name, NexusHome home) { if(homes == null) homes = new HashMap<String, NexusHome>(); homes.put(name, home); }
	public void setHomes(HashMap<String, NexusHome> homes) { if(homes == null) homes = new HashMap<String, NexusHome>(); this.homes = homes; }
	public void setReply(String reply) { this.reply = reply; }
	public void setSpy(boolean spy) { this.spy = spy; }
	public void setTPA(HashMap<String, Double> tpa) { this.tpa = tpa; }
	public void setTPAHere(HashMap<String, Double> tpahere) { this.tpahere = tpahere; }
	public void setPowertools(HashMap<String, String> powertools) { this.powertools = powertools; }
	public void setPages(HashMap<Integer, List<Text>> pages) { this.pages = pages; }
	public void setPageTitle(Text page_title) { this.page_title = page_title; }
	public void setPageHeader(Text page_header) { this.page_header = page_header; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getNick() { return nick; }
	public String getChannel() { return channel; }
	public double getGod() { return god; }
	public double getFly() { return fly; }
	public double getTPToggle() { return tptoggle; }
	public double getInvisible() { return invisible; }
	public double getOnlinetime() { return onlinetime; }
	public String getMails() { return mails; }
	public String getLastlocation() { return lastlocation; }
	public String getLastdeath() { return lastdeath; }
	public double getFirstseen() { return firstseen; }
	public double getLastseen() { return lastseen; }
	
	public double getLastaction() { return lastaction; }
	public boolean getAFK() { return afk; }
	public NexusHome getHome(String name) { if(homes == null) homes = new HashMap<String, NexusHome>(); return homes.containsKey(name) ? homes.get(name) : null; }
	public HashMap<String, NexusHome> getHomes() { if(homes == null) homes = new HashMap<String, NexusHome>(); return homes; }
	public String getReply() { return reply; }
	public boolean getSpy() { return spy; }
	public HashMap<String, Double> getTPA() { return tpa; }
	public HashMap<String, Double> getTPAHere() { return tpahere; }
	public HashMap<Integer, List<Text>> getPages() { return pages; }
	public HashMap<String, String> getPowertools() { return powertools; }
	public Text getPageTitle() { return page_title; }
	public Text getPageHeader() { return page_header; }
	
}
