package net.minecraft.src;

class WorldBlockPositionType {
	int posX;
	int posY;
	int posZ;
	int acceptCountdown;
	int blockID;
	int metadata;
	final WorldClient worldClient;

	public WorldBlockPositionType(WorldClient var1, int var2, int var3, int var4, int var5, int var6) {
		this.worldClient = var1;
		this.posX = var2;
		this.posY = var3;
		this.posZ = var4;
		this.acceptCountdown = 80;
		this.blockID = var5;
		this.metadata = var6;
	}
}
