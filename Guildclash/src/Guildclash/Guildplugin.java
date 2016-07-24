package Guildclash;

import java.util.ArrayList;
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

import Guildclash.Objects.GuildMember;
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
									p.sendMessage(ChatColor.AQUA + "Das Bündnis " + ChatColor.GRAY + guildname
											+ ChatColor.AQUA + " wurde erstellt");
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
								if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (g.hasPlayer(target.getUniqueId())) {
												if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
													if (target.isOnline()) {
														Player other = (Player) target;
														other.sendMessage(
																ChatColor.RED + "Du wurdest aus dem Bündnis geworfen");
													}
													g.removePlayer(target.getUniqueId());
													g.broadcastMessage("");
													g.broadcastMessage(ChatColor.GRAY + target.getName() + ChatColor.RED
															+ " wurde aus dem Bündnis geworfen");
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
					} else if (args[0].equalsIgnoreCase("promote")) {
						if (args.length > 1) {
							OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
							if (target != null) {
								if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (g.hasPlayer(target.getUniqueId())) {
												if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
													if (g.getPermissionLevel(p.getUniqueId()) < g
															.getPermissionLevel(target.getUniqueId()) - 1) {
														GuildMember gm = g.getMember(target.getUniqueId());
														gm.setStatus(gm.getStatus() - 1);
														String rang = "Error";
														if (gm.getStatus() == 1) {
															rang = "Offizier";
														} else if (gm.getStatus() == 2) {
															rang = "Bauer";
														}
														g.broadcastMessage("");
														g.broadcastMessage(ChatColor.GRAY + target.getName()
																+ ChatColor.AQUA + " wurde zum " + rang + " befördert");
														g.broadcastMessage("");
														return true;
													} else {
														p.sendMessage("Deine Rechte reichen dafür nicht aus");
													}
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
									p.sendMessage("Du kannst dich nicht selbst befördern");
								}
							} else {
								p.sendMessage("Dieser Spieler existiert nicht");
							}
						} else {
							p.sendMessage("/guild kick spieler");
						}
					} else if (args[0].equalsIgnoreCase("demote")) {
						if (args.length > 1) {
							OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
							if (target != null) {
								if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (g.hasPlayer(target.getUniqueId())) {
												if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
													if (g.getPermissionLevel(p.getUniqueId()) < g
															.getPermissionLevel(target.getUniqueId())) {
														GuildMember gm = g.getMember(target.getUniqueId());
														if (gm.getStatus() < 3) {
															gm.setStatus(gm.getStatus() + 1);
															String rang = "Error";
															if (gm.getStatus() == 1) {
																rang = "Offizier";
															} else if (gm.getStatus() == 2) {
																rang = "Bauer";
															} else {
																rang = "Mitglied";
															}
															g.broadcastMessage("");
															g.broadcastMessage(
																	ChatColor.GRAY + target.getName() + ChatColor.AQUA
																			+ " wurde zum " + rang + " degradiert");
															g.broadcastMessage("");
															return true;
														} else {
															p.sendMessage(
																	"Dieser Spieler kann nicht weiter degradiert werden");
														}
													} else {
														p.sendMessage("Deine Rechte reichen dafür nicht aus");
													}
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
									p.sendMessage("Du kannst dich nicht selbst degradieren");
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
								if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
									if (guildmanager.hasaguildalready(p.getUniqueId())) {
										if (!guildmanager.hasaguildalready(target.getUniqueId())) {
											Guild g = guildmanager.getguildofplayer(p.getUniqueId());
											if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
												if (!g.isInvited(target.getUniqueId())) {
													if (target.isOnline()) {
														Player other = (Player) target;
														other.sendMessage(ChatColor.AQUA + "Du wurdest in das Bündnis "
																+ g.getName() + " eingeladen");
														other.sendMessage(ChatColor.AQUA + "Schreibe /guild join "
																+ g.getName() + " um dem Bündnis beizutreten");
													}
													p.sendMessage(ChatColor.GRAY + target.getName() + ChatColor.AQUA
															+ " wurde in das Bündnis eingeladen");
													Date timeout = DateUtils.addDays(new Date(), 1);
													Invitation in = new Invitation(target.getUniqueId(), timeout);
													g.addInvitation(in);
													return true;
												} else {
													p.sendMessage("Dieser Spieler ist bereits eingeladen");
												}
											} else {
												p.sendMessage("Deine Rechte reichen dafür nicht aus");
											}
										} else {
											p.sendMessage("Dieser Spieler ist bereits in einem Bündnis");
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
								if (g != null) {
									if (g.isInvited(p.getUniqueId())) {
										g.broadcastMessage("");
										g.broadcastMessage(ChatColor.GRAY + p.getName() + ChatColor.AQUA
												+ " ist dem Bündnis beigetreten");
										g.broadcastMessage("");
										g.acceptInvitation(p.getUniqueId());
										p.sendMessage("Du bist dem Bündnis " + g.getName() + " beigetreten");
										return true;
									} else {
										p.sendMessage("Du wurdest nicht in dieses Bündnis eingeladen");
									}
								} else {
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
									g.broadcastMessage(ChatColor.DARK_RED + "Das Bündnis wurde aufgelöst");
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
					} else if (args[0].equalsIgnoreCase("leave")) {
						if (guildmanager.hasaguildalready(p.getUniqueId())) {
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							if (g.getOwner().compareTo(p.getUniqueId()) != 0) {
								g.removePlayer(p.getUniqueId());
								g.broadcastMessage("");
								g.broadcastMessage(
										ChatColor.GRAY + p.getName() + ChatColor.AQUA + " hat das Bündnis verlassen");
								g.broadcastMessage("");
								p.sendMessage("Du hast das Bündnis " + g.getName() + " verlassen");
								return true;
							} else {
								p.sendMessage("Du kannst dein Bündnis nicht verlassen");
							}
						} else {
							p.sendMessage("Du bist in keinem Bündnis");
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if (guildmanager.hasaguildalready(p.getUniqueId())) {
							Guild g = guildmanager.getguildofplayer(p.getUniqueId());
							ArrayList<UUID> officer = new ArrayList<UUID>();
							ArrayList<UUID> builder = new ArrayList<UUID>();
							ArrayList<UUID> member = new ArrayList<UUID>();
							for (int i = 0; i < g.getMembers().size(); i++) {
								GuildMember gm = g.getMembers().get(i);
								if (gm.getStatus() == 1) {
									officer.add(gm.getUUID());
								} else if (gm.getStatus() == 2) {
									builder.add(gm.getUUID());
								} else {
									member.add(gm.getUUID());
								}
							}
							p.sendMessage(ChatColor.AQUA + "-----------------------------------------------------");
							p.sendMessage(ChatColor.GOLD + "Bündnis Name: " + g.getName());
							p.sendMessage("");
							p.sendMessage(ChatColor.GREEN + "                                -- Meister --");
							OfflinePlayer opowner = Bukkit.getOfflinePlayer(g.getOwner());
							p.sendMessage(ChatColor.DARK_BLUE + opowner.getName());
							if (officer.size() > 0) {
								p.sendMessage(ChatColor.GREEN + "                               -- Offiziere --");
								String line = "";
								for (UUID u : officer) {
									OfflinePlayer opofficer = Bukkit.getOfflinePlayer(u);
									if (opofficer.getName().length() <= (52 - line.length())) {
										line += opofficer.getName() + " ";
									} else {
										p.sendMessage(line);
										line = "";
									}
								}
								if (line.length() > 0) {
									p.sendMessage(line);
								}
							}
							if (builder.size() > 0) {
								p.sendMessage(ChatColor.GREEN + "                                 -- Bauer --");
								String line = "";
								for (UUID u : builder) {
									OfflinePlayer opbuilder = Bukkit.getOfflinePlayer(u);
									if (opbuilder.getName().length() <= 52 - line.length()) {
										line += opbuilder.getName() + " ";
									} else {
										p.sendMessage(line);
										line = "";
									}
								}
								if (line.length() > 0) {
									p.sendMessage(line);
								}
							}
							if (member.size() > 0) {
								p.sendMessage(ChatColor.GREEN + "                              -- Mitglieder --");
								String line = "";
								for (UUID u : member) {
									OfflinePlayer opmember = Bukkit.getOfflinePlayer(u);
									if (opmember.getName().length() <= 52 - line.length()) {
										line += opmember.getName() + " ";
									} else {
										p.sendMessage(line);
										line = "";
									}
								}
								if (line.length() > 0) {
									p.sendMessage(line);
								}
							}
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
			sender.sendMessage("Du musst ein Spieler sein um diesen Befehl benutzen zu können");
		}
		return false;
	}
}