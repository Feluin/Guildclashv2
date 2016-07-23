package Guildclash;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import Guildclash.Objects.GuildMember;
import Guildclash.Objects.Invitation;

public class Guild {
	private ArrayList<Invitation> ginvites = new ArrayList<Invitation>();
	private ArrayList<GuildMember> members = new ArrayList<GuildMember>();
	private ArrayList<String> allies = new ArrayList<String>();
	private ArrayList<String> naps = new ArrayList<String>();
	private ArrayList<String> enemies = new ArrayList<String>();
	private UUID owner;
	private String name;

	public Guild(String name, Player p) {
		this.name = name;
		this.owner = p.getUniqueId();
	}

	public Guild(String name, UUID owner, ArrayList<GuildMember> members, ArrayList<String> allies,
			ArrayList<String> naps, ArrayList<String> enemies, ArrayList<Invitation> ginvites) {
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

	public ArrayList<GuildMember> getMembers() {
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
		for (GuildMember gm : members) {
			if (gm.getUUID().compareTo(uuid) == 0) {
				return gm.getStatus();
			}
		}
		return 1000;
	}

	public boolean hasPlayer(UUID uuid) {
		if (uuid.compareTo(owner) == 0) {
			return true;
		}
		for (GuildMember gm : members) {
			if (gm.getUUID().compareTo(uuid) == 0) {
				return true;
			}
		}
		return false;
	}

	public void removePlayer(UUID uuid) {
		for (GuildMember gm : members) {
			if (uuid.compareTo(gm.getUUID()) == 0) {
				members.remove(gm);
			}
		}
	}

	public void broadcastMessage(String msg) {
		for (GuildMember u : members) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(u.getUUID());
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

	public void addInvitation(Invitation in) {
		ginvites.add(in);
	}

	public void removeInvitation(Invitation in) {
		ginvites.remove(in);
	}

	public boolean isInvited(UUID uuid) {
		for (Invitation in : ginvites) {
			if (in.getPlayer().compareTo(uuid) == 0 && in.isExpired() == false) {
				return true;
			}
		}
		return false;
	}

	public void addMember(UUID uuid) {
		members.add(new GuildMember(uuid, 100));
	}

	public void setStatus(UUID uuid, int status) {
		for (GuildMember gm : members) {
			if (gm.getUUID().compareTo(uuid) == 0) {
				gm.setStatus(status);
			}
		}
	}
}
