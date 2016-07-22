package Guildclash;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Guildclash.Objects.Invitation;

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

	@SuppressWarnings("deprecation")
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
									return true;
								} else {
									p.sendMessage("Dieser Name ist bereits vergeben");
								}
							} else {
								p.sendMessage("Du bist bereits in einem Bündnis");
							}
						} else {
							p.sendMessage("/guild create name");
						}
					} else if (args[0].equalsIgnoreCase("kick")) {
						if (args.length > 1) {
							OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
							if (target != null) {
								if (target.getUniqueId().compareTo(p.getUniqueId()) == 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (g.hasPlayer(target.getUniqueId())) {
												if (g.getPermissionLevel(p.getUniqueId()) > g
														.getPermissionLevel(target.getUniqueId())) {
													if (target.isOnline()) {
														Player other = (Player) target;
														other.sendMessage("Du wurdest aus dem Bündnis geworfen");
													}
													g.removePlayer(target.getUniqueId());
													g.broadcastMessage("");
													g.broadcastMessage(ChatColor.GRAY + target.getName()
															+ ChatColor.WHITE + " wurde aus dem Bündnis geworfen");
													g.broadcastMessage("");
													return true;
												} else {
													p.sendMessage("Deine Rechte reichen dafür nicht aus");
												}
											} else {
												p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
											}
										} else {
											p.sendMessage("Dieser Spieler ist in keinem Bündnis");
										}
									} else {
										p.sendMessage("Du bist in keinem Bündnis");
									}
								} else {
									p.sendMessage("Du kannst dich nicht selbst kicken");
								}
							} else {
								p.sendMessage("Dieser Spieler existiert nicht");
							}
						} else {
							p.sendMessage("/guild kick spieler");
						}
					} else if (args[0].equalsIgnoreCase("invite")) {
						if (args.length > 1) {
							OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
							if (target != null) {
								if (target.getUniqueId().compareTo(p.getUniqueId()) == 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (!guildmanager.hasaguildalready(target.getUniqueId())) {
												if (g.getPermissionLevel(p.getUniqueId()) == 0) {
													if (target.isOnline()) {
														Player other = (Player) target;
														other.sendMessage("Du wurdest in das Bündnis " + g.getName()
																+ " eingeladen");
														other.sendMessage("Schreibe /guild join " + g.getName()
																+ " um dem Bündnis beizutreten");
													}
													Date timeout = DateUtils.addDays(new Date(), 1);
													Invitation in = new Invitation(target.getUniqueId(), timeout);
													g.addInvitation(in);
													return true;
												} else {
													p.sendMessage("Deine Rechte reichen dafür nicht aus");
												}
											} else {
												p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
											}
										} else {
											p.sendMessage("Dieser Spieler ist in keinem Bündnis");
										}
									} else {
										p.sendMessage("Du bist in keinem Bündnis");
									}
								} else {
									p.sendMessage("Du kannst dich nicht selbst einladen");
								}
							} else {
								p.sendMessage("Dieser Spieler existiert nicht");
							}
						} else {
							p.sendMessage("/guild invite spieler");
						}
					} else if (args[0].equalsIgnoreCase("join")) {
						if (args.length > 1) {
							String name = args[1];
							if (!guildmanager.hasaguildalready(p.getUniqueId())) {
								Guild g = guildmanager.getGuildByName(name);
								if (g!=null) {
									if(g.isInvited(p.getUniqueId())){
										g.addMember(p.getUniqueId());
									}
									else{
										p.sendMessage("Du wurdest nicht in dieses Bündnis eingeladen");
									}
								}
								else{
									p.sendMessage("Dieses Bündnis existiert nicht");
								}
								return true;
							} else {
								p.sendMessage("Du bist bereits in einem Bündnis");
							}
						} else {
							p.sendMessage("/guild join name");
						}
					} else if (args[0].equalsIgnoreCase("delete")) {
						if (guildmanager.hasaguildalready(p.getUniqueId())) {
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							if (g.getPermissionLevel(p.getUniqueId()) == 0) {
								if (guildmanager.removeGuild(g)) {
									g.broadcastMessage("");
									g.broadcastMessage(ChatColor.RED + "Das Bündnis wurde aufgelöst");
									g.broadcastMessage("");
									return true;
								} else {
									for (OfflinePlayer op : Bukkit.getOperators()) {
										if (op.isOnline()) {
											Player operator = op.getPlayer();
											operator.sendMessage(
													"Ein Fehler beim Löschen von Bündnisdaten ist aufgetreten");
										}
									}
								}
							} else {
								p.sendMessage("Deine Berechtigung reicht dafür nicht aus");
							}
						} else {
							p.sendMessage("Du bist in keinem Bündnis");
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if (guildmanager.hasaguildalready(p.getUniqueId())) {
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							p.sendMessage(ChatColor.AQUA + "-----------------------------------------------------");
							p.sendMessage(ChatColor.GOLD + "Bündnis Name: " + g.getName());
							p.sendMessage("");
							p.sendMessage(ChatColor.GREEN + "                                -- Meister --");
							OfflinePlayer opowner = Bukkit.getOfflinePlayer(g.getOwner());
							p.sendMessage(ChatColor.DARK_BLUE + opowner.getName());
							p.sendMessage(ChatColor.GREEN + "                               -- Mitglieder --");
							String buffer[] = new String[g.getMembers().size() % 5];
							for (int i = 0; i < g.getMembers().size(); i++) {
								UUID u = g.getMembers().get(i);
								System.out.println(u);
								OfflinePlayer opmember = Bukkit.getOfflinePlayer(u);
								System.out.println(opmember.getName());
								buffer[i % 5] += opmember.getName() + " ";
							}
							p.sendMessage(buffer);
							p.sendMessage(ChatColor.AQUA + "-----------------------------------------------------");
							return true;
						} else {
							p.sendMessage("Du bist in keinem Bündnis");
						}
					} else {
						p.sendMessage("/guild help seite");
					}
				} else {
					p.sendMessage("/guild help seite");
				}
			}
		} else {
			sender.sendMessage("Du musst ein Spieler sein um das Kommando zu nutzen");
		}
		return false;
	}
}