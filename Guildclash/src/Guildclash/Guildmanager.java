package Guildclash;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Guildmanager {
		ArrayList<Guild> guilds=new ArrayList<Guild>();
		File mainordner;
		File guildordner;
		private Player owner;
	public Guildmanager(File mainordner) {
		this.mainordner=mainordner;
		guildordner=new File(mainordner.getAbsolutePath()+"/Guilds");
		guildordner.mkdir();
		loadguilds();
	}
	public void createNewGuild(String guildname, Player player) {
		guilds.add(new Guild(guildname,player,guildordner));
		
	}
	public void saveGuilds() {
		// TODO Auto-generated method stub
		for (Guild g:guilds) {
			g.save();
		}
	}
	public void loadguilds(){
		for(File guildfile:guildordner.listFiles()){
			guilds.add(new Guild(guildfile));
		}
	}

}
