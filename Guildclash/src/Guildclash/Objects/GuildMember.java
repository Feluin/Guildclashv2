package Guildclash.Objects;

import java.util.UUID;

public class GuildMember {
	private UUID u;
	private int status;

	public GuildMember(UUID uuid, int status) {
		this.u = uuid;
		this.status = status;
	}

	public UUID getUUID() {
		return u;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
