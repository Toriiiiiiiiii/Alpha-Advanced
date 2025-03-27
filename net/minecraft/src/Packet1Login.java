package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet {
	public int protocolVersion;
	public String username;
	public String password;

	public Packet1Login() {
	}

	public Packet1Login(String var1, String var2, int var3) {
		this.username = var1;
		this.password = var2;
		this.protocolVersion = var3;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.protocolVersion = var1.readInt();
		this.username = var1.readUTF();
		this.password = var1.readUTF();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.protocolVersion);
		var1.writeUTF(this.username);
		var1.writeUTF(this.password);
	}

	public void processPacket(NetHandler var1) {
		var1.handleLogin(this);
	}

	public int getPacketSize() {
		return 4 + this.username.length() + this.password.length() + 4;
	}
}
