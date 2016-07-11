package Guildclash;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Guild {
	private ArrayList<UUID> members = new ArrayList<UUID>();
	private ArrayList<String> allies = new ArrayList<String>();
	private ArrayList<String> naps = new ArrayList<String>();
	private ArrayList<String> enemies = new ArrayList<String>();
	private UUID owner;
	private String name;
	

	public Guild(String name, Player p) {
		this.name = name;
		this.owner = p.getUniqueId();
	}

	public Guild(String name, UUID owner, ArrayList<UUID> members, ArrayList<String> allies, ArrayList<String> naps,
			ArrayList<String> enemies) {
		this.name = name;
		this.owner = owner;
		this.members = members;
		this.allies = allies;
		this.enemies = enemies;
		this.naps = naps;
	}

	public String getName() {
		return name;
	}

	public UUID getOwner() {
		return owner;
	}

	public ArrayList<UUID> getMembers() {
		return members;
	}

	public ArrayList<String> getAllies() {
		return allies;
	}

	public ArrayList<String> getNaps() {
		return naps;
	}

	public ArrayList<String> getEnemies() {
		return enemies;
	}

}
