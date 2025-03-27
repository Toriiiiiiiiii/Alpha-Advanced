package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler {
	private boolean disconnected = false;
	private NetworkManager netManager;
	public String loginProgress;
	private Minecraft mc;
	private WorldClient worldClient;
	private boolean posUpdated = false;
	Random rand = new Random();

	public NetClientHandler(Minecraft var1, String var2, int var3) throws IOException {
		this.mc = var1;
		Socket var4 = new Socket(InetAddress.getByName(var2), var3);
		this.netManager = new NetworkManager(var4, "Client", this);
	}

	public void processReadPackets() {
		if(!this.disconnected) {
			this.netManager.processReadPackets();
		}
	}

	public void handleLogin(Packet1Login var1) {
		this.mc.playerController = new PlayerControllerMP(this.mc, this);
		this.worldClient = new WorldClient(this);
		this.worldClient.multiplayerWorld = true;
		this.mc.changeWorld1(this.worldClient);
		this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
	}

	public void handlePickupSpawn(Packet21PickupSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		EntityItem var8 = new EntityItem(this.worldClient, var2, var4, var6, new ItemStack(var1.itemID, var1.count));
		var8.motionX = (double)var1.rotation / 128.0D;
		var8.motionY = (double)var1.pitch / 128.0D;
		var8.motionZ = (double)var1.roll / 128.0D;
		var8.serverPosX = var1.xPosition;
		var8.serverPosY = var1.yPosition;
		var8.serverPosZ = var1.zPosition;
		this.worldClient.addEntityToWorld(var1.entityId, var8);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		Object var8 = null;
		if(var1.type == 10) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 0);
		}

		if(var1.type == 11) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 1);
		}

		if(var1.type == 12) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 2);
		}

		if(var1.type == 1) {
			var8 = new EntityBoat(this.worldClient, var2, var4, var6);
		}

		if(var8 != null) {
			((Entity)var8).serverPosX = var1.xPosition;
			((Entity)var8).serverPosY = var1.yPosition;
			((Entity)var8).serverPosZ = var1.zPosition;
			((Entity)var8).rotationYaw = 0.0F;
			((Entity)var8).rotationPitch = 0.0F;
			((Entity)var8).entityID = var1.entityId;
			this.worldClient.addEntityToWorld(var1.entityId, (Entity)var8);
		}

	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		float var8 = (float)(var1.rotation * 360) / 256.0F;
		float var9 = (float)(var1.pitch * 360) / 256.0F;
		EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.mc.theWorld, var1.name);
		var10.serverPosX = var1.xPosition;
		var10.serverPosY = var1.yPosition;
		var10.serverPosZ = var1.zPosition;
		int var11 = var1.currentItem;
		if(var11 == 0) {
			var10.inventory.mainInventory[var10.inventory.currentItem] = null;
		} else {
			var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(var11);
		}

		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		this.worldClient.addEntityToWorld(var1.entityId, var10);
	}

	public void handleEntityTeleport(Packet34EntityTeleport var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.serverPosX = var1.xPosition;
			var2.serverPosY = var1.yPosition;
			var2.serverPosZ = var1.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = (float)(var1.yaw * 360) / 256.0F;
			float var10 = (float)(var1.pitch * 360) / 256.0F;
			var2.setPositionAndRotation(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleEntity(Packet30Entity var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.serverPosX += var1.xPosition;
			var2.serverPosY += var1.yPosition;
			var2.serverPosZ += var1.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = var1.rotating ? (float)(var1.yaw * 360) / 256.0F : var2.rotationYaw;
			float var10 = var1.rotating ? (float)(var1.pitch * 360) / 256.0F : var2.rotationPitch;
			var2.setPositionAndRotation(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleDestroyEntity(Packet29DestroyEntity var1) {
		this.worldClient.removeEntityFromWorld(var1.entityId);
	}

	public void handleFlying(Packet10Flying var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		double var3 = var2.posX;
		double var5 = var2.posY;
		double var7 = var2.posZ;
		float var9 = var2.rotationYaw;
		float var10 = var2.rotationPitch;
		if(var1.moving) {
			var3 = var1.xPosition;
			var5 = var1.yPosition;
			var7 = var1.zPosition;
		}

		if(var1.rotating) {
			var9 = var1.yaw;
			var10 = var1.pitch;
		}

		var2.ySize = 0.0F;
		var2.motionX = var2.motionY = var2.motionZ = 0.0D;
		var2.setPositionAndRotation(var3, var5, var7, var9, var10);
		var1.xPosition = var2.posX;
		var1.yPosition = var2.boundingBox.minY;
		var1.zPosition = var2.posZ;
		var1.stance = var2.posY;
		this.netManager.addToSendQueue(var1);
		if(!this.posUpdated) {
			this.mc.thePlayer.prevPosX = this.mc.thePlayer.posX;
			this.mc.thePlayer.prevPosY = this.mc.thePlayer.posY;
			this.mc.thePlayer.prevPosZ = this.mc.thePlayer.posZ;
			this.posUpdated = true;
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void handlePreChunk(Packet50PreChunk var1) {
		this.worldClient.doPreChunk(var1.xPosition, var1.yPosition, var1.mode);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
		Chunk var2 = this.worldClient.getChunkFromChunkCoords(var1.xPosition, var1.zPosition);
		int var3 = var1.xPosition * 16;
		int var4 = var1.zPosition * 16;

		for(int var5 = 0; var5 < var1.size; ++var5) {
			short var6 = var1.coordinateArray[var5];
			int var7 = var1.typeArray[var5] & 255;
			byte var8 = var1.metadataArray[var5];
			int var9 = var6 >> 12 & 15;
			int var10 = var6 >> 8 & 15;
			int var11 = var6 & 255;
			var2.setBlockIDWithMetadata(var9, var11, var10, var7, var8);
			this.worldClient.invalidateBlockReceiveRegion(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
			this.worldClient.markBlocksDirty(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
		}

	}

	public void handleMapChunk(Packet51MapChunk var1) {
		this.worldClient.invalidateBlockReceiveRegion(var1.xPosition, var1.yPosition, var1.zPosition, var1.xPosition + var1.xSize - 1, var1.yPosition + var1.ySize - 1, var1.zPosition + var1.zSize - 1);
		this.worldClient.setChunkData(var1.xPosition, var1.yPosition, var1.zPosition, var1.xSize, var1.ySize, var1.zSize, var1.chunkData);
	}

	public void handleBlockChange(Packet53BlockChange var1) {
		this.worldClient.handleBlockChange(var1.xPosition, var1.yPosition, var1.zPosition, var1.type, var1.metadata);
	}

	public void handleKickDisconnect(Packet255KickDisconnect var1) {
		this.netManager.networkShutdown("Got kicked");
		this.disconnected = true;
		this.mc.changeWorld1((World)null);
		this.mc.displayGuiScreen(new GuiConnectFailed("Disconnected by server", var1.reason));
	}

	public void handleErrorMessage(String var1) {
		if(!this.disconnected) {
			this.disconnected = true;
			this.mc.changeWorld1((World)null);
			this.mc.displayGuiScreen(new GuiConnectFailed("Connection lost", var1));
		}
	}

	public void addToSendQueue(Packet var1) {
		if(!this.disconnected) {
			this.netManager.addToSendQueue(var1);
		}
	}

	public void handleCollect(Packet22Collect var1) {
		EntityItem var2 = (EntityItem)this.worldClient.getEntityByID(var1.collectedEntityId);
		Object var3 = (EntityLiving)this.worldClient.getEntityByID(var1.collectorEntityId);
		if(var3 == null) {
			var3 = this.mc.thePlayer;
		}

		if(var2 != null) {
			this.worldClient.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, var2, (Entity)var3, -0.5F));
			this.worldClient.removeEntityFromWorld(var1.collectedEntityId);
		}

	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			EntityPlayer var3 = (EntityPlayer)var2;
			int var4 = var1.id;
			if(var4 == 0) {
				var3.inventory.mainInventory[var3.inventory.currentItem] = null;
			} else {
				var3.inventory.mainInventory[var3.inventory.currentItem] = new ItemStack(var4);
			}

		}
	}

	public void handleChat(Packet3Chat var1) {
		this.mc.ingameGUI.addChatMessage(var1.message);
	}

	public void handleArmAnimation(Packet18ArmAnimation var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			EntityPlayer var3 = (EntityPlayer)var2;
			var3.swingItem();
		}
	}

	public void handleAddToInventory(Packet17AddToInventory var1) {
		this.mc.thePlayer.inventory.addItemStackToInventory(new ItemStack(var1.itemID, var1.count, var1.itemDamage));
	}

	public void handleHandshake(Packet2Handshake var1) {
		if(var1.username.equals("-")) {
			this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 2));
		} else {
			try {
				URL var2 = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + this.mc.session.username + "&sessionId=" + this.mc.session.sessionId + "&serverId=" + var1.username);
				BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
				String var4 = var3.readLine();
				var3.close();
				if(var4.equalsIgnoreCase("ok")) {
					this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 2));
				} else {
					this.netManager.networkShutdown("Failed to login: " + var4);
				}
			} catch (Exception var5) {
				var5.printStackTrace();
				this.netManager.networkShutdown("Internal client error: " + var5.toString());
			}
		}

	}

	public void disconnect() {
		this.disconnected = true;
		this.netManager.networkShutdown("Closed");
	}

	public void handleMobSpawn(Packet24MobSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		float var8 = (float)(var1.yaw * 360) / 256.0F;
		float var9 = (float)(var1.pitch * 360) / 256.0F;
		EntityLiving var10 = (EntityLiving)EntityList.createEntityByID(var1.type, this.mc.theWorld);
		var10.serverPosX = var1.xPosition;
		var10.serverPosY = var1.yPosition;
		var10.serverPosZ = var1.zPosition;
		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		var10.isAIEnabled = true;
		this.worldClient.addEntityToWorld(var1.entityId, var10);
	}

	public void handleUpdateTime(Packet4UpdateTime var1) {
		this.mc.theWorld.setWorldTime(var1.time);
	}

	public void handlePlayerInventory(Packet5PlayerInventory var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		if(var1.inventoryType == -1) {
			var2.inventory.mainInventory = var1.inventory;
		}

		if(var1.inventoryType == -2) {
			var2.inventory.craftingInventory = var1.inventory;
		}

		if(var1.inventoryType == -3) {
			var2.inventory.armorInventory = var1.inventory;
		}

	}

	public void handleComplexEntity(Packet59ComplexEntity var1) {
		TileEntity var2 = this.worldClient.getBlockTileEntity(var1.xCoord, var1.yCoord, var1.zCoord);
		if(var2 != null) {
			var2.readFromNBT(var1.tileEntityNBT);
			this.worldClient.markBlocksDirty(var1.xCoord, var1.yCoord, var1.zCoord, var1.xCoord, var1.yCoord, var1.zCoord);
		}

	}

	public void handleSpawnPosition(Packet6SpawnPosition var1) {
		this.worldClient.spawnX = var1.xPosition;
		this.worldClient.spawnY = var1.yPosition;
		this.worldClient.spawnZ = var1.zPosition;
	}
}
