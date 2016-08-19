package Guildclash;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventManager implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Guildmanager gmanager = Guildplugin.getGuildManager();
		Player p = event.getPlayer();
		if (gmanager.hasaguildalready(p.getUniqueId())) {
			Guild g = gmanager.getguildofplayer(p.getUniqueId());
			if (!g.getTag().equals("")) {
				event.setFormat("<[" + g.getTag() + "] %1$s> %2$s");
			}
		}
	}
}
