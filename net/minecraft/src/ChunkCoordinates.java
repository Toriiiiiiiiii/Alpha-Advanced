package net.minecraft.src;

final class ChunkCoordinates {
	public final int posX;
	public final int posZ;

	public ChunkCoordinates(int var1, int var2) {
		this.posX = var1;
		this.posZ = var2;
	}

	public boolean equals(Object var1) {
		if(!(var1 instanceof ChunkCoordinates)) {
			return false;
		} else {
			ChunkCoordinates var2 = (ChunkCoordinates)var1;
			return this.posX == var2.posX && this.posZ == var2.posZ;
		}
	}

	public int hashCode() {
		return this.posX << 16 ^ this.posZ;
	}
}
