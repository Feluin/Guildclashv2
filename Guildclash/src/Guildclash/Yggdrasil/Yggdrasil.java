package Guildclash.Yggdrasil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;



public class Yggdrasil {
	private World yggdrasil;

	public Yggdrasil() {
		yggdrasil=Bukkit.createWorld(new WorldCreator("yggdrasil"));
	}

	public World getWorld() {
		return yggdrasil;
	}
	

}
