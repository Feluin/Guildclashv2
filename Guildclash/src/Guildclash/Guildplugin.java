package Guildclash;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Guildclash.Commands.Commands;

public class Guildplugin extends JavaPlugin {
	private static Guildmanager guildmanager;
	private static String mainfolder = "";
	private static String guildfolder = "/guilds";
	private static String worldfolder = "/guilds";

	@Override
	public void onEnable() {
		mainfolder = getDataFolder() + "";
		guildfolder = mainfolder + "/guilds";
		worldfolder = mainfolder + "/worlds";
		// Erstelle Manager
		guildmanager = new Guildmanager();
		guildmanager.loadGuilds();
		guildmanager.loadWorlds();
	}

	@Override
	public void onDisable() {
		guildmanager.saveGuilds();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (command.getName().equalsIgnoreCase("guild")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("create")) {
						if (Commands.doGuildCreateCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("kick")) {
						if (Commands.doGuildKickCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("promote")) {
						if (Commands.doGuildPromoteCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("demote")) {
						if (Commands.doGuildDemoteCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("invite")) {
						if (Commands.doGuildInviteCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("join")) {
						if (Commands.doGuildJoinCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("delete")) {
						if (Commands.doGuildDeleteCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("leave")) {
						if (Commands.doGuildLeaveCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("home")) {
						if (Commands.doGuildHomeCommand(p, args)) {
							return true;
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if (Commands.doGuildInfoCommand(p, args)) {
							return true;
						}
					} else {
						p.sendMessage("/guild help page");
					}
				} else {
					p.sendMessage("/guild help page");
				}
			}
		} else {
			sender.sendMessage("Du musst ein Spieler sein um diesen Befehl benutzen zu können");
		}
		return false;
	}

	public static String getGuildFolder() {
		return guildfolder;
	}

	public static String getWorldFolder() {
		return worldfolder;
	}

	public static Guildmanager getGuildManager() {
		return guildmanager;
	}

	public static String getMainFolder() {
		return mainfolder;
	}

}