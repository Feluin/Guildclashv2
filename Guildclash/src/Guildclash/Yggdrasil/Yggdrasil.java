package Guildclash.Yggdrasil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.util.Vector;

public class Yggdrasil {
	private World yggdrasil;

	public Yggdrasil() {
		yggdrasil = Bukkit.createWorld(new WorldCreator("yggdrasil"));
	}

	public World getWorld() {
		return yggdrasil;
	}

	public Location getPortalLocationbyIndex(int index) {
		Vector north = new Vector(0, 0, -200);
		Vector west = new Vector(-200, 0, 0);
		Vector south = new Vector(0, 0, 200);
		Vector east = new Vector(200, 0, 0);
		int l = 1;
		int x = 0;
		int a = 0;
		Location start = yggdrasil.getSpawnLocation();
		for (int i = 0; i < index; i++) {
			if (i % 2 == 0) {
				l++;
			}
			x++;
			if (x == l) {

				a++;
				
				if (a >= 4) {
					a = 0;
				}
				x=0;
			}

			switch (a) {
			case 1:
				start.add(north.clone());

				break;
			case 2:
				start.add(west.clone());
				break;
			case 3:
				start.add(south.clone());
				break;
			default:
				start.add(east.clone());
				break;
			}
		}

		return start;

	}

}
