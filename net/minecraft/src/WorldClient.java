package net.minecraft.src;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class WorldClient extends World {
	private LinkedList blocksToReceive = new LinkedList();
	private NetClientHandler sendQueue;
	private ChunkProviderClient clientChunkProvider;
	private boolean noTileEntityUpdates = false;
	private MCHashTable entityHashTable = new MCHashTable();
	private Set entityList = new HashSet();
	private Set entitySpawnQueue = new HashSet();

	public WorldClient(NetClientHandler var1) {
		super("MpServer");
		this.sendQueue = var1;
		this.spawnX = 8;
		this.spawnY = 64;
		this.spawnZ = 8;
	}

	public void tick() {
		++this.worldTime;
		int var1 = this.calculateSkylightSubtracted(1.0F);
		int var2;
		if(var1 != this.skylightSubtracted) {
			this.skylightSubtracted = var1;

			for(var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
				((IWorldAccess)this.worldAccesses.get(var2)).updateAllRenderers();
			}
		}

		for(var2 = 0; var2 < 10 && !this.entitySpawnQueue.isEmpty(); ++var2) {
			Entity var3 = (Entity)this.entitySpawnQueue.iterator().next();
			this.spawnEntityInWorld(var3);
		}

		this.sendQueue.processReadPackets();

		for(var2 = 0; var2 < this.blocksToReceive.size(); ++var2) {
			WorldBlockPositionType var4 = (WorldBlockPositionType)this.blocksToReceive.get(var2);
			if(--var4.acceptCountdown == 0) {
				super.setBlockAndMetadata(var4.posX, var4.posY, var4.posZ, var4.blockID, var4.metadata);
				super.markBlockNeedsUpdate(var4.posX, var4.posY, var4.posZ);
				this.blocksToReceive.remove(var2--);
			}
		}

	}

	public void invalidateBlockReceiveRegion(int var1, int var2, int var3, int var4, int var5, int var6) {
		for(int var7 = 0; var7 < this.blocksToReceive.size(); ++var7) {
			WorldBlockPositionType var8 = (WorldBlockPositionType)this.blocksToReceive.get(var7);
			if(var8.posX >= var1 && var8.posY >= var2 && var8.posZ >= var3 && var8.posX <= var4 && var8.posY <= var5 && var8.posZ <= var6) {
				this.blocksToReceive.remove(var7--);
			}
		}

	}

	protected IChunkProvider getChunkProvider(File var1) {
		this.clientChunkProvider = new ChunkProviderClient(this);
		return this.clientChunkProvider;
	}

	public void setSpawnLocation() {
		this.spawnX = 8;
		this.spawnY = 64;
		this.spawnZ = 8;
	}

	protected void updateBlocksAndPlayCaveSounds() {
	}

	public void scheduleBlockUpdate(int var1, int var2, int var3, int var4) {
	}

	public boolean tickUpdates(boolean var1) {
		return false;
	}

	public void doPreChunk(int var1, int var2, boolean var3) {
		if(var3) {
			this.clientChunkProvider.loadChunk(var1, var2);
		} else {
			this.clientChunkProvider.unloadChunk(var1, var2);
		}

		if(!var3) {
			this.markBlocksDirty(var1 * 16, 0, var2 * 16, var1 * 16 + 15, 256, var2 * 16 + 15);
		}

	}

	public boolean spawnEntityInWorld(Entity var1) {
		boolean var2 = super.spawnEntityInWorld(var1);
		if(var1 instanceof EntityPlayerSP) {
			this.entityList.add(var1);
		}

		return var2;
	}

	public void setEntityDead(Entity var1) {
		super.setEntityDead(var1);
		if(var1 instanceof EntityPlayerSP) {
			this.entityList.remove(var1);
		}

	}

	protected void obtainEntitySkin(Entity var1) {
		super.obtainEntitySkin(var1);
		if(this.entitySpawnQueue.contains(var1)) {
			this.entitySpawnQueue.remove(var1);
		}

	}

	protected void releaseEntitySkin(Entity var1) {
		super.releaseEntitySkin(var1);
		if(this.entityList.contains(var1)) {
			this.entitySpawnQueue.add(var1);
		}

	}

	public void addEntityToWorld(int var1, Entity var2) {
		this.entityList.add(var2);
		if(!this.spawnEntityInWorld(var2)) {
			this.entitySpawnQueue.add(var2);
		}

		this.entityHashTable.addKey(var1, var2);
	}

	public Entity getEntityByID(int var1) {
		return (Entity)this.entityHashTable.lookup(var1);
	}

	public Entity removeEntityFromWorld(int var1) {
		Entity var2 = (Entity)this.entityHashTable.removeObject(var1);
		if(var2 != null) {
			this.entityList.remove(var2);
			this.setEntityDead(var2);
		}

		return var2;
	}

	public boolean setBlockMetadata(int var1, int var2, int var3, int var4) {
		int var5 = this.getBlockId(var1, var2, var3);
		int var6 = this.getBlockMetadata(var1, var2, var3);
		if(super.setBlockMetadata(var1, var2, var3, var4)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, var1, var2, var3, var5, var6));
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlockAndMetadata(int var1, int var2, int var3, int var4, int var5) {
		int var6 = this.getBlockId(var1, var2, var3);
		int var7 = this.getBlockMetadata(var1, var2, var3);
		if(super.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, var1, var2, var3, var6, var7));
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlock(int var1, int var2, int var3, int var4) {
		int var5 = this.getBlockId(var1, var2, var3);
		int var6 = this.getBlockMetadata(var1, var2, var3);
		if(super.setBlock(var1, var2, var3, var4)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, var1, var2, var3, var5, var6));
			return true;
		} else {
			return false;
		}
	}

	public boolean handleBlockChange(int var1, int var2, int var3, int var4, int var5) {
		this.invalidateBlockReceiveRegion(var1, var2, var3, var1, var2, var3);
		if(super.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
			this.notifyBlockChange(var1, var2, var3, var4);
			return true;
		} else {
			return false;
		}
	}

	public void updateTileEntityChunkAndDoNothing(int var1, int var2, int var3, TileEntity var4) {
		if(!this.noTileEntityUpdates) {
			this.sendQueue.addToSendQueue(new Packet59ComplexEntity(var1, var2, var3, var4));
		}
	}

	public void sendQuittingDisconnectingPacket() {
		this.sendQueue.addToSendQueue(new Packet255KickDisconnect("Quitting"));
	}
}
