package Guildclash;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Guildplugin extends JavaPlugin {
	private Guildmanager guildmanager ;
	File mainordner= new File("Guildclash");

	@Override
	public void onEnable() {
		System.out.println("Guildclash enabled");
		mainordner.mkdir();
		guildmanager= new Guildmanager(mainordner);
		mainordner.mkdirs();
	}
	@Override
	public void onDisable() {
		guildmanager.saveGuilds();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase("guild")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("create")) {
						player.sendMessage("bündnis wird erstellt!");
						if(args.length>1){
							String guildname="";
							for (int i = 1; i < args.length; i++) {
								guildname+=" "+args[i];
							}
							guildmanager.createNewGuild(guildname,player);
					}
				}
			}

		}
		return true;
	}
		return true;
}
}