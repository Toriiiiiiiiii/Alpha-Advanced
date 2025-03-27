package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Packet51MapChunk extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int xSize;
	public int ySize;
	public int zSize;
	public byte[] chunkData;
	private int tempLength;

	public Packet51MapChunk() {
		this.isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.xPosition = var1.readInt();
		this.yPosition = var1.readShort();
		this.zPosition = var1.readInt();
		this.xSize = var1.read() + 1;
		this.ySize = var1.read() + 1;
		this.zSize = var1.read() + 1;
		int var2 = var1.readInt();
		byte[] var3 = new byte[var2];
		var1.readFully(var3);
		this.chunkData = new byte[this.xSize * this.ySize * this.zSize * 5 / 2];
		Inflater var4 = new Inflater();
		var4.setInput(var3);

		try {
			var4.inflate(this.chunkData);
		} catch (DataFormatException var9) {
			throw new IOException("Bad compressed data format");
		} finally {
			var4.end();
		}

	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.xPosition);
		var1.writeShort(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.write(this.xSize - 1);
		var1.write(this.ySize - 1);
		var1.write(this.zSize - 1);
		var1.writeInt(this.tempLength);
		var1.write(this.chunkData, 0, this.tempLength);
	}

	public void processPacket(NetHandler var1) {
		var1.handleMapChunk(this);
	}

	public int getPacketSize() {
		return 17 + this.tempLength;
	}
}
