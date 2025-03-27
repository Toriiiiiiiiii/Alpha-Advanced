package net.minecraft.src;

public class PathPoint {
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	public final int hash;
	int index = -1;
	float totalPathDistance;
	float distanceToNext;
	float distanceToTarget;
	PathPoint previous;
	public boolean isFirst = false;

	public PathPoint(int var1, int var2, int var3) {
		this.xCoord = var1;
		this.yCoord = var2;
		this.zCoord = var3;
		this.hash = var1 | var2 << 10 | var3 << 20;
	}

	public float distanceTo(PathPoint var1) {
		float var2 = (float)(var1.xCoord - this.xCoord);
		float var3 = (float)(var1.yCoord - this.yCoord);
		float var4 = (float)(var1.zCoord - this.zCoord);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	public boolean equals(Object var1) {
		return ((PathPoint)var1).hash == this.hash;
	}

	public int hashCode() {
		return this.hash;
	}

	public boolean isAssigned() {
		return this.index >= 0;
	}

	public String toString() {
		return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
	}
}
