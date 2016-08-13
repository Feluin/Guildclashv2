package Guildclash;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Guildclash.Commands.Commands;
import Guildclash.Schematics.Schematic;
import Guildclash.Schematics.SchematicManager;
import Guildclash.Yggdrasil.Yggdrasil;

public class Guildplugin extends JavaPlugin {
	private static Guildmanager guildmanager;
	private static String guildfolder = "/guilds";;
	private static Yggdrasil yggdrasil;

	@Override
	public void onEnable() {
		guildfolder = getDataFolder() + "/guilds";
		// Erstelle Manager
		guildmanager = new Guildmanager();
		guildmanager.loadGuilds();
		yggdrasil = new Yggdrasil();
		
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
		if (command.getName().equalsIgnoreCase("Yggdrasil")) {
			p.teleport(yggdrasil.getWorld().getSpawnLocation());
		}
		if (command.getName().equalsIgnoreCase("paste")) {
			try {
				Schematic portal= SchematicManager.loadSchematic(new File("C:/Users/Pascal/Desktop/Servers/BuildTools/Server/plugins/WorldEdit/schematics/portal_north_dm.schematic.gz"));
				SchematicManager.pasteSchematic(p.getWorld(), p.getLocation(), portal);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(command.getName().equalsIgnoreCase("portal")){
			Location xLocation =yggdrasil.getPortalLocationbyIndex(Integer.parseInt(args[0]));
			p.sendMessage(xLocation.getBlockX()+"");
			p.sendMessage(xLocation.getBlockY()+"");
			p.sendMessage(xLocation.getBlockZ()+"");
		}
		} else {
			sender.sendMessage("Du musst ein Spieler sein um diesen Befehl benutzen zu können");
		}
		return false;
	}

	public static String getGuildFolder() {
		return guildfolder;
	}

	public static Guildmanager getGuildManager() {
		return guildmanager;
	}
}