package Guildclash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warpmanager {
	private ArrayList<Warp> warps = new ArrayList<Warp>();

	public Warpmanager() {
		File wf = new File(Guildplugin.warpfolder);
		wf.mkdirs();
	}

	public void loadWarps() {
		// TODO Auto-generated method stub
		File base = new File(Guildplugin.warpfolder);
		if (base.exists()) {
			for (File file : base.listFiles()) {
				Warp w = load(file);
				if (w != null) {
					warps.add(w);
				}
			}

		}

	}

	public void saveGuilds() {
		for (Warp w : warps) {
			save(w);
		}
	}
	public Warp getWarpByName(String name) {
		for (Warp w:warps) {
			if (w.getName().equals(name)) {
				return w;
			}
		}
		return null;
	}
	
	public boolean existsalready(String name) {
		for (Warp w:warps) {
			if (w.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void save(Warp w) {
		File f = new File(Guildplugin.guildfolder + "/" + w.getName() + "/main.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Fehler beim erstellen");
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f.getAbsolutePath())));
			String s = "{";
			s += w.getName() + ",";
			s += w.getLoc().getWorld().getName() + ",";
			s += w.getLoc().getBlockX() + ",";
			s += w.getLoc().getBlockY() + ",";
			s += w.getLoc().getBlockZ() + ",";
			s += w.getPermission() + "";
			s += "}";
			os.write(s + "\n");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Warp load(File file) {
		String name = "";
		Location loc = null;
		int permission = 100;
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/main.txt")));
			while (is.ready()) {
				String param = is.readLine();
				param = param.substring(1, param.length() - 1);
				String[] parts = param.split(",");
				if (parts.length > 5) {
					name = parts[0];
					loc = new Location(Bukkit.getWorld(parts[1]), Integer.getInteger(parts[2]),
							Integer.getInteger(parts[3]), Integer.getInteger(parts[4]));
					permission = Integer.getInteger(parts[6]);
				}
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Warp " + file.getName() + " konnte nicht geladen werden!");
			return null;
		}

		return new Warp(name, loc, permission);
	}
	
	public void createNewWarp(String name,Location loc,int permission) {
		Warp warp=new Warp(name, loc, permission);
		warps.add(warp);
		File wf = new File(Guildplugin.warpfolder + "/" + name);
		wf.mkdirs();
		save(warp);

	}
}
