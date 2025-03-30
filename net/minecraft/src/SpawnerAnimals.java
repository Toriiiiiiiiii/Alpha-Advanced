package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SpawnerAnimals {
	private int maxSpawns;
	private Class entityType;
	private Class[] entities;
	private Set eligibleChunksForSpawning = new HashSet();

	public SpawnerAnimals(int var1, Class var2, Class[] var3) {
		this.maxSpawns = var1;
		this.entityType = var2;
		this.entities = var3;
	}

	public void onUpdate(World var1) {
		int var2 = var1.countEntities(this.entityType);
		if(var2 < this.maxSpawns) {
			for(int var3 = 0; var3 < 3; ++var3) {
				this.performSpawning(var1, 1, (IProgressUpdate)null);
			}
		}

	}

	protected ChunkPosition getRandomSpawningPointInChunk(World var1, int var2, int var3) {
		Chunk chunk = var1.getChunkFromChunkCoords(var2, var3);
		int var4 = var2 * 16 + var1.rand.nextInt(16);
		int var6 = var3 * 16 + var1.rand.nextInt(16);
		int var5 = var1.rand.nextInt(chunk == null ? 256 : (chunk.blocks2 == null ? 127 : 255));
		return new ChunkPosition(var4, var5, var6);
	}

	private int performSpawning(World var1, int var2, IProgressUpdate var3) {
		this.eligibleChunksForSpawning.clear();

		int var4;
		int var7;
		int var9;
		int var10;
		for(var4 = 0; var4 < var1.playerEntities.size(); ++var4) {
			EntityPlayer var5 = (EntityPlayer)var1.playerEntities.get(var4);
			int var6 = MathHelper.floor_double(var5.posX / 16.0D);
			var7 = MathHelper.floor_double(var5.posZ / 16.0D);
			byte var8 = 4;

			for(var9 = -var8; var9 <= var8; ++var9) {
				for(var10 = -var8; var10 <= var8; ++var10) {
					this.eligibleChunksForSpawning.add(new ChunkCoordIntPair(var9 + var6, var10 + var7));
				}
			}
		}

		var4 = 0;
		Iterator var26 = this.eligibleChunksForSpawning.iterator();

		while(true) {
			ChunkCoordIntPair var27;
			do {
				if(!var26.hasNext()) {
					return var4;
				}

				var27 = (ChunkCoordIntPair)var26.next();
			} while(var1.rand.nextInt(10) != 0);

			var7 = var1.rand.nextInt(this.entities.length);
			ChunkPosition var28 = this.getRandomSpawningPointInChunk(var1, var27.chunkXPos, var27.chunkZPos);
			var9 = var28.x;
			var9 = var28.x;
			var10 = var28.y;
			int var11 = var28.z;
			if(var1.isBlockNormalCube(var9, var10, var11)) {
				return 0;
			}

			if(var1.getBlockMaterial(var9, var10, var11) != Material.air) {
				return 0;
			}

			for(int var12 = 0; var12 < 3; ++var12) {
				int var13 = var9;
				int var14 = var10;
				int var15 = var11;
				byte var16 = 6;

				for(int var17 = 0; var17 < 2; ++var17) {
					var13 += var1.rand.nextInt(var16) - var1.rand.nextInt(var16);
					var14 += var1.rand.nextInt(1) - var1.rand.nextInt(1);
					var15 += var1.rand.nextInt(var16) - var1.rand.nextInt(var16);
					if(var1.isBlockNormalCube(var13, var14 - 1, var15) && !var1.isBlockNormalCube(var13, var14, var15) && !var1.getBlockMaterial(var13, var14, var15).getIsLiquid() && !var1.isBlockNormalCube(var13, var14 + 1, var15)) {
						float var18 = (float)var13 + 0.5F;
						float var19 = (float)var14;
						float var20 = (float)var15 + 0.5F;
						if(var1.getClosestPlayer((double)var18, (double)var19, (double)var20, 24.0D) == null) {
							float var21 = var18 - (float)var1.spawnX;
							float var22 = var19 - (float)var1.spawnY;
							float var23 = var20 - (float)var1.spawnZ;
							float var24 = var21 * var21 + var22 * var22 + var23 * var23;
							if(var24 >= 576.0F) {
								EntityLiving var29;
								try {
									var29 = (EntityLiving)this.entities[var7].getConstructor(new Class[]{World.class}).newInstance(new Object[]{var1});
								} catch (Exception var25) {
									var25.printStackTrace();
									return var4;
								}

								var29.setLocationAndAngles((double)var18, (double)var19, (double)var20, var1.rand.nextFloat() * 360.0F, 0.0F);
								if(var29.getCanSpawnHere()) {
									++var4;
									var1.spawnEntityInWorld(var29);
									if(var29 instanceof EntitySpider && var1.rand.nextInt(100) == 0) {
										EntitySkeleton var30 = new EntitySkeleton(var1);
										var30.setLocationAndAngles((double)var18, (double)var19, (double)var20, var29.rotationYaw, 0.0F);
										var1.spawnEntityInWorld(var30);
										var30.mountEntity(var29);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
