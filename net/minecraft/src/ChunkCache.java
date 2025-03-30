package net.minecraft.src;

public class ChunkCache implements IBlockAccess {
	private int chunkX;
	private int chunkZ;
	private Chunk[][] chunkArray;
	private boolean isEmpty;
	private World worldObj;

	public ChunkCache(World var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this(var1, var2, var3, var4, var5, var6, var7, 0);
	}

	public ChunkCache(World var1, int var2, int var3, int var4, int var5, int var6, int var7, int r) {
		this.worldObj = var1;
		this.chunkX = var2 - r >> 4;
		this.chunkZ = var4 - r >> 4;
		int var8 = var5 + r >> 4;
		int var9 = var7 + r >> 4;
		this.chunkArray = new Chunk[var8 - this.chunkX + 1][var9 - this.chunkZ + 1];

		this.isEmpty = true;

		int x;
		int z;
		Chunk chunk;
		for(x = this.chunkX; x <= var8; ++x) {
			for(z = this.chunkZ; z <= var9; ++z) {
				chunk = var1.getChunkFromChunkCoords(x, z);
				if(chunk == null) {
					continue;
				}

				this.chunkArray[x - this.chunkX][z - this.chunkZ] = chunk;
			}
		}

		for(x = var2 >> 4; x <= var5 >> 4; ++x) {
			for(z = var4 >> 4; z <= var7 >> 4; ++z) {
				chunk = this.chunkArray[x - this.chunkX][z - this.chunkZ];
				if(chunk == null || chunk.isChunkRendered || var3 >= 128 && chunk.blocks2 == null) {
					continue;
				}

				this.isEmpty = false;
			}
		}

	}

	public boolean extendedLevelsInChunkCache() {
		return this.isEmpty;
	}

	public int getBlockId(int var1, int var2, int var3) {
		if(var2 < 0) {
			return 0;
		} else if(var2 >= 256) {
			return 0;
		} else {
			int var4 = (var1 >> 4) - this.chunkX;
			int var5 = (var3 >> 4) - this.chunkZ;
			return this.chunkArray[var4][var5].getBlockID(var1 & 15, var2, var3 & 15);
		}
	}

	public TileEntity getBlockTileEntity(int var1, int var2, int var3) {
		int var4 = (var1 >> 4) - this.chunkX;
		int var5 = (var3 >> 4) - this.chunkZ;
		return this.chunkArray[var4][var5].getChunkBlockTileEntity(var1 & 15, var2, var3 & 15);
	}

	public float getBrightness(int var1, int var2, int var3) {
		return World.lightBrightnessTable[this.getLightValue(var1, var2, var3)];
	}

	public int getLightValue(int var1, int var2, int var3) {
		return this.getLightValueExt(var1, var2, var3, true);
	}

	public int getLightValueExt(int var1, int var2, int var3, boolean var4) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			int var5;
			int var6;
			if(var4) {
				var5 = this.getBlockId(var1, var2, var3);
				if(var5 == Block.stairSingle.blockID || var5 == Block.tilledField.blockID) {
					var6 = this.getLightValueExt(var1, var2 + 1, var3, false);
					int var7 = this.getLightValueExt(var1 + 1, var2, var3, false);
					int var8 = this.getLightValueExt(var1 - 1, var2, var3, false);
					int var9 = this.getLightValueExt(var1, var2, var3 + 1, false);
					int var10 = this.getLightValueExt(var1, var2, var3 - 1, false);
					if(var7 > var6) {
						var6 = var7;
					}

					if(var8 > var6) {
						var6 = var8;
					}

					if(var9 > var6) {
						var6 = var9;
					}

					if(var10 > var6) {
						var6 = var10;
					}

					return var6;
				}
			}

			if(var2 < 0) {
				return 0;
			} else if(var2 >= 256) {
				var5 = 15 - this.worldObj.skylightSubtracted;
				if(var5 < 0) {
					var5 = 0;
				}

				return var5;
			} else {
				var5 = (var1 >> 4) - this.chunkX;
				var6 = (var3 >> 4) - this.chunkZ;
				return this.chunkArray[var5][var6].getBlockLightValue(var1 & 15, var2, var3 & 15, this.worldObj.skylightSubtracted);
			}
		} else {
			return 15;
		}
	}

	public int getBlockMetadata(int var1, int var2, int var3) {
		if(var2 < 0) {
			return 0;
		} else if(var2 >= 256) {
			return 0;
		} else {
			int var4 = (var1 >> 4) - this.chunkX;
			int var5 = (var3 >> 4) - this.chunkZ;
			return this.chunkArray[var4][var5].getBlockMetadata(var1 & 15, var2, var3 & 15);
		}
	}

	public Material getBlockMaterial(int var1, int var2, int var3) {
		int var4 = this.getBlockId(var1, var2, var3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].material;
	}

	public boolean isBlockNormalCube(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
}
