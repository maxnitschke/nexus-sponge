package main.java.me.cuebyte.nexus.customized;

public class NexusSpawn {
	
	private String name;
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	private String message;
	
	public NexusSpawn(String name, String world, double x, double y, double z, double yaw, double pitch, String message) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.message = message;
	}
	
	public void insert() {
		NexusDatabase.queue("INSERT INTO spawns VALUES ('" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + message + "')");
		NexusDatabase.addSpawn(name, this);
	}
	
	
	public void update() {
		NexusDatabase.queue("UPDATE spawns SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + ", message = '" + message + "' WHERE name = '" + name + "'");
		NexusDatabase.removeSpawn(name);
		NexusDatabase.addSpawn(name, this);
	}
	
	public void delete() {
		NexusDatabase.queue("DELETE FROM spawns WHERE name = '" + name + "'");
		NexusDatabase.removeSpawn(name);
	}
	
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void setYaw(double yaw) { this.yaw = yaw; }
	public void setPitch(double pitch) { this.pitch = pitch; }
	public void setMessage(String message) { this.message = message; }
	
	public String getName() { return name; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getYaw() { return yaw; }
	public double getPitch() { return pitch; }
	public String getMessage() { return message; }

}
