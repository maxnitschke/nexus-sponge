package main.java.me.cuebyte.nexus.api;

import main.java.me.cuebyte.nexus.customized.NexusBan;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;

public class NexusAPIBanManager {

	public static NexusAPIBanManager instance;
	
	public boolean addBan(String uuid, String sender, String reason, double time, double duration) {
		if(NexusDatabase.getBan(uuid) != null) return false;
		NexusBan ban = new NexusBan(uuid, sender.toLowerCase(), reason, time, duration); ban.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban could be issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * sender = Sender issuing the ban
	 * reason = The ban's reason
	 * time = Systemtime when the ban has been issued
	 * duration = If the ban is permanent you go with 0. Otherwise you do: (System.currentTimeMillis() + TIME_IN_MILLISECONDS)
	 * 
	 * EXAMPLE: ("my-unique-id", "creepsterlgc", "griefing my house", System.currentTimeMillis(), System.currentTimeMillis() + 1000 * 3600)
	 * Would ban "my-unique-id" for 1 hour.
	 * 
	 */
	
	public boolean removeBan(String uuid) {
		if(NexusDatabase.getBan(uuid) == null) return false;
		NexusBan ban = NexusDatabase.getBan(uuid); ban.delete(); return true;
	}
	
	/*
	 * RETURNS: true if the ban has been removed, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would unban "my-unique-id".
	 * 
	 */
	
	public boolean isBanned(String uuid) {
		return NexusDatabase.getBan(uuid) != null;
	}
	
	/*
	 * RETURNS: true if the uuid is banned, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
	public NexusBan getBan(String uuid) {
		return NexusDatabase.getBan(uuid) != null ? NexusDatabase.getBan(uuid) : null;
	}
	
	/*
	 * RETURNS: the ban or null if the player is not banned.
	 * 
	 */
	
}
