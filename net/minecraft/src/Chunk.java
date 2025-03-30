package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk {
	public static boolean isLit;
	public byte[] blocks;
	public boolean isChunkLoaded;
	public World worldObj;
	public NibbleArray data;
	public NibbleArray skylightMap;
	public NibbleArray blocklightMap;
	public byte[] heightMap;
	public int height;
	public final int xPosition;
	public final int zPosition;
	public Map chunkTileEntityMap;
	public List[] entities;
	public boolean isTerrainPopulated;
	public boolean isModified;
	public boolean neverSave;
	public boolean isChunkRendered;
	public boolean hasEntities;
	public long lastSaveTime;
	public byte[] blocks2;
	public NibbleArray data2;
	public NibbleArray skylightMap2;
	public NibbleArray blocklightMap2;

	public Chunk(World var1, int var2, int var3) {
		this.chunkTileEntityMap = new HashMap();
		this.entities = new List[16];
		this.isTerrainPopulated = false;
		this.isModified = false;
		this.isChunkRendered = false;
		this.hasEntities = false;
		this.lastSaveTime = 0L;
		this.worldObj = var1;
		this.xPosition = var2;
		this.zPosition = var3;
		this.heightMap = new byte[256];

		for(int var4 = 0; var4 < this.entities.length; ++var4) {
			this.entities[var4] = new ArrayList();
		}

	}

	public Chunk(World var1, byte[] var2, int var3, int var4) {
		this(var1, var3, var4);
		this.blocks = var2;
		this.data = new NibbleArray(var2.length);
		this.skylightMap = new NibbleArray(var2.length);
		this.blocklightMap = new NibbleArray(var2.length);
	}

	public boolean isAtLocation(int var1, int var2) {
		return var1 == this.xPosition && var2 == this.zPosition;
	}

	public int getHeightValue(int var1, int var2) {
		return this.heightMap[var2 << 4 | var1] & 255;
	}

	public void doNothing() {
	}

	public void generateHeightMap() {
		int var1 = this.blocks2 == null ? 127 : 255;

		for(int var2 = 0; var2 < 16; ++var2) {
			for(int var3 = 0; var3 < 16; ++var3) {
				int var4 = this.blocks2 == null ? 127 : 255;

				for(int var5 = var2 << 11 | var3 << 7; var4 > 0 && Block.lightOpacity[var4 > 128 ? (this.blocks2 == null ? 0 : this.blocks2[var5 + var4 - 129]) : this.blocks[var5 + var4 - 1]] == 0; --var4) {
				}

				this.heightMap[var3 << 4 | var2] = (byte)var4;
				if(var4 < var1) {
					var1 = var4;
				}
			}
		}

		this.height = var1;
		this.isModified = true;
	}

	public void generateSkylightMap() {
		int var1 = this.blocks2 == null ? 127 : 255;

		int var2;
		int var3;
		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				int var4 = this.blocks2 == null ? 127 : 255;

				int var5;
				for(var5 = var2 << 11 | var3 << 7; var4 > 0 && Block.lightOpacity[var4 > 128 ? (this.blocks2 == null ? 0 : this.blocks2[var5 + var4 - 129]) : this.blocks[var5 + var4 - 1]] == 0; --var4) {
				}

				this.heightMap[var3 << 4 | var2] = (byte)var4;
				if(var4 < var1) {
					var1 = var4;
				}

				int var6 = 15;
				int var7 = this.blocks2 == null ? 127 : 255;

				do {
					var6 -= Block.lightOpacity[var7 > 127 ? (this.blocks2 == null ? 0 : this.blocks2[var5 + var7 - 128]) : this.blocks[var5 + var7]];
					if(var6 > 0) {
						if((var7 & 128) == 0) {
							this.skylightMap.set(var2, var7, var3, var6);
						} else if(this.blocks2 != null) {
							this.skylightMap2.set(var2, var7 & 127, var3, var6);
						}
					}

					--var7;
				} while(var7 > 0 && var6 > 0);
			}
		}

		this.height = var1;

		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				this.updateSkylight_do(var2, var3);
			}
		}

		this.isModified = true;
	}

	private void updateSkylight_do(int var1, int var2) {
		int var3 = this.getHeightValue(var1, var2);
		int var4 = this.xPosition * 16 + var1;
		int var5 = this.zPosition * 16 + var2;
		this.checkSkylightNeighborUpdate(var4 - 1, var5, var3);
		this.checkSkylightNeighborUpdate(var4 + 1, var5, var3);
		this.checkSkylightNeighborUpdate(var4, var5 - 1, var3);
		this.checkSkylightNeighborUpdate(var4, var5 + 1, var3);
	}

	private void checkSkylightNeighborUpdate(int var1, int var2, int var3) {
		int var4 = this.worldObj.getHeightValue(var1, var2);
		if(var4 > var3) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var1, var3, var2, var1, var4, var2, 10);
		} else if(var4 < var3) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var1, var4, var2, var1, var3, var2, 10);
		}

		this.isModified = true;
	}

	private void relightBlock(int var1, int var2, int var3) {
		int var4 = this.heightMap[var3 << 4 | var1] & 255;
		int var5 = var4;
		if(var2 > var4) {
			var5 = var2;
		}

		for(int var6 = var1 << 11 | var3 << 7; var5 > 0 && Block.lightOpacity[var5 > 128 ? (this.blocks2 == null ? 0 : this.blocks2[var6 + var5 - 129]) : this.blocks[var6 + var5 - 1]] == 0; --var5) {
		}

		if(var5 != var4) {
			this.worldObj.markBlocksDirtyVertical(var1, var3, var5, var4);
			this.heightMap[var3 << 4 | var1] = (byte)var5;
			int var7;
			int var8;
			int var9;
			if(var5 < this.height) {
				this.height = var5;
			} else {
				var7 = this.blocks2 == null ? 127 : 255;

				for(var8 = 0; var8 < 16; ++var8) {
					for(var9 = 0; var9 < 16; ++var9) {
						if((this.heightMap[var9 << 4 | var8] & 255) < var7) {
							var7 = this.heightMap[var9 << 4 | var8] & 255;
						}
					}
				}

				this.height = var7;
			}

			var7 = this.xPosition * 16 + var1;
			var8 = this.zPosition * 16 + var3;
			if(var5 < var4) {
				for(var9 = var5; var9 < var4; ++var9) {
					if((var9 & 128) != 0) {
						if(this.blocks2 != null) {
							this.skylightMap2.set(var1, var9 & 127, var3, 15);
						}

						continue;
					}

					this.skylightMap.set(var1, var9, var3, 15);
				}
			} else {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var7, var4, var8, var7, var5, var8, 10);

				for(var9 = var4; var9 < var5; ++var9) {
					if((var9 & 128) != 0) {
						if(this.blocks2 != null) {
							this.skylightMap2.set(var1, var9 & 127, var3, 0);
						}

						continue;
					}
					this.skylightMap.set(var1, var9, var3, 0);
				}
			}

			var9 = 15;

			int var10;
			for(var10 = var5; var5 > 0 && var9 > 0;) {
				--var5;
				int var11 = Block.lightOpacity[this.getBlockID(var1, var5, var3)];
				if(var11 == 0) {
					var11 = 1;
				}

				var9 -= var11;
				if(var9 < 0) {
					var9 = 0;
				}

				if((var5 & 128) != 0) {
					if(this.blocks2 != null) {
						this.skylightMap2.set(var1, var5 & 127, var3, var9);
					}

					continue;
				}

				this.skylightMap.set(var1, var5, var3, var9);
			}

			while(var5 > 0 && Block.lightOpacity[this.getBlockID(var1, var5 - 1, var3)] == 0) {
				--var5;
			}

			if(var5 != var10) {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var7 - 1, var5, var8 - 1, var7 + 1, var10, var8 + 1, 10);
			}

			this.isModified = true;
		}
	}

	public int getBlockID(int var1, int var2, int var3) {
		if((var2 & 128) != 0) {
			if(this.blocks2 == null) {
				return 0;
			}

			return this.blocks2[var1 << 11 | var3 << 7 | var2 & 127];
		}
		return this.blocks[var1 << 11 | var3 << 7 | var2];
	}

	public boolean setBlockIDWithMetadata(int var1, int var2, int var3, int var4, int var5) {
		byte var6 = (byte)var4;
		int var7 = this.heightMap[var3 << 4 | var1] & 255;
		int var8 = (var2 & 128) == 0 ? this.blocks[var1 << 11 | var3 << 7 | var2] & 255 : (this.blocks2 == null ? 0 : this.blocks2[var1 << 11 | var3 << 7 | var2 & 127] & 255);
		if(var8 == var4 && ((var2 & 128) == 0 ? this.data.get(var1, var2, var3) : (this.blocks2 == null ? 0 : this.data2.get(var1, var2 & 127, var3))) == var5) {
			return false;
		} else {
			boolean generateSkylightMap = false;
			if((var2 & 128) != 0 && this.blocks2 == null) {
				if(var4 == 0) {
					return false;
				}

				this.blocks2 = new byte[this.blocks.length];
				this.data2 = new NibbleArray(this.blocks.length);
				this.skylightMap2 = new NibbleArray(this.blocks.length);
				this.blocklightMap2 = new NibbleArray(this.blocks.length);
				generateSkylightMap = var2 >= var7;
			}
			int var9 = this.xPosition * 16 + var1;
			int var10 = this.zPosition * 16 + var3;
			if((var2 & 128) == 0) {
				this.blocks[var1 << 11 | var3 << 7 | var2] = var6;
			} else {
				this.blocks2[var1 << 11 | var3 << 7 | var2 & 127] = var6;
			}
			if(var8 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[var8].onBlockRemoval(this.worldObj, var9, var2, var10);
			}

			if((var2 & 128) == 0) {
				this.data.set(var1, var2, var3, var5);
			} else {
				this.data2.set(var1, var2 & 127, var3, var5);
			}

			if(generateSkylightMap) {
				this.generateSkylightMap();
			} else {
				if(Block.lightOpacity[var6] != 0) {
					if(var2 >= var7) {
						this.relightBlock(var1, var2 + 1, var3);
					}
				} else if(var2 == var7 - 1) {
					this.relightBlock(var1, var2, var3);
				}
			} 	this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var9, var2, var10, var9, var2, var10, 10);

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, var9, var2, var10, var9, var2, var10, 10);
			this.updateSkylight_do(var1, var3);
			if(var4 != 0) {
				Block.blocksList[var4].onBlockAdded(this.worldObj, var9, var2, var10);
			}

			this.isModified = true;
			return true;
		}
	}

	public boolean setBlockID(int var1, int var2, int var3, int var4) {
		byte var5 = (byte)var4;
		int var6 = this.heightMap[var3 << 4 | var1] & 255;
		int var7 = (var2 & 128) == 0 ? this.blocks[var1 << 11 | var3 << 7 | var2] & 255 : (this.blocks2 == null ? 0 : this.blocks2[var1 << 11 | var3 << 7 | var2 & 127] & 255);
		if(var7 == var4) {
			return false;
		} else {
			boolean generateSkylightMap = false;
			if((var2 & 128) != 0 && this.blocks2 == null) {
				if(var4 == 0) {
					return false;
				}

				this.blocks2 = new byte[this.blocks.length];
				this.data2 = new NibbleArray(this.blocks.length);
				this.skylightMap2 = new NibbleArray(this.blocks.length);
				this.blocklightMap2 = new NibbleArray(this.blocks.length);
				generateSkylightMap = var2 >= var6;
			}
			int var8 = this.xPosition * 16 + var1;
			int var9 = this.zPosition * 16 + var3;
			if((var2 & 128) == 0) {
				this.blocks[var1 << 11 | var3 << 7 | var2] = var5;
			} else {
				this.blocks2[var1 << 11 | var3 << 7 | var2 & 127] = var5;
			}

			if(var7 != 0) {
				Block.blocksList[var7].onBlockRemoval(this.worldObj, var8, var2, var9);
			}

			if((var2 & 128) == 0) {
				this.data.set(var1, var2, var3, 0);
			} else {
				this.data2.set(var1, var2 & 127, var3, 0);
			}

			if(generateSkylightMap) {
				this.generateSkylightMap();
			} else {
				if(Block.lightOpacity[var5] != 0) {
					if(var2 >= var6) {
						this.relightBlock(var1, var2 + 1, var3);
					}
				} else if(var2 == var6 - 1) {
					this.relightBlock(var1, var2, var3);
				}
			} this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var8, var2, var9, var8, var2, var9, 10);

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var8, var2, var9, var8, var2, var9, 10);
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, var8, var2, var9, var8, var2, var9, 10);
			this.updateSkylight_do(var1, var3);
			if(var4 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[var4].onBlockAdded(this.worldObj, var8, var2, var9);
			}

			this.isModified = true;
			return true;
		}
	}

	public int getBlockMetadata(int var1, int var2, int var3) {
		if((var2 & 128) != 0) {
			if(this.blocks2 == null) {
				return 0;
			}

			return this.data2.get(var1, var2 & 127, var3);
		}
		return this.data.get(var1, var2, var3);
	}

	public void setBlockMetadata(int var1, int var2, int var3, int var4) {
		if((var2 & 128) != 0) {
			if(this.blocks2 == null) {
				return;
			}

			this.isModified = true;
			this.data2.set(var1, var2 & 127, var3, var4);
			return;
		}
		this.isModified = true;
		this.data.set(var1, var2, var3, var4);
	}

	public int getSavedLightValue(EnumSkyBlock var1, int var2, int var3, int var4) {
		if((var3 & 128) != 0) {
			if(this.blocks2 == null) {
				return this.canBlockSeeTheSky(var2, var3, var4) ? var1.defaultLightValue : 0;
			}

			return var1 == EnumSkyBlock.Sky ? this.skylightMap2.get(var2, var3 & 127, var4) : (var1 == EnumSkyBlock.Block ? this.blocklightMap2.get(var2, var3 & 127, var4) : 0);
		}
		return var1 == EnumSkyBlock.Sky ? this.skylightMap.get(var2, var3, var4) : (var1 == EnumSkyBlock.Block ? this.blocklightMap.get(var2, var3, var4) : 0);
	}

	public void setLightValue(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
		if((var3 & 128) != 0) {
			if(this.blocks2 == null) {
				this.blocks2 = new byte[this.blocks.length];
				this.data2 = new NibbleArray(this.blocks.length);
				this.skylightMap2 = new NibbleArray(this.blocks.length);
				this.blocklightMap2 = new NibbleArray(this.blocks.length);
				this.generateSkylightMap();
			}

			this.isModified = true;
			if(var1 == EnumSkyBlock.Sky) {
				this.skylightMap2.set(var2, var3 & 127, var4, var5);
			} else if(var1 == EnumSkyBlock.Block) {
				this.blocklightMap2.set(var2, var3 & 127, var4, var5);
			}

			return;
		}
		this.isModified = true;
		if(var1 == EnumSkyBlock.Sky) {
			this.skylightMap.set(var2, var3, var4, var5);
		} else {
			if(var1 != EnumSkyBlock.Block) {
				return;
			}

			this.blocklightMap.set(var2, var3, var4, var5);
		}

	}

	public int getBlockLightValue(int var1, int var2, int var3, int var4) {
		if((var2 & 128) != 0) {
			if(this.blocks2 == null) {
				return var4 < EnumSkyBlock.Sky.defaultLightValue ? EnumSkyBlock.Sky.defaultLightValue - var4 : 0;
			}

			int var5 = this.skylightMap2.get(var1, var2 & 127, var3);
			if(var5 > 0) {
				isLit = true;
			}

			var5 -= var4;
			int var6 = this.blocklightMap2.get(var1, var2 & 127, var3);
			if(var6 > var5) {
				var5 = var6;
			}

			return var5;
		}
		int var5 = this.skylightMap.get(var1, var2, var3);
		if(var5 > 0) {
			isLit = true;
		}

		var5 -= var4;
		int var6 = this.blocklightMap.get(var1, var2, var3);
		if(var6 > var5) {
			var5 = var6;
		}

		return var5;
	}

	public void addEntity(Entity var1) {
		if(!this.isChunkRendered) {
			this.hasEntities = true;
			int var2 = MathHelper.floor_double(var1.posX / 16.0D);
			int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
			if(var2 != this.xPosition || var3 != this.zPosition) {
				System.out.println("Wrong location! " + var1);
			}

			int var4 = MathHelper.floor_double(var1.posY / 16.0D);
			if(var4 < 0) {
				var4 = 0;
			}

			if(var4 >= this.entities.length) {
				var4 = this.entities.length - 1;
			}

			var1.addedToChunk = true;
			var1.chunkCoordX = this.xPosition;
			var1.chunkCoordY = var4;
			var1.chunkCoordZ = this.zPosition;
			this.entities[var4].add(var1);
		}
	}

	public void removeEntity(Entity var1) {
		this.removeEntityAtIndex(var1, var1.chunkCoordY);
	}

	public void removeEntityAtIndex(Entity var1, int var2) {
		if(var2 < 0) {
			var2 = 0;
		}

		if(var2 >= this.entities.length) {
			var2 = this.entities.length - 1;
		}

		this.entities[var2].remove(var1);
	}

	public boolean canBlockSeeTheSky(int var1, int var2, int var3) {
		return var2 >= (this.heightMap[var3 << 4 | var1] & 255);
	}

	public TileEntity getChunkBlockTileEntity(int var1, int var2, int var3) {
		ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
		TileEntity var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
		if(var5 == null) {
			int var6 = this.getBlockID(var1, var2, var3);
			if(!Block.isBlockContainer[var6]) {
				return null;
			}

			BlockContainer var7 = (BlockContainer)Block.blocksList[var6];
			var7.onBlockAdded(this.worldObj, this.xPosition * 16 + var1, var2, this.zPosition * 16 + var3);
			var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
		}

		return var5;
	}

	public void addTileEntity(TileEntity var1) {
		int var2 = var1.xCoord - this.xPosition * 16;
		int var3 = var1.yCoord;
		int var4 = var1.zCoord - this.zPosition * 16;
		this.setChunkBlockTileEntity(var2, var3, var4, var1);
	}

	public void setChunkBlockTileEntity(int var1, int var2, int var3, TileEntity var4) {
		ChunkPosition var5 = new ChunkPosition(var1, var2, var3);
		var4.worldObj = this.worldObj;
		var4.xCoord = this.xPosition * 16 + var1;
		var4.yCoord = var2;
		var4.zCoord = this.zPosition * 16 + var3;
		if(this.getBlockID(var1, var2, var3) != 0 && Block.blocksList[this.getBlockID(var1, var2, var3)] instanceof BlockContainer) {
			if(this.isChunkLoaded) {
				if(this.chunkTileEntityMap.get(var5) != null) {
					this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.get(var5));
				}

				this.worldObj.loadedTileEntityList.add(var4);
			}

			this.chunkTileEntityMap.put(var5, var4);
		} else {
			System.out.println("Attempted to place a tile entity where there was no entity tile!");
		}
	}

	public void removeChunkBlockTileEntity(int var1, int var2, int var3) {
		ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
		if(this.isChunkLoaded) {
			this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.remove(var4));
		}

	}

	public void onChunkLoad() {
		this.isChunkLoaded = true;
		this.worldObj.loadedTileEntityList.addAll(this.chunkTileEntityMap.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.addLoadedEntities(this.entities[var1]);
		}

	}

	public void onChunkUnload() {
		this.isChunkLoaded = false;
		this.worldObj.loadedTileEntityList.removeAll(this.chunkTileEntityMap.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.unloadEntities(this.entities[var1]);
		}

	}

	public void setChunkModified() {
		this.isModified = true;
	}

	public void getEntitiesWithinAABBForEntity(Entity var1, AxisAlignedBB var2, List var3) {
		int var4 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(var9 != var1 && var9.boundingBox.intersectsWith(var2)) {
					var3.add(var9);
				}
			}
		}

	}

	public void getEntitiesOfTypeWithinAAAB(Class var1, AxisAlignedBB var2, List var3) {
		int var4 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(var1.isAssignableFrom(var9.getClass()) && var9.boundingBox.intersectsWith(var2)) {
					var3.add(var9);
				}
			}
		}

	}

	public boolean needsSaving(boolean var1) {
		return this.neverSave ? false : (this.hasEntities && this.worldObj.worldTime != this.lastSaveTime ? true : this.isModified);
	}

	public int setChunkData(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
		if(var6 > 128 && this.blocks2 == null) {
			this.blocks2 = new byte[this.blocks.length];
			this.data2 = new NibbleArray(this.blocks.length);
			this.skylightMap2 = new NibbleArray(this.blocks.length);
			this.blocklightMap2 = new NibbleArray(this.blocks.length);
		}

		int var9;
		int var10;
		int var11;
		int var12;
		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				if(var3 < 128) {
					var11 = var9 << 11 | var10 << 7 | var3;
					var12 = (var6 > 128 ? 128 : var6) - var3;
					System.arraycopy(var1, var8, this.blocks, var11, var12);
					var8 += var12;
				}

				if(var6 > 128) {
					var11 = var9 << 11 | var10 << 7 | (var3 < 128 ? 0 : var3 & 127);
					var12 = var6 - (var3 < 128 ? 128 : var3);
					System.arraycopy(var1, var8, this.blocks2, var11, var12);
					var8 += var12;
				}
			}
		}

		this.generateHeightMap();

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				if(var3 < 128) {
					var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
					var12 = ((var6 > 128 ? 128 : var6) - var3) / 2;
					System.arraycopy(var1, var8, this.data.data, var11, var12);
					var8 += var12;
				}

				if(var6 > 128) {
					var11 = (var9 << 11 | var10 << 7 | (var3 < 128 ? 0 : var3 & 127)) >> 1;
					var12 = (var6 - (var3 < 128 ? 128 : var3)) / 2;
					System.arraycopy(var1, var8, this.data2.data, var11, var12);
					var8 += var12;
				}
			}
		}

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				if(var3 < 128) {
					var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
					var12 = ((var6 > 128 ? 128 : var6) - var3) / 2;
					System.arraycopy(var1, var8, this.blocklightMap.data, var11, var12);
					var8 += var12;
				}

				if(var6 > 128) {
					var11 = (var9 << 11 | var10 << 7 | (var3 < 128 ? 0 : var3 & 127)) >> 1;
					var12 = (var6 - (var3 < 128 ? 128 : var3)) / 2;
					System.arraycopy(var1, var8, this.blocklightMap2.data, var11, var12);
					var8 += var12;
				}
			}
		}

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				if(var3 < 128) {
					var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
					var12 = ((var6 > 128 ? 128 : var6) - var3) / 2;
					System.arraycopy(var1, var8, this.skylightMap.data, var11, var12);
					var8 += var12;
				}

				if(var6 > 128) {
					var11 = (var9 << 11 | var10 << 7 | (var3 < 128 ? 0 : var3 & 127)) >> 1;
					var12 = (var6 - (var3 < 128 ? 128 : var3)) / 2;
					System.arraycopy(var1, var8, this.skylightMap2.data, var11, var12);
					var8 += var12;
				}
			}
		}

		return var8;
	}

	public Random getRandomWithSeed(long var1) {
		return new Random(this.worldObj.randomSeed + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ var1);
	}
}
