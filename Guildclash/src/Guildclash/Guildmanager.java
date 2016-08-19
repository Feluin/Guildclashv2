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
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Guildclash.Language.LanguageUtil;
import Guildclash.Objects.GuildMember;
import Guildclash.Objects.Invitation;

public class Guildmanager {

	private ArrayList<Guild> guilds = new ArrayList<Guild>();
	private ArrayList<World> worlds = new ArrayList<World>();

	public Guildmanager() {
		// Erstelle Guild Ordner
		File gf = new File(Guildplugin.getGuildFolder());
		gf.mkdirs();
		File wf = new File(Guildplugin.getWorldFolder());
		wf.mkdirs();
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
										if (LanguageUtil.getLocale(other) == LanguageUtil.GERMAN) {
											other.sendMessage(ChatColor.AQUA + "Deine Einladung in das Bündnis "
													+ g.getName() + " ist abgelaufen");
										} else {
											other.sendMessage(ChatColor.AQUA + "Your invitation to the guild "
													+ g.getName() + " is expired");
										}
									}
									g.removeInvitation(i);
								}
							}
						}
					}
				}, 0, 20);
	}

	public boolean createNewGuild(String name, Player player) {
		// Erstelle Guild Welt
		File gw = new File(Guildplugin.getWorldFolder() + "/" + name);
		gw.mkdirs();
		if (!copyWorld(gw)) {
			if (LanguageUtil.getLocale(player) == LanguageUtil.GERMAN) {
				player.sendMessage(ChatColor.RED + "Ein Fehler beim erstellen des Bündnisses ist aufgetreten.");
			} else {
				player.sendMessage(ChatColor.RED + "An error occured while creating the guild");
			}
			Bukkit.getServer().getLogger().log(Level.WARNING,
					"Ein Fehler beim erstellen der Buendniswelt des Buendnisses " + name + " ist aufgetreten");
			System.out.println("Fehler beim erstellen");
			return false;
		}
		World w = Bukkit.createWorld(new WorldCreator(name));
		worlds.add(w);
		Guild g = new Guild(name, player);
		File gf = new File(Guildplugin.getGuildFolder() + "/" + name);
		gf.mkdirs();
		guilds.add(g);
		save(g);
		return true;
	}

	private boolean copyWorld(File gw) {
		try {
			ZipInputStream zis = new ZipInputStream(JavaPlugin.getPlugin(Guildplugin.class).getResource("gworld.zip"));
			ZipEntry ze = zis.getNextEntry();
			byte[] buffer = new byte[1024];
			while (ze != null) {
				String fileName = ze.getName().replaceAll("gworld/", "");
				File newFile = new File(gw + File.separator + fileName);
				if (ze.isDirectory()) {
					newFile.mkdirs();
				} else {
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void saveGuilds() {
		for (Guild g : guilds) {
			save(g);
		}
	}

	public void loadGuilds() {
		File base = new File(Guildplugin.getGuildFolder());
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

	public World getWorldByName(String name) {
		for (World w : worlds) {
			if (w.getName().equals(name)) {
				return w;
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
		File f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/main.txt");
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
			s += g.getOwner() + ",";
			s += g.getTag() + "";
			s += "}";
			os.write(s + "\n");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/members.txt");
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
		f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/ginvites.txt");
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
		f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/allies.txt");
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
		f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/enemies.txt");
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
		f = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/naps.txt");
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
		String tag = "";
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
				if (parts.length > 2) {
					name = parts[0];
					owner = UUID.fromString(parts[1]);
					tag = parts[2];
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
		Guild ret = new Guild(name, owner, members, allies, naps, enemies, ginvites, tag);
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
		File gf = new File(Guildplugin.getGuildFolder() + "/" + g.getName() + "/");
		if (gf.exists()) {
			for (File f : gf.listFiles()) {
				f.delete();
			}
			if (!gf.delete()) {
				return false;
			} else {
				guilds.remove(g);
			}
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getWorld().getName().equals(g.getName())) {
				p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			}
		}
		Bukkit.getServer().unloadWorld(g.getName(), true);
		File gw = new File(Guildplugin.getWorldFolder() + "/" + g.getName() + "/");
		if (gw.exists()) {
			for (File f : gw.listFiles()) {
				f.delete();
			}
			if (gw.delete()) {
				worlds.remove(getWorldByName(g.getName()));
				return true;
			}
		}
		return false;
	}

	public void loadWorlds() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Guild g = getguildofplayer(p.getUniqueId());
			if (g != null) {
				World gw = getWorldByName(g.getName());
				if (gw == null) {
					World w = Bukkit.createWorld(new WorldCreator(g.getName()));
					worlds.add(w);
				}
			}
		}
	}

	public boolean tagalreadyused(String tag) {
		for (Guild g : guilds) {
			if (g.getTag().equals(tag)) {
				return true;
			}
		}
		return false;
	}
}
