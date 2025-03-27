package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet59ComplexEntity extends Packet {
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public byte[] compressedNBT;
	public NBTTagCompound tileEntityNBT;

	public Packet59ComplexEntity() {
		this.isChunkDataPacket = true;
	}

	public Packet59ComplexEntity(int var1, int var2, int var3, TileEntity var4) {
		this.isChunkDataPacket = true;
		this.xCoord = var1;
		this.yCoord = var2;
		this.zCoord = var3;
		this.tileEntityNBT = new NBTTagCompound();
		var4.writeToNBT(this.tileEntityNBT);

		try {
			this.compressedNBT = CompressedStreamTools.compress(this.tileEntityNBT);
		} catch (IOException var6) {
			var6.printStackTrace();
		}

	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.xCoord = var1.readInt();
		this.yCoord = var1.readShort();
		this.zCoord = var1.readInt();
		int var2 = var1.readShort() & '\uffff';
		this.compressedNBT = new byte[var2];
		var1.readFully(this.compressedNBT);
		this.tileEntityNBT = CompressedStreamTools.decompress(this.compressedNBT);
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.xCoord);
		var1.writeShort(this.yCoord);
		var1.writeInt(this.zCoord);
		var1.writeShort((short)this.compressedNBT.length);
		var1.write(this.compressedNBT);
	}

	public void processPacket(NetHandler var1) {
		var1.handleComplexEntity(this);
	}

	public int getPacketSize() {
		return this.compressedNBT.length + 2 + 10;
	}
}
