package main.java.me.creepsterlgc.nexus.customized;

import java.util.HashMap;

public class NexusChannels {

	private static HashMap<String, NexusChannel> channels = new HashMap<String, NexusChannel>();
	public static void add(String name, NexusChannel channel) { if(!channels.containsKey(name)) channels.put(name, channel); }
	public static void remove(String name) { if(channels.containsKey(name)) channels.remove(name); }
	public static NexusChannel get(String name) { return channels.containsKey(name) ? channels.get(name) : null; }
	public static HashMap<String, NexusChannel> all() { return channels; }
	
}
