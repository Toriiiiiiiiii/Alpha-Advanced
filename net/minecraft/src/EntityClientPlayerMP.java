package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP {
	private NetClientHandler sendQueue;
	private int motionUpdateCounter = 0;
	private double oldPosX;
	private double oldBasePos;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	private float oldRotationPitch;
	private InventoryPlayer serverSideInventory = new InventoryPlayer((EntityPlayer)null);

	public EntityClientPlayerMP(Minecraft var1, World var2, Session var3, NetClientHandler var4) {
		super(var1, var2, var3);
		this.sendQueue = var4;
	}

	public void onUpdate() {
		super.onUpdate();
		this.sendMotionUpdates();
	}

	public void onPlayerUpdate() {
		this.sendMotionUpdates();
	}

	public void sendMotionUpdates() {
		if(this.motionUpdateCounter++ == 20) {
			if(!this.inventory.getInventoryEqual(this.serverSideInventory)) {
				this.sendQueue.addToSendQueue(new Packet5PlayerInventory(-1, this.inventory.mainInventory));
				this.sendQueue.addToSendQueue(new Packet5PlayerInventory(-2, this.inventory.craftingInventory));
				this.sendQueue.addToSendQueue(new Packet5PlayerInventory(-3, this.inventory.armorInventory));
				this.serverSideInventory = this.inventory.copyInventory();
			}

			this.motionUpdateCounter = 0;
		}

		double var1 = this.posX - this.oldPosX;
		double var3 = this.boundingBox.minY - this.oldBasePos;
		double var5 = this.posY - this.oldPosY;
		double var7 = this.posZ - this.oldPosZ;
		double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
		double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
		boolean var13 = var3 != 0.0D || var5 != 0.0D || var1 != 0.0D || var7 != 0.0D;
		boolean var14 = var9 != 0.0D || var11 != 0.0D;
		if(var13 && var14) {
			this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
		} else if(var13) {
			this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
		} else if(var14) {
			this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
		} else {
			this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
		}

		if(var13) {
			this.oldPosX = this.posX;
			this.oldBasePos = this.boundingBox.minY;
			this.oldPosY = this.posY;
			this.oldPosZ = this.posZ;
		}

		if(var14) {
			this.oldRotationYaw = this.rotationYaw;
			this.oldRotationPitch = this.rotationPitch;
		}

	}

	protected void joinEntityItemWithWorld(EntityItem var1) {
		System.out.println("Dropping?");
		Packet21PickupSpawn var2 = new Packet21PickupSpawn(var1);
		this.sendQueue.addToSendQueue(var2);
		var1.posX = (double)var2.xPosition / 32.0D;
		var1.posY = (double)var2.yPosition / 32.0D;
		var1.posZ = (double)var2.zPosition / 32.0D;
		var1.motionX = (double)var2.rotation / 128.0D;
		var1.motionY = (double)var2.pitch / 128.0D;
		var1.motionZ = (double)var2.roll / 128.0D;
	}

	public void sendChatMessage(String var1) {
		this.sendQueue.addToSendQueue(new Packet3Chat(var1));
	}

	public void swingItem() {
		super.swingItem();
		this.sendQueue.addToSendQueue(new Packet18ArmAnimation(this, 1));
	}
}
