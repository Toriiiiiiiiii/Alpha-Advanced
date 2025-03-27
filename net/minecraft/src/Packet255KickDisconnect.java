package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet {
	public String reason;

	public Packet255KickDisconnect() {
	}

	public Packet255KickDisconnect(String var1) {
		this.reason = var1;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.reason = var1.readUTF();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeUTF(this.reason);
	}

	public void processPacket(NetHandler var1) {
		var1.handleKickDisconnect(this);
	}

	public int getPacketSize() {
		return this.reason.length();
	}
}
