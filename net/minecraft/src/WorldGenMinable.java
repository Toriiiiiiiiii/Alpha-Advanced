package net.minecraft.src;

import java.util.Random;

public class WorldGenMinable extends WorldGenerator {
	private int minableBlockId;
	private int numberOfBlocks;

	public WorldGenMinable(int var1, int var2) {
		this.minableBlockId = var1;
		this.numberOfBlocks = var2;
	}

	public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
		float var6 = var2.nextFloat() * (float)Math.PI;
		double var7 = (double)((float)(var3 + 8) + MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0F);
		double var9 = (double)((float)(var3 + 8) - MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0F);
		double var11 = (double)((float)(var5 + 8) + MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0F);
		double var13 = (double)((float)(var5 + 8) - MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0F);
		double var15 = (double)(var4 + var2.nextInt(3) + 2);
		double var17 = (double)(var4 + var2.nextInt(3) + 2);

		for(int var19 = 0; var19 <= this.numberOfBlocks; ++var19) {
			double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.numberOfBlocks;
			double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.numberOfBlocks;
			double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.numberOfBlocks;
			double var26 = var2.nextDouble() * (double)this.numberOfBlocks / 16.0D;
			double var28 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
			double var30 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * var26 + 1.0D;

			for(int var32 = (int)(var20 - var28 / 2.0D); var32 <= (int)(var20 + var28 / 2.0D); ++var32) {
				for(int var33 = (int)(var22 - var30 / 2.0D); var33 <= (int)(var22 + var30 / 2.0D); ++var33) {
					for(int var34 = (int)(var24 - var28 / 2.0D); var34 <= (int)(var24 + var28 / 2.0D); ++var34) {
						double var35 = ((double)var32 + 0.5D - var20) / (var28 / 2.0D);
						double var37 = ((double)var33 + 0.5D - var22) / (var30 / 2.0D);
						double var39 = ((double)var34 + 0.5D - var24) / (var28 / 2.0D);
						if(var35 * var35 + var37 * var37 + var39 * var39 < 1.0D && var1.getBlockId(var32, var33, var34) == Block.stone.blockID) {
							var1.setBlock(var32, var33, var34, this.minableBlockId);
						}
					}
				}
			}
		}

		return true;
	}
}
