package Guildclash;

import org.bukkit.Location;

public class Warp {
	private String name;
	private Location loc;
	private int permission;
	public Warp(String name,Location loc,int permission) {
		// TODO Auto-generated constructor stub
		this.setLoc(loc);
		this.setName(name);
		this.setPermission(permission);
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
