package Guildclash.Objects;

import java.util.Date;
import java.util.UUID;

public class Invitation {
	private UUID p;
	private Date timeout;

	public Invitation(UUID p, Date timeout) {
		this.timeout = timeout;
		this.p = p;
	}

	public boolean isExpired() {
		if (timeout.after(new Date())) {
			return true;
		}
		return false;
	}

	public UUID getPlayer() {
		return this.p;
	}

	public Date getDate() {
		return this.timeout;
	}
}
