package main.java.me.creepsterlgc.nexus.api;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusMute;

public class NexusAPIMuteManager {

	public static NexusAPIMuteManager instance;
	
	public boolean addMute(String uuid, double duration, String reason) {
		if(NexusDatabase.getMute(uuid) != null) return false;
		NexusMute mute = new NexusMute(uuid, duration, reason); mute.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban mute be issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * reason = The ban's reason
	 * duration = You go with: (System.currentTimeMillis() + TIME_IN_MILLISECONDS)
	 * 
	 * EXAMPLE: ("my-unique-id", "Don't spam please!", System.currentTimeMillis() + 1000 * 60)
	 * Would mute "my-unique-id" for 1 minute.
	 * 
	 */
	
	public boolean removeMute(String uuid) {
		if(NexusDatabase.getMute(uuid) == null) return false;
		NexusMute mute = NexusDatabase.getMute(uuid); mute.delete(); return true;
	}
	
	/*
	 * RETURNS: true if the ban mute has been issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would unmute "my-unique-id".
	 * 
	 */
	
	public boolean isMuted(String uuid) {
		return NexusDatabase.getMute(uuid) != null;
	}
	
	/*
	 * RETURNS: true if the uuid is muted, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
	public NexusMute getMute(String uuid) {
		return NexusDatabase.getMute(uuid) != null ? NexusDatabase.getMute(uuid) : null;
	}
	
	/*
	 * RETURNS: the mute or null if the player is not muted.
	 * 
	 */
	
}
