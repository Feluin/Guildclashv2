package Guildclash.Objects;

import org.bukkit.entity.Player;

import Guildclash.Guild;

public class Confirmation {
	private Player p;
	private Guild g;
	private int type;
	private int remainingticks;

	public Confirmation(Player p, Guild g, int type, int confirmationtime) {
		this.p = p;
		this.type = type;
		this.g = g;
		this.remainingticks = confirmationtime;
	}

	public Guild getGuild() {
		return g;
	}

	public int getType() {
		return type;
	}

	public Player getPlayer() {
		return p;
	}

	public void setRemainingTicks(int remainingticks) {
		this.remainingticks = remainingticks;
	}

	public int getRemainingTicks() {
		return remainingticks;
	}

}
