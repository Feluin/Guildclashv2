package Guildclash;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import Guildclash.Objects.Invitation;

public class Guild {
	private ArrayList<Invitation> ginvites = new ArrayList<Invitation>();
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
			ArrayList<String> enemies, ArrayList<Invitation> ginvites) {
		this.name = name;
		this.owner = owner;
		this.members = members;
		this.allies = allies;
		this.enemies = enemies;
		this.naps = naps;
		this.ginvites = ginvites;
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

	public ArrayList<Invitation> getInvitations() {
		return ginvites;
	}

	public int getPermissionLevel(UUID uuid) {
		if (owner.compareTo(uuid) == 0) {
			return 0;
		}
		return 100;
	}

	public boolean hasPlayer(UUID uuid) {
		for (UUID u : members) {
			if (uuid.compareTo(u) == 0) {
				return true;
			}
		}
		if (uuid.compareTo(owner) == 0) {
			return true;
		}
		return false;
	}

	public void removePlayer(UUID uuid) {
		for (UUID u : members) {
			if (uuid.compareTo(u) == 0) {
				members.remove(u);
			}
		}
	}

	public void broadcastMessage(String msg) {
		for (UUID u : members) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(u);
			if (op != null) {
				if (op.isOnline()) {
					Player p = op.getPlayer();
					p.sendMessage(msg);
				}
			}
		}
		OfflinePlayer op = Bukkit.getOfflinePlayer(owner);
		if (op != null) {
			if (op.isOnline()) {
				Player p = op.getPlayer();
				p.sendMessage(msg);
			}
		}
	}

}
