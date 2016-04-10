package main.java.me.cuebyte.nexus.api;

public class NexusAPI {
	
	public NexusAPIBanManager getBanManager() {
		if(NexusAPIBanManager.instance == null) NexusAPIBanManager.instance = new NexusAPIBanManager();
		return NexusAPIBanManager.instance;
	}
	
	/*
	 * returns Nexus's ban manager @(/me/creepsterlgc/nexus/api/NexusAPIBanManager)
	 */
	
	public NexusAPIMuteManager getMuteManager() {
		if(NexusAPIMuteManager.instance == null) NexusAPIMuteManager.instance = new NexusAPIMuteManager();
		return NexusAPIMuteManager.instance;
	}
	
	/*
	 * returns Nexus's mute manager @(/me/creepsterlgc/nexus/api/NexusAPIMuteManager)
	 * 
	 */
	
	public NexusAPIWarpManager getWarpManager() {
		if(NexusAPIWarpManager.instance == null) NexusAPIWarpManager.instance = new NexusAPIWarpManager();
		return NexusAPIWarpManager.instance;
	}
	
	/*
	 * returns Nexus's warp manager @(/me/creepsterlgc/nexus/api/NexusAPIWarpManager)
	 * 
	 */
	
	public NexusAPISpawnManager getSpawnManager() {
		if(NexusAPISpawnManager.instance == null) NexusAPISpawnManager.instance = new NexusAPISpawnManager();
		return NexusAPISpawnManager.instance;
	}

	/*
	 * returns Nexus's spawn manager @(/me/creepsterlgc/nexus/api/NexusAPISpawnManager)
	 * 
	 */
	
}
