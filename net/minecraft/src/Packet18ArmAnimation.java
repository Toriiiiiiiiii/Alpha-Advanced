package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet18ArmAnimation extends Packet {
	public int entityId;
	public int animate;

	public Packet18ArmAnimation() {
	}

	public Packet18ArmAnimation(Entity var1, int var2) {
		this.entityId = var1.entityID;
		this.animate = var2;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.animate = var1.readByte();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeByte(this.animate);
	}

	public void processPacket(NetHandler var1) {
		var1.handleArmAnimation(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
