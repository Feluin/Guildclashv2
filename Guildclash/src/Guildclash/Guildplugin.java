package Guildclash;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Guildplugin extends JavaPlugin {
	private Guildmanager guildmanager;
	public static String guildfolder;
	public static String warpfolder;
	private Warpmanager warpmanager;

	@Override
	public void onEnable() {
		guildfolder = getDataFolder() + "/guilds";
		warpfolder = getDataFolder() + "/warp";
		// Erstelle Manager
		guildmanager = new Guildmanager();
		guildmanager.loadGuilds();
		warpmanager = new Warpmanager();
		warpmanager.loadWarps();
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
						if (args.length > 1) {
							String guildname = args[1];
							if (!guildmanager.hasaguildalready(p.getUniqueId())) {
								if (!guildmanager.existsalready(guildname)) {
									guildmanager.createNewGuild(guildname, p);
									p.sendMessage("Das Bündnis " + guildname + " wurde erstellt");
								} else {
									p.sendMessage("Dieser Name ist bereits vergeben");
								}
							} else {
								p.sendMessage("Du bist bereits in einem Bündnis");
							}
						} else {
							p.sendMessage("/guild create name");
						}
					} else {
						p.sendMessage("/guild command [param]");
					}
				} else {
					p.sendMessage("/guild command [param]");
				}
			}
			if (command.getName().equalsIgnoreCase("warp")) {
				if (args.length == 0) {
					System.out.println("noch nicht initalisiert");
				}else if (args.length <= 1) {
					if (args[0].equalsIgnoreCase("new")) {
						if (args.length == 2) {
							if (!args[1].equalsIgnoreCase("new")) {
								warpmanager.createNewWarp(args[1], p.getLocation(), 0);
							}
						}
					} else {
						Warp w = warpmanager.getWarpByName(args[0]);
						System.out.println(args[0]);
						if (w != null) {
							p.teleport(w.getLoc());
						} else {
							p.sendMessage("Warp wurde nicht gefunden!");
						}
					}
				}

			}

		}
		return false;
	}
}