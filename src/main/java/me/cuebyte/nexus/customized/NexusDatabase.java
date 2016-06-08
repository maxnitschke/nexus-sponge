package main.java.me.cuebyte.nexus.customized;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;

import main.java.me.cuebyte.nexus.files.FileConfig;
import main.java.me.cuebyte.nexus.utils.DeserializeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.service.sql.SqlService;

public class NexusDatabase {
	
	public static SqlService sql;
	public static DataSource datasource;
	
	public static List<String> queue = new ArrayList<String>();
	
	public static void setup(Game game) {

		try {
			
			sql = game.getServiceManager().provide(SqlService.class).get();
			
			if(!FileConfig.MYSQL_USE()) {
				
		    	File folder = new File("config/nexus/data");
		    	if(!folder.exists()) folder.mkdir();
		    	
				datasource = sql.getDataSource("jdbc:sqlite:config/nexus/data/nexus.db");
				
			}
			else {
				
				String host = FileConfig.MYSQL_HOST();
				String port = String.valueOf(FileConfig.MYSQL_PORT());
				String username = FileConfig.MYSQL_USERNAME();
				String password = FileConfig.MYSQL_PASSWORD();
				String database = FileConfig.MYSQL_DATABASE();
				
				datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);
				
			}
			
			DatabaseMetaData metadata = datasource.getConnection().getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();		
			while(resultset.next()) {
				tables.add(resultset.getString(3));
			}

			if(!tables.contains("bans")) {
				execute("CREATE TABLE bans (uuid TEXT, sender TEXT, reason TEXT, time DOUBLE, duration DOUBLE)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (uuid TEXT, name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (uuid TEXT, duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (uuid TEXT, name TEXT, nick TEXT, channel TEXT, god DOUBLE, fly DOUBLE, tptoggle DOUBLE, invisible DOUBLE, onlinetime DOUBLE, mails TEXT, lastlocation TEXT, lastdeath TEXT, firstseen DOUBLE, lastseen DOUBLE)");
			}
			
			if(!tables.contains("spawns")) {
				execute("CREATE TABLE spawns (name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, message TEXT)");
			}
			
			if(!tables.contains("tickets")) {
				execute("CREATE TABLE tickets (id DOUBLE, uuid TEXT, message TEXT, time DOUBLE, comments TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, assigned TEXT, priority TEXT, status TEXT)");
			}
			
			if(!tables.contains("warps")) {
				execute("CREATE TABLE warps (name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, owner TEXT, invited TEXT, private TEXT, message TEXT)");
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	
	public static void load(Game game) {
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				NexusBan ban = new NexusBan(rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				NexusDatabase.addBan(ban.getUUID(), ban);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM mutes");
			while(rs.next()) {
				NexusMute mute = new NexusMute(rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				NexusDatabase.addMute(mute.getUUID(), mute);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM players");
			while(rs.next()) {
				NexusPlayer player = new NexusPlayer(rs.getString("uuid"), rs.getString("name"), rs.getString("nick"), rs.getString("channel"), rs.getDouble("god"), rs.getDouble("fly"), rs.getDouble("tptoggle"), rs.getDouble("invisible"), rs.getDouble("onlinetime"), rs.getString("mails"), rs.getString("lastlocation"), rs.getString("lastdeath"), rs.getDouble("firstseen"), rs.getDouble("lastseen"));
				NexusDatabase.addPlayer(player.getUUID(), player);
				NexusDatabase.addUUID(player.getName(), player.getUUID());
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM homes");
			while(rs.next()) {
				NexusHome home = new NexusHome(rs.getString("uuid"), rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				NexusPlayer player = NexusDatabase.getPlayer(home.getUUID());
				player.setHome(home.getName(), home);
				NexusDatabase.removePlayer(home.getUUID());
				NexusDatabase.addPlayer(home.getUUID(), player);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM spawns");
			while(rs.next()) {
				NexusSpawn spawn = new NexusSpawn(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("message"));
				NexusDatabase.addSpawn(spawn.getName(), spawn);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM tickets");
			while(rs.next()) {
				NexusTicket ticket = new NexusTicket((int)rs.getDouble("id"), rs.getString("uuid"), rs.getString("message"), rs.getDouble("time"), DeserializeUtils.messages(rs.getString("comments")), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("assigned"), rs.getString("priority"), rs.getString("status"));
				NexusDatabase.addTicket(ticket.getID(), ticket);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM warps");
			while(rs.next()) {
				NexusWarp warp = new NexusWarp(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("owner"), DeserializeUtils.list(rs.getString("invited")), rs.getString("private"), rs.getString("message"));
				NexusDatabase.addWarp(warp.getName(), warp);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void execute(String execute) {	
		try {
			
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(List<String> execute) {	
		try {
		
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit() {
		
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
		
	}
	
	public static void queue(String queue) { NexusDatabase.queue.add(queue); }
	
	private static HashMap<String, NexusBan> bans = new HashMap<String, NexusBan>();
	public static void addBan(String uuid, NexusBan ban) { if(!bans.containsKey(uuid)) bans.put(uuid, ban); }
	public static void removeBan(String uuid) { if(bans.containsKey(uuid)) bans.remove(uuid); }
	public static NexusBan getBan(String uuid) { return bans.containsKey(uuid) ? bans.get(uuid) : null; }
	public static HashMap<String, NexusBan> getBans() { return bans; }
	
	private static HashMap<String, NexusMute> mutes = new HashMap<String, NexusMute>();
	public static void addMute(String uuid, NexusMute mute) { if(!mutes.containsKey(uuid)) mutes.put(uuid, mute); }
	public static void removeMute(String uuid) { if(mutes.containsKey(uuid)) mutes.remove(uuid); }
	public static NexusMute getMute(String uuid) { return mutes.containsKey(uuid) ? mutes.get(uuid) : null; }
	public static HashMap<String, NexusMute> getMutes() { return mutes; }
	
	private static HashMap<String, NexusPlayer> players = new HashMap<String, NexusPlayer>();
	public static void addPlayer(String uuid, NexusPlayer player) { if(!players.containsKey(players)) players.put(uuid, player); }
	public static void removePlayer(String uuid) { if(players.containsKey(uuid)) players.remove(uuid); }
	public static NexusPlayer getPlayer(String uuid) { return players.containsKey(uuid) ? players.get(uuid) : null; }
	public static HashMap<String, NexusPlayer> getPlayers() { return players; }
	
	private static HashMap<String, String> uuids = new HashMap<String, String>();
	public static void addUUID(String name, String uuid) { uuids.put(name, uuid); }
	public static void removeUUID(String name) { if(uuids.containsKey(name)) uuids.remove(name); }
	public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }
	
	private static HashMap<String, NexusSpawn> spawns = new HashMap<String, NexusSpawn>();
	public static void addSpawn(String name, NexusSpawn spawn) { if(!spawns.containsKey(name)) spawns.put(name, spawn); }
	public static void removeSpawn(String name) { if(spawns.containsKey(name)) spawns.remove(name); }
	public static NexusSpawn getSpawn(String name) { return spawns.containsKey(name) ? spawns.get(name) : null; }
	public static HashMap<String, NexusSpawn> getSpawns() { return spawns; }
	
	private static HashMap<Integer, NexusTicket> tickets = new HashMap<Integer, NexusTicket>();
	public static void addTicket(int id, NexusTicket ticket) { if(!tickets.containsKey(id)) tickets.put(id, ticket); }
	public static void removeTicket(int id) { if(tickets.containsKey(id)) tickets.remove(id); }
	public static NexusTicket getTicket(int id) { return tickets.containsKey(id) ? tickets.get(id) : null; }
	public static HashMap<Integer, NexusTicket> getTickets() { return tickets; }
	public static void clearTickets() { tickets.clear(); }
	
	private static HashMap<String, NexusWarp> warps = new HashMap<String, NexusWarp>();
	public static void addWarp(String name, NexusWarp warp) { if(!warps.containsKey(name)) warps.put(name, warp); }
	public static void removeWarp(String name) { if(warps.containsKey(name)) warps.remove(name); }
	public static NexusWarp getWarp(String name) { return warps.containsKey(name) ? warps.get(name) : null; }
	public static HashMap<String, NexusWarp> getWarps() { return warps; }
	
}
