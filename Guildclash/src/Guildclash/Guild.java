package Guildclash;


import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

	
public class Guild {
	ArrayList<UUID> playerUUID =new ArrayList<UUID>();
	ArrayList<Guild> ailliedGuilds = new ArrayList<Guild>();
	ArrayList<Guild> NapGuilds = new ArrayList<Guild>();
	ArrayList<Guild> enemyGuilds = new ArrayList<Guild>();
	
	File guildfile;
	File uneditedguildworld=new File("Guildclash/uneditedguildworld");
	File worldfile;
	World guildworld;
 	public Guild(String guildname, Player player, File guildordner) {
		playerUUID.add(player.getUniqueId());
			worldfile=new File("Guildclash/Worlds/"+guildname+"world");
			guildfile=new File(guildordner.getAbsolutePath()+"/"+guildname+".txt");
			try {
				Files.copy(uneditedguildworld.toPath(), worldfile.toPath() );
			} catch (Exception e) {
				// TODO: handle exception
			}
			WorldCreator w=new WorldCreator("w");
			
			World world=Bukkit.createWorld(new WorldCreator(worldfile.getName()));
			
	}
 	public Guild(File guildfile){
 		this.guildfile=guildfile;
 	}
 	
 	
 	public void save() {
		// TODO Auto-generated method stub

	}
 	public void load(File f){
 		
 	}

}
