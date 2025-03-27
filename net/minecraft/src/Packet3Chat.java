package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet {
	public String message;

	public Packet3Chat() {
	}

	public Packet3Chat(String var1) {
		this.message = var1;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.message = var1.readUTF();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeUTF(this.message);
	}

	public void processPacket(NetHandler var1) {
		var1.handleChat(this);
	}

	public int getPacketSize() {
		return this.message.length();
	}
}
