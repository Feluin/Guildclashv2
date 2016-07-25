package Guildclash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Guildclash.Objects.GuildMember;
import Guildclash.Objects.Invitation;

public class Guildmanager {
	private ArrayList<Guild> guilds = new ArrayList<Guild>();

	public Guildmanager() {
		// Erstelle Guild Ordner
		File gf = new File(Guildplugin.guildfolder);
		gf.mkdirs();
		// Aktualisiere Guild Invites
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Guildplugin.class),
				new Runnable() {
					public void run() {
						for (Guild g : guilds) {
							for (int i = 0; i < g.getInvitations().size(); i++) {
								Invitation in = g.getInvitations().get(i);
								if (in.isExpired()) {
									OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(in.getPlayer());
									if (target.isOnline()) {
										Player other = (Player) target;
										other.sendMessage(ChatColor.AQUA + "Deine Einladung in das Bündnis "
												+ g.getName() + " ist abgelaufen");
									}
									g.removeInvitation(i);
								}
							}
						}
					}
				}, 0, 20);
	}

	public void createNewGuild(String name, Player player) {
		Guild g = new Guild(name, player);
		guilds.add(g);
		File gf = new File(Guildplugin.guildfolder + "/" + name);
		gf.mkdirs();
		save(g);
		// Erstelle Guild Welt
		// try {
		// Files.copy(Guildmanager.getBasicWorld().toPath(),
		// worldfile.toPath());
		// } catch (Exception e) {
		// }
		// WorldCreator w = new WorldCreator("w");
		// World world = Bukkit.createWorld(new
		// WorldCreator(worldfile.getName()));
	}

	public void saveGuilds() {
		for (Guild g : guilds) {
			save(g);
		}
	}

	public void loadGuilds() {
		File base = new File(Guildplugin.guildfolder);
		if (base.exists()) {
			for (File file : base.listFiles()) {
				Guild g = load(file);
				if (g != null) {
					guilds.add(g);
				}
			}
		}
	}

	public Guild getGuildByName(String name) {
		for (Guild g : guilds) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}

	public boolean existsalready(String name) {
		for (Guild g : guilds) {
			if (g.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasaguildalready(UUID uuid) {
		for (Guild g : guilds) {
			if (g.getOwner().compareTo(uuid) == 0) {
				return true;
			}
			for (GuildMember gm : g.getMembers()) {
				if (gm.getUUID().compareTo(uuid) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void save(Guild g) {
		File f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/main.txt");
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
			s += g.getName() + ",";
			s += g.getOwner() + "";
			s += "}";
			os.write(s + "\n");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/members.txt");
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
			for (GuildMember gm : g.getMembers()) {
				String s = "{";
				s += gm.getUUID() + ",";
				s += gm.getStatus();
				s += "}";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/ginvites.txt");
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
			for (Invitation in : g.getInvitations()) {
				String s = "{";
				s += in.getPlayer() + ",";
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				s += df.format(in.getDate());
				s += "}";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/allies.txt");
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
			for (String ally : g.getAllies()) {
				String s = "";
				s += ally + "";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/enemies.txt");
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
			for (String enemy : g.getEnemies()) {
				String s = "";
				s += enemy + "";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.guildfolder + "/" + g.getName() + "/naps.txt");
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
			for (String nap : g.getNaps()) {
				String s = "";
				s += nap + "";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Guild load(File file) {
		String name = "";
		UUID owner = null;
		ArrayList<GuildMember> members;
		ArrayList<String> allies;
		ArrayList<String> naps;
		ArrayList<String> enemies;
		ArrayList<Invitation> ginvites;
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/main.txt")));
			while (is.ready()) {
				String param = is.readLine();
				param = param.substring(1, param.length() - 1);
				String[] parts = param.split(",");
				if (parts.length > 1) {
					name = parts[0];
					owner = UUID.fromString(parts[1]);
				}
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/members.txt")));
			ArrayList<GuildMember> result = new ArrayList<GuildMember>();
			while (is.ready()) {
				String param = is.readLine();
				param = param.substring(1, param.length() - 1);
				String[] parts = param.split(",");
				if (parts.length > 1) {
					UUID u = UUID.fromString(parts[0]);
					result.add(new GuildMember(u, Integer.parseInt(parts[1])));
				}
			}
			members = result;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/allies.txt")));
			ArrayList<String> result = new ArrayList<String>();
			while (is.ready()) {
				String ally = is.readLine();
				result.add(ally);
			}
			allies = result;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/naps.txt")));
			ArrayList<String> result = new ArrayList<String>();
			while (is.ready()) {
				String nap = is.readLine();
				result.add(nap);
			}
			naps = result;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/enemies.txt")));
			ArrayList<String> result = new ArrayList<String>();
			while (is.ready()) {
				String enemy = is.readLine();
				result.add(enemy);
			}
			enemies = result;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/ginvites.txt")));
			ArrayList<Invitation> result = new ArrayList<Invitation>();
			while (is.ready()) {
				String param = is.readLine();
				param = param.substring(1, param.length() - 1);
				String[] parts = param.split(",");
				if (parts.length > 1) {
					UUID u = UUID.fromString(parts[0]);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Date timeout = df.parse(parts[1]);
					result.add(new Invitation(u, timeout));
				}
			}

			ginvites = result;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Guild ret = new Guild(name, owner, members, allies, naps, enemies, ginvites);
		return ret;
	}

	public Guild getguildofplayer(UUID uuid) {
		for (Guild g : guilds) {
			if (g.getOwner().equals(uuid)) {
				return g;
			}
			for (GuildMember gm : g.getMembers()) {
				if (gm.getUUID().compareTo(uuid) == 0) {
					return g;
				}
			}
		}
		return null;
	}

	public boolean removeGuild(Guild g) {
		File gf = new File(Guildplugin.guildfolder + "/" + g.getName() + "/");
		if (gf.exists()) {
			for (File f : gf.listFiles()) {
				f.delete();
			}
			if (gf.delete()) {
				guilds.remove(g);
				return true;
			}
		}
		return false;
	}
}
