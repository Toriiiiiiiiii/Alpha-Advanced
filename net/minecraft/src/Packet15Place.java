package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15Place extends Packet {
	public int id;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;

	public Packet15Place() {
	}

	public Packet15Place(int var1, int var2, int var3, int var4, int var5) {
		this.id = var1;
		this.xPosition = var2;
		this.yPosition = var3;
		this.zPosition = var4;
		this.direction = var5;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.id = var1.readShort();
		this.xPosition = var1.readInt();
		this.yPosition = var1.read();
		this.zPosition = var1.readInt();
		this.direction = var1.read();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeShort(this.id);
		var1.writeInt(this.xPosition);
		var1.write(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.write(this.direction);
	}

	public void processPacket(NetHandler var1) {
		var1.handlePlace(this);
	}

	public int getPacketSize() {
		return 12;
	}
}
