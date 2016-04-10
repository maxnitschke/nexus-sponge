package main.java.me.cuebyte.nexus.customized;

public class NexusMute {

	private String uuid;
	private double duration;
	private String reason;
	
	public NexusMute(String uuid, double duration, String reason) {
		this.uuid = uuid;
		this.duration = duration;
		this.reason = reason;
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setDuration(double duration) { this.duration = duration; }
	public void setReason(String reason) { this.reason = reason; }
	
	public String getUUID() { return uuid; }
	public double getDuration() { return duration; }
	public String getReason() { return reason; }
	
	public void insert() {
		NexusDatabase.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		NexusDatabase.addMute(uuid, this);
	}
	
	public void update() {
		NexusDatabase.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE uuid = '" + uuid + "'");
		NexusDatabase.removeMute(uuid);
		NexusDatabase.addMute(uuid, this);
	}
	
	public void delete() {
		NexusDatabase.queue("DELETE FROM mutes WHERE uuid = '" + uuid + "'");
		NexusDatabase.removeMute(uuid);
	}
	
}
