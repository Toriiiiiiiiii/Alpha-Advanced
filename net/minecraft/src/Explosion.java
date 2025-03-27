package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Explosion {
	public void doExplosion(World var1, Entity var2, double var3, double var5, double var7, float var9) {
		var1.playSoundEffect(var3, var5, var7, "random.explode", 4.0F, (1.0F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.2F) * 0.7F);
		HashSet var10 = new HashSet();
		float var11 = var9;
		byte var12 = 16;

		int var13;
		int var14;
		int var15;
		double var25;
		double var27;
		double var29;
		for(var13 = 0; var13 < var12; ++var13) {
			for(var14 = 0; var14 < var12; ++var14) {
				for(var15 = 0; var15 < var12; ++var15) {
					if(var13 == 0 || var13 == var12 - 1 || var14 == 0 || var14 == var12 - 1 || var15 == 0 || var15 == var12 - 1) {
						double var16 = (double)((float)var13 / ((float)var12 - 1.0F) * 2.0F - 1.0F);
						double var18 = (double)((float)var14 / ((float)var12 - 1.0F) * 2.0F - 1.0F);
						double var20 = (double)((float)var15 / ((float)var12 - 1.0F) * 2.0F - 1.0F);
						double var22 = Math.sqrt(var16 * var16 + var18 * var18 + var20 * var20);
						var16 /= var22;
						var18 /= var22;
						var20 /= var22;
						float var24 = var9 * (0.7F + var1.rand.nextFloat() * 0.6F);
						var25 = var3;
						var27 = var5;
						var29 = var7;

						for(float var31 = 0.3F; var24 > 0.0F; var24 -= var31 * (12.0F / 16.0F)) {
							int var32 = MathHelper.floor_double(var25);
							int var33 = MathHelper.floor_double(var27);
							int var34 = MathHelper.floor_double(var29);
							int var35 = var1.getBlockId(var32, var33, var34);
							if(var35 > 0) {
								var24 -= (Block.blocksList[var35].getExplosionResistance(var2) + 0.3F) * var31;
							}

							if(var24 > 0.0F) {
								var10.add(new ChunkPosition(var32, var33, var34));
							}

							var25 += var16 * (double)var31;
							var27 += var18 * (double)var31;
							var29 += var20 * (double)var31;
						}
					}
				}
			}
		}

		var9 *= 2.0F;
		var13 = MathHelper.floor_double(var3 - (double)var9 - 1.0D);
		var14 = MathHelper.floor_double(var3 + (double)var9 + 1.0D);
		var15 = MathHelper.floor_double(var5 - (double)var9 - 1.0D);
		int var45 = MathHelper.floor_double(var5 + (double)var9 + 1.0D);
		int var17 = MathHelper.floor_double(var7 - (double)var9 - 1.0D);
		int var46 = MathHelper.floor_double(var7 + (double)var9 + 1.0D);
		List var19 = var1.getEntitiesWithinAABBExcludingEntity(var2, AxisAlignedBB.getBoundingBoxFromPool((double)var13, (double)var15, (double)var17, (double)var14, (double)var45, (double)var46));
		Vec3D var47 = Vec3D.createVector(var3, var5, var7);

		double var55;
		double var56;
		double var57;
		for(int var21 = 0; var21 < var19.size(); ++var21) {
			Entity var49 = (Entity)var19.get(var21);
			double var23 = var49.getDistance(var3, var5, var7) / (double)var9;
			if(var23 <= 1.0D) {
				var25 = var49.posX - var3;
				var27 = var49.posY - var5;
				var29 = var49.posZ - var7;
				var55 = (double)MathHelper.sqrt_double(var25 * var25 + var27 * var27 + var29 * var29);
				var25 /= var55;
				var27 /= var55;
				var29 /= var55;
				var56 = (double)var1.getBlockDensity(var47, var49.boundingBox);
				var57 = (1.0D - var23) * var56;
				var49.attackEntityFrom(var2, (int)((var57 * var57 + var57) / 2.0D * 8.0D * (double)var9 + 1.0D));
				var49.motionX += var25 * var57;
				var49.motionY += var27 * var57;
				var49.motionZ += var29 * var57;
			}
		}

		var9 = var11;
		ArrayList var48 = new ArrayList();
		var48.addAll(var10);

		for(int var50 = var48.size() - 1; var50 >= 0; --var50) {
			ChunkPosition var51 = (ChunkPosition)var48.get(var50);
			int var52 = var51.x;
			int var53 = var51.y;
			int var26 = var51.z;
			int var54 = var1.getBlockId(var52, var53, var26);

			for(int var28 = 0; var28 < 1; ++var28) {
				var29 = (double)((float)var52 + var1.rand.nextFloat());
				var55 = (double)((float)var53 + var1.rand.nextFloat());
				var56 = (double)((float)var26 + var1.rand.nextFloat());
				var57 = var29 - var3;
				double var37 = var55 - var5;
				double var39 = var56 - var7;
				double var41 = (double)MathHelper.sqrt_double(var57 * var57 + var37 * var37 + var39 * var39);
				var57 /= var41;
				var37 /= var41;
				var39 /= var41;
				double var43 = 0.5D / (var41 / (double)var9 + 0.1D);
				var43 *= (double)(var1.rand.nextFloat() * var1.rand.nextFloat() + 0.3F);
				var57 *= var43;
				var37 *= var43;
				var39 *= var43;
				var1.spawnParticle("explode", (var29 + var3 * 1.0D) / 2.0D, (var55 + var5 * 1.0D) / 2.0D, (var56 + var7 * 1.0D) / 2.0D, var57, var37, var39);
				var1.spawnParticle("smoke", var29, var55, var56, var57, var37, var39);
			}

			if(var54 > 0) {
				Block.blocksList[var54].dropBlockAsItemWithChance(var1, var52, var53, var26, var1.getBlockMetadata(var52, var53, var26), 0.3F);
				var1.setBlockWithNotify(var52, var53, var26, 0);
				Block.blocksList[var54].onBlockDestroyedByExplosion(var1, var52, var53, var26);
			}
		}

	}
}
