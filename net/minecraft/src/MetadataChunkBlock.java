package net.minecraft.src;

public class MetadataChunkBlock {
	public final EnumSkyBlock skyBlock;
	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;

	public MetadataChunkBlock(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.skyBlock = var1;
		this.minX = var2;
		this.minY = var3;
		this.minZ = var4;
		this.maxX = var5;
		this.maxY = var6;
		this.maxZ = var7;
	}

	public void updateLight(World var1) {
		int var2 = this.maxX - this.minX;
		int var3 = this.maxY - this.minY;
		int var4 = this.maxZ - this.minZ;
		int var5 = var2 * var3 * var4;
		if(var5 <= -Short.MIN_VALUE) {
			for(int var6 = this.minX; var6 <= this.maxX; ++var6) {
				for(int var7 = this.minZ; var7 <= this.maxZ; ++var7) {
					if(var1.blockExists(var6, 0, var7)) {
						for(int var8 = this.minY; var8 <= this.maxY; ++var8) {
							if(var8 >= 0 && var8 < 128) {
								int var9 = var1.getSavedLightValue(this.skyBlock, var6, var8, var7);
								boolean var10 = false;
								int var11 = var1.getBlockId(var6, var8, var7);
								int var12 = Block.lightOpacity[var11];
								if(var12 == 0) {
									var12 = 1;
								}

								int var13 = 0;
								if(this.skyBlock == EnumSkyBlock.Sky) {
									if(var1.canExistingBlockSeeTheSky(var6, var8, var7)) {
										var13 = 15;
									}
								} else if(this.skyBlock == EnumSkyBlock.Block) {
									var13 = Block.lightValue[var11];
								}

								int var14;
								int var20;
								if(var12 >= 15 && var13 == 0) {
									var20 = 0;
								} else {
									var14 = var1.getSavedLightValue(this.skyBlock, var6 - 1, var8, var7);
									int var15 = var1.getSavedLightValue(this.skyBlock, var6 + 1, var8, var7);
									int var16 = var1.getSavedLightValue(this.skyBlock, var6, var8 - 1, var7);
									int var17 = var1.getSavedLightValue(this.skyBlock, var6, var8 + 1, var7);
									int var18 = var1.getSavedLightValue(this.skyBlock, var6, var8, var7 - 1);
									int var19 = var1.getSavedLightValue(this.skyBlock, var6, var8, var7 + 1);
									var20 = var14;
									if(var15 > var14) {
										var20 = var15;
									}

									if(var16 > var20) {
										var20 = var16;
									}

									if(var17 > var20) {
										var20 = var17;
									}

									if(var18 > var20) {
										var20 = var18;
									}

									if(var19 > var20) {
										var20 = var19;
									}

									var20 -= var12;
									if(var20 < 0) {
										var20 = 0;
									}

									if(var13 > var20) {
										var20 = var13;
									}
								}

								if(var9 != var20) {
									var1.setLightValue(this.skyBlock, var6, var8, var7, var20);
									var14 = var20 - 1;
									if(var14 < 0) {
										var14 = 0;
									}

									var1.neighborLightPropagationChanged(this.skyBlock, var6 - 1, var8, var7, var14);
									var1.neighborLightPropagationChanged(this.skyBlock, var6, var8 - 1, var7, var14);
									var1.neighborLightPropagationChanged(this.skyBlock, var6, var8, var7 - 1, var14);
									if(var6 + 1 >= this.maxX) {
										var1.neighborLightPropagationChanged(this.skyBlock, var6 + 1, var8, var7, var14);
									}

									if(var8 + 1 >= this.maxY) {
										var1.neighborLightPropagationChanged(this.skyBlock, var6, var8 + 1, var7, var14);
									}

									if(var7 + 1 >= this.maxZ) {
										var1.neighborLightPropagationChanged(this.skyBlock, var6, var8, var7 + 1, var14);
									}
								}
							}
						}
					}
				}
			}

		}
	}

	public boolean getLightUpdated(int var1, int var2, int var3, int var4, int var5, int var6) {
		if(var1 >= this.minX && var2 >= this.minY && var3 >= this.minZ && var4 <= this.maxX && var5 <= this.maxY && var6 <= this.maxZ) {
			return true;
		} else {
			byte var7 = 1;
			if(var1 >= this.minX - var7 && var2 >= this.minY - var7 && var3 >= this.minZ - var7 && var4 <= this.maxX + var7 && var5 <= this.maxY + var7 && var6 <= this.maxZ + var7) {
				int var8 = this.maxX - this.minX;
				int var9 = this.maxY - this.minY;
				int var10 = this.maxZ - this.minZ;
				if(var1 > this.minX) {
					var1 = this.minX;
				}

				if(var2 > this.minY) {
					var2 = this.minY;
				}

				if(var3 > this.minZ) {
					var3 = this.minZ;
				}

				if(var4 < this.maxX) {
					var4 = this.maxX;
				}

				if(var5 < this.maxY) {
					var5 = this.maxY;
				}

				if(var6 < this.maxZ) {
					var6 = this.maxZ;
				}

				int var11 = var4 - var1;
				int var12 = var5 - var2;
				int var13 = var6 - var3;
				int var14 = var8 * var9 * var10;
				int var15 = var11 * var12 * var13;
				if(var15 - var14 <= 2) {
					this.minX = var1;
					this.minY = var2;
					this.minZ = var3;
					this.maxX = var4;
					this.maxY = var5;
					this.maxZ = var6;
					return true;
				}
			}

			return false;
		}
	}
}
