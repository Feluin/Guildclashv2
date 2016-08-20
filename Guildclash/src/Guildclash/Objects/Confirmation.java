package Guildclash.Objects;

import org.bukkit.entity.Player;

import Guildclash.Guild;

public class Confirmation {
	private Player p;
	private Guild g;
	private String[] args;
	private int remainingticks;

	public Confirmation(Player p, Guild g, String[] args, int confirmationtime) {
		this.p = p;
		this.g = g;
		this.args = args;
		this.remainingticks = confirmationtime;
	}

	public Guild getGuild() {
		return g;
	}

	public String[] getArgs() {
		return args;
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
