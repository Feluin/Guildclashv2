package Guildclash;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Guildplugin extends JavaPlugin {
	private Guildmanager guildmanager;
	public static String guildfolder;

	@Override
	public void onEnable() {
		guildfolder = getDataFolder() + "/guilds";
		// Erstelle Manager
		guildmanager = new Guildmanager();
		guildmanager.loadGuilds();
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
					}else if(args[0].equalsIgnoreCase("kick")){
						if(guildmanager.hasaguildalready(p.getUniqueId())){
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							if(g.getPermissionLevel(p.getUniqueId())==0){

							}
						}
						else{
							p.sendMessage("Du bist in keinem Bündnis");
						}
					}
					else if(args[0].equalsIgnoreCase("delete")){
						if(guildmanager.hasaguildalready(p.getUniqueId())){
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							if(g.getPermissionLevel(p.getUniqueId())==0){
								if(guildmanager.removeGuild(g)){
									p.sendMessage("Das Bündnis wurde aufgelöst");
								}
							}
						}
						else{
							p.sendMessage("Du bist in keinem Bündnis");
						}
					}
					else {
						p.sendMessage("/guild command [param]");
					}
				} else {
					p.sendMessage("/guild command [param]");
				}
			}
		}
		return false;
	}
}