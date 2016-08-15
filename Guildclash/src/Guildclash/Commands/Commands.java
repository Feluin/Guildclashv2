package Guildclash.Commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import Guildclash.Guild;
import Guildclash.Guildmanager;
import Guildclash.Guildplugin;
import Guildclash.Language.LanguageUtil;
import Guildclash.Objects.GuildMember;
import Guildclash.Objects.Invitation;

public class Commands {

	public static boolean doGuildCreateCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			String guildname = args[1];
			if (!gmanager.hasaguildalready(p.getUniqueId())) {
				if (!gmanager.existsalready(guildname)) {
					if (gmanager.createNewGuild(guildname, p)) {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage(ChatColor.AQUA + "Das Bündnis " + ChatColor.GRAY + guildname + ChatColor.AQUA
									+ " wurde erstellt");
						} else {
							p.sendMessage(ChatColor.AQUA + "The guild " + ChatColor.GRAY + guildname + ChatColor.AQUA
									+ " was succesfully founded");
						}
					}
					return true;
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Dieser Name ist bereits vergeben");
					} else {
						p.sendMessage("This name is already used");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Du bist bereits in einem Bündnis");
				} else {
					p.sendMessage("You are already in a guild");
				}
			}
		} else {
			p.sendMessage("/guild create name");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean doGuildKickCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
			if (target != null) {
				if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
					if (gmanager.hasaguildalready(p.getUniqueId())) {
						if (gmanager.hasaguildalready(target.getUniqueId())) {
							Guild g = gmanager.getguildofplayer(p.getUniqueId());
							if (g.hasPlayer(target.getUniqueId())) {
								if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
									if (target.isOnline()) {
										Player other = (Player) target;
										if (LanguageUtil.getLocale(other) == LanguageUtil.GERMAN) {
											other.sendMessage(ChatColor.RED + "Du wurdest aus dem Bündnis geworfen");
										} else {
											other.sendMessage(ChatColor.RED + "You were kicked from the guild");
										}
									}
									g.removePlayer(target.getUniqueId());
									g.broadcastMessage("");
									g.broadcastSpecialMessage(0, target.getName(), 0);
									g.broadcastMessage("");
									return true;
								} else {
									if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
										p.sendMessage("Deine Rechte reichen dafür nicht aus");
									} else {
										p.sendMessage("You do not have permission to do that");
									}
								}
							} else {
								if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
									p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
								} else {
									p.sendMessage("This player is not in your guild");
								}
							}
						} else {
							if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
								p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
							} else {
								p.sendMessage("This player is not in your guild");
							}
						}
					} else {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du bist in keinem Bündnis");
						} else {
							p.sendMessage("You are not in a guild");
						}
					}
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Du kannst dich nicht selbst kicken");
					} else {
						p.sendMessage("You can not kick yourself from the guild");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Dieser Spieler existiert nicht");
				} else {
					p.sendMessage("This player does not exist");
				}
			}
		} else {
			p.sendMessage("/guild kick player");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean doGuildPromoteCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
			if (target != null) {
				if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
					if (gmanager.hasaguildalready(p.getUniqueId())) {
						if (gmanager.hasaguildalready(target.getUniqueId())) {
							Guild g = gmanager.getguildofplayer(p.getUniqueId());
							if (g.hasPlayer(target.getUniqueId())) {
								if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
									if (g.getPermissionLevel(
											p.getUniqueId()) < g.getPermissionLevel(target.getUniqueId()) - 1) {
										GuildMember gm = g.getMember(target.getUniqueId());
										gm.setStatus(gm.getStatus() - 1);
										g.broadcastMessage("");
										g.broadcastSpecialMessage(1, target.getName(), gm.getStatus());
										g.broadcastMessage("");
										return true;
									} else {
										if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
											p.sendMessage("Deine Rechte reichen dafür nicht aus");
										} else {
											p.sendMessage("You do not have permission to do that");
										}
									}
								} else {
									if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
										p.sendMessage("Deine Rechte reichen dafür nicht aus");
									} else {
										p.sendMessage("You do not have permission to do that");
									}
								}
							} else {
								if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
									p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
								} else {
									p.sendMessage("This player is not in your guild");
								}
							}
						} else {
							if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
								p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
							} else {
								p.sendMessage("This player is not in your guild");
							}
						}
					} else {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du bist in keinem Bündnis");
						} else {
							p.sendMessage("You are not in a guild");
						}
					}
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Du kannst dich nicht selbst befördern");
					} else {
						p.sendMessage("You can not promote yourself");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Dieser Spieler existiert nicht");
				} else {
					p.sendMessage("This player does not exist");
				}
			}
		} else {
			p.sendMessage("/guild promote player");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean doGuildDemoteCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
			if (target != null) {
				if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
					if (gmanager.hasaguildalready(p.getUniqueId())) {
						if (gmanager.hasaguildalready(target.getUniqueId())) {
							Guild g = gmanager.getguildofplayer(p.getUniqueId());
							if (g.hasPlayer(target.getUniqueId())) {
								if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
									if (g.getPermissionLevel(p.getUniqueId()) < g
											.getPermissionLevel(target.getUniqueId())) {
										GuildMember gm = g.getMember(target.getUniqueId());
										if (gm.getStatus() < 3) {
											gm.setStatus(gm.getStatus() + 1);
											g.broadcastMessage("");
											g.broadcastSpecialMessage(2, target.getName(), gm.getStatus());
											g.broadcastMessage("");
											return true;
										} else {
											if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
												p.sendMessage("Dieser Spieler kann nicht weiter degradiert werden");
											} else {
												p.sendMessage("This player can not be demoted anymore");
											}
										}
									} else {
										if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
											p.sendMessage("Deine Rechte reichen dafür nicht aus");
										} else {
											p.sendMessage("You do not have permission to do that");
										}
									}
								} else {
									if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
										p.sendMessage("Deine Rechte reichen dafür nicht aus");
									} else {
										p.sendMessage("You do not have permission to do that");
									}
								}
							} else {
								if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
									p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
								} else {
									p.sendMessage("This player is not in your guild");
								}
							}
						} else {
							if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
								p.sendMessage("Dieser Spieler ist nicht im gleichem Bündnis");
							} else {
								p.sendMessage("This player is not in your guild");
							}
						}
					} else {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du bist in keinem Bündnis");
						} else {
							p.sendMessage("You are not in a guild");
						}
					}
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Du kannst dich nicht selbst degradieren");
					} else {
						p.sendMessage("You can not demote yourself");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Dieser Spieler existiert nicht");
				} else {
					p.sendMessage("This player does not exist");
				}
			}
		} else {
			p.sendMessage("/guild demote player");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean doGuildInviteCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
			if (target != null) {
				if (target.getUniqueId().compareTo(p.getUniqueId()) != 0) {
					if (gmanager.hasaguildalready(p.getUniqueId())) {
						if (!gmanager.hasaguildalready(target.getUniqueId())) {
							Guild g = gmanager.getguildofplayer(p.getUniqueId());
							if (g.getPermissionLevel(p.getUniqueId()) <= 1) {
								if (!g.isInvited(target.getUniqueId())) {
									if (target.isOnline()) {
										Player other = (Player) target;
										if (LanguageUtil.getLocale(other) == LanguageUtil.GERMAN) {
											other.sendMessage(ChatColor.AQUA + "Du wurdest in das Bündnis "
													+ g.getName() + " eingeladen");
											other.sendMessage(ChatColor.AQUA + "Schreibe /guild join " + g.getName()
													+ " um dem Bündnis beizutreten");
										} else {
											other.sendMessage(
													ChatColor.AQUA + "You were invited to the guild " + g.getName());
											other.sendMessage(ChatColor.AQUA + "Write /guild join " + g.getName()
													+ " to join the guild");
										}
									}
									if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
										p.sendMessage(ChatColor.GRAY + target.getName() + ChatColor.AQUA
												+ " wurde in das Bündnis eingeladen");
									} else {
										p.sendMessage(ChatColor.GRAY + target.getName() + ChatColor.AQUA
												+ " was invited to the guild");
									}
									Date timeout = DateUtils.addDays(new Date(), 1);
									Invitation in = new Invitation(target.getUniqueId(), timeout);
									g.addInvitation(in);
									return true;
								} else {
									if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
										p.sendMessage("Dieser Spieler ist bereits eingeladen");
									} else {
										p.sendMessage("This player is already invited to the guild");
									}
								}
							} else {
								if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
									p.sendMessage("Deine Rechte reichen dafür nicht aus");
								} else {
									p.sendMessage("You do not have permission to do that");
								}
							}
						} else {
							if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
								p.sendMessage("Dieser Spieler ist bereits in einem Bündnis");
							} else {
								p.sendMessage("This player is already in a guild");
							}
						}
					} else {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du bist in keinem Bündnis");
						} else {
							p.sendMessage("You are not in a guild");
						}
					}
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Du kannst dich nicht selbst einladen");
					} else {
						p.sendMessage("You can not invite yourself to your guild");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Dieser Spieler existiert nicht");
				} else {
					p.sendMessage("This player does not exist");
				}
			}
		} else {
			p.sendMessage("/guild invite player");
		}
		return false;
	}

	public static boolean doGuildJoinCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (args.length > 1) {
			String name = args[1];
			if (!gmanager.hasaguildalready(p.getUniqueId())) {
				Guild g = gmanager.getGuildByName(name);
				if (g != null) {
					if (g.isInvited(p.getUniqueId())) {
						g.broadcastMessage("");
						g.broadcastSpecialMessage(3, p.getName(), 0);
						g.broadcastMessage("");
						g.acceptInvitation(p.getUniqueId());
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du bist dem Bündnis " + g.getName() + " beigetreten");
						} else {
							p.sendMessage("You joined the guild " + g.getName());
						}

						return true;
					} else {
						if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
							p.sendMessage("Du wurdest nicht in dieses Bündnis eingeladen");
						} else {
							p.sendMessage("You are not invited to this guild");
						}
					}
				} else {
					if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
						p.sendMessage("Dieses Bündnis existiert nicht");
					} else {
						p.sendMessage("This guild does not exist");
					}
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Du bist bereits in einem Bündnis");
				} else {
					p.sendMessage("You are already in a guild");
				}
			}
		} else {
			p.sendMessage("/guild join name");
		}
		return false;
	}

	public static boolean doGuildDeleteCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (gmanager.hasaguildalready(p.getUniqueId())) {
			Guild g = gmanager.getguildofplayer(p.getUniqueId());
			if (g.getPermissionLevel(p.getUniqueId()) == 0) {
				if (gmanager.removeGuild(g)) {
					g.broadcastMessage("");
					g.broadcastSpecialMessage(4, "", 0);
					g.broadcastMessage("");
					return true;
				} else {
					for (OfflinePlayer op : Bukkit.getOperators()) {
						if (op.isOnline()) {
							Player operator = op.getPlayer();
							if (LanguageUtil.getLocale(operator) == LanguageUtil.GERMAN) {
								operator.sendMessage("Ein Fehler ist beim Löschen von Bündnisdaten aufgetreten");
							} else {
								operator.sendMessage("An error occured when deleting guild data");
							}
						}
					}
					Bukkit.getServer().getConsoleSender().sendMessage(
							"Ein Fehler ist beim loeschen der Daten des Buendnisses " + g.getName() + " aufgetreten");
				}
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Deine Rechte reichen dafür nicht aus");
				} else {
					p.sendMessage("You do not have permission to do that");
				}
			}
		} else {
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage("Du bist in keinem Bündnis");
			} else {
				p.sendMessage("You are not in a guild");
			}
		}
		return false;
	}

	public static boolean doGuildLeaveCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (gmanager.hasaguildalready(p.getUniqueId())) {
			Guild g = gmanager.getguildofplayer(p.getUniqueId());
			if (g.getOwner().compareTo(p.getUniqueId()) != 0) {
				g.removePlayer(p.getUniqueId());
				g.broadcastMessage("");
				g.broadcastSpecialMessage(5, p.getName(), 0);
				g.broadcastMessage("");
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Du hast dein Bündnis verlassen");
				} else {
					p.sendMessage("You left your guild");
				}
				return true;
			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Du kannst dein Bündnis nicht verlassen");
				} else {
					p.sendMessage("You can not leave your guild");
				}
			}
		} else {
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage("Du bist in keinem Bündnis");
			} else {
				p.sendMessage("You are not in a guild");
			}
		}
		return false;
	}

	public static boolean doGuildInfoCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		Guild g = null;
		if (args.length > 1) {
			String name = args[1];
			g = gmanager.getGuildByName(name);
			if (g == null) {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Dieses Bündnis existiert nicht");
				} else {
					p.sendMessage("This guild does not exist");
				}
			}
		} else {
			if (gmanager.hasaguildalready(p.getUniqueId())) {
				g = gmanager.getguildofplayer(p.getUniqueId());

			} else {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage("Du bist in keinem Bündnis");
				} else {
					p.sendMessage("You are not in a guild");
				}
			}
		}
		if (g != null) {
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
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage(ChatColor.GOLD + "Bündnis Name: " + g.getName());
			} else {
				p.sendMessage(ChatColor.GOLD + "Guild name: " + g.getName());
			}
			p.sendMessage("");
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage(ChatColor.GREEN + "                                -- Meister --");
			} else {
				p.sendMessage(ChatColor.GREEN + "                                 -- Owner --");
			}
			p.sendMessage("");
			OfflinePlayer opowner = Bukkit.getOfflinePlayer(g.getOwner());
			p.sendMessage(ChatColor.DARK_BLUE + opowner.getName());
			if (officer.size() > 0) {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage(ChatColor.GREEN + "                               -- Offiziere --");
				} else {
					p.sendMessage(ChatColor.GREEN + "                               -- Officers --");
				}
				p.sendMessage("");
				String line = "";
				for (UUID u : officer) {
					OfflinePlayer opofficer = Bukkit.getOfflinePlayer(u);
					if ((opofficer.getName().length() + 1) <= (53 - line.length())) {
						line += opofficer.getName() + " ";
					} else {
						p.sendMessage(line);
						line = opofficer.getName() + " ";
					}
				}
				if (line.length() > 0) {
					p.sendMessage(line);
				}
			}
			if (builder.size() > 0) {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage(ChatColor.GREEN + "                              -- Bauarbeiter --");
				} else {
					p.sendMessage(ChatColor.GREEN + "                                -- Builder --");
				}
				p.sendMessage("");
				String line = "";
				for (UUID u : builder) {
					OfflinePlayer opbuilder = Bukkit.getOfflinePlayer(u);
					if ((opbuilder.getName().length() + 1) <= (53 - line.length())) {
						line += opbuilder.getName() + " ";
					} else {
						p.sendMessage(line);
						line = opbuilder.getName() + " ";
					}
				}
				if (line.length() > 0) {
					p.sendMessage(line);
				}
			}
			if (member.size() > 0) {
				if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
					p.sendMessage(ChatColor.GREEN + "                              -- Mitglieder --");
				} else {
					p.sendMessage(ChatColor.GREEN + "                               -- Members --");
				}
				p.sendMessage("");
				String line = "";
				for (UUID u : member) {
					OfflinePlayer opmember = Bukkit.getOfflinePlayer(u);
					if ((opmember.getName().length() + 1) <= (53 - line.length())) {
						line += opmember.getName() + " ";
					} else {
						p.sendMessage(line);
						line = opmember.getName() + " ";
					}
				}
				if (line.length() > 0) {
					p.sendMessage(line);
				}
			}
			p.sendMessage(ChatColor.AQUA + "-----------------------------------------------------");
			return true;
		}
		return false;
	}

	public static boolean doGuildHomeCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (gmanager.hasaguildalready(p.getUniqueId())) {
			Guild g = gmanager.getguildofplayer(p.getUniqueId());
			World gw = gmanager.getWorldByName(g.getName());
			if (gw != null) {
				Location spawn = new Location(gw, -161, 81, -16, 270, 0);
				p.teleport(spawn);
			} else {
				p.sendMessage("Dein Bündnis hat keine eigene Welt");
			}
		} else {
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage("Du bist in keinem Bündnis");
			} else {
				p.sendMessage("You are not in a guild");
			}
		}
		return false;
	}

	public static boolean doGuildChatCommand(Player p, String[] args) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		if (gmanager.hasaguildalready(p.getUniqueId())) {
			if (args.length > 0) {
				Guild g = gmanager.getguildofplayer(p.getUniqueId());
				String message = "";
				for (int i = 0; i < args.length; i++) {
					message += " "+args[i];
				}
				g.broadcastMessage(ChatColor.AQUA + "Guild > " + ChatColor.RESET + p.getDisplayName() + ChatColor.RESET
						+ ":" + message);
				return true;
			}
		} else {
			if (LanguageUtil.getLocale(p) == LanguageUtil.GERMAN) {
				p.sendMessage("Du bist in keinem Bündnis");
			} else {
				p.sendMessage("You are not in a guild");
			}
		}
		return false;
	}
}
