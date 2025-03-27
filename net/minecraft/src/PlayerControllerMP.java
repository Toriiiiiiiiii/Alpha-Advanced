package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP extends PlayerController {
	private int currentBlockX = -1;
	private int currentBlockY = -1;
	private int currentBlockZ = -1;
	private float curBlockDamageMP = 0.0F;
	private float prevBlockDamageMP = 0.0F;
	private float stepSoundTickCounter = 0.0F;
	private int blockHitDelay = 0;
	private boolean isHittingBlock = false;
	private NetClientHandler netClientHandler;
	private int currentPlayerItem = 0;

	public PlayerControllerMP(Minecraft var1, NetClientHandler var2) {
		super(var1);
		this.netClientHandler = var2;
	}

	public void flipPlayer(EntityPlayer var1) {
		var1.rotationYaw = -180.0F;
	}

	public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
		this.netClientHandler.addToSendQueue(new Packet14BlockDig(3, var1, var2, var3, var4));
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		int var6 = this.mc.theWorld.getBlockMetadata(var1, var2, var3);
		boolean var7 = super.sendBlockRemoved(var1, var2, var3, var4);
		ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
		if(var8 != null) {
			var8.onDestroyBlock(var5, var1, var2, var3);
			if(var8.stackSize == 0) {
				var8.onItemDestroyedByUse(this.mc.thePlayer);
				this.mc.thePlayer.destroyCurrentEquippedItem();
			}
		}

		if(var7 && this.mc.thePlayer.canHarvestBlock(Block.blocksList[var5])) {
			Block.blocksList[var5].dropBlockAsItem(this.mc.theWorld, var1, var2, var3, var6);
		}

		return var7;
	}

	public void clickBlock(int var1, int var2, int var3, int var4) {
		this.isHittingBlock = true;
		this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, var1, var2, var3, var4));
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		if(var5 > 0 && this.curBlockDamageMP == 0.0F) {
			Block.blocksList[var5].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
		}

		if(var5 > 0 && Block.blocksList[var5].blockStrength(this.mc.thePlayer) >= 1.0F) {
			this.sendBlockRemoved(var1, var2, var3, var4);
		}

	}

	public void resetBlockRemoving() {
		if(this.isHittingBlock) {
			this.isHittingBlock = false;
			this.netClientHandler.addToSendQueue(new Packet14BlockDig(2, 0, 0, 0, 0));
			this.curBlockDamageMP = 0.0F;
			this.blockHitDelay = 0;
		}
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
		this.isHittingBlock = true;
		this.syncCurrentPlayItem();
		this.netClientHandler.addToSendQueue(new Packet14BlockDig(1, var1, var2, var3, var4));
		if(this.blockHitDelay > 0) {
			--this.blockHitDelay;
		} else {
			if(var1 == this.currentBlockX && var2 == this.currentBlockY && var3 == this.currentBlockZ) {
				int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.curBlockDamageMP += var6.blockStrength(this.mc.thePlayer);
				if(this.stepSoundTickCounter % 4.0F == 0.0F && var6 != null) {
					this.mc.sndManager.playSound(var6.stepSound.getStepSound(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 8.0F, var6.stepSound.getPitch() * 0.5F);
				}

				++this.stepSoundTickCounter;
				if(this.curBlockDamageMP >= 1.0F) {
					this.sendBlockRemoved(var1, var2, var3, var4);
					this.curBlockDamageMP = 0.0F;
					this.prevBlockDamageMP = 0.0F;
					this.stepSoundTickCounter = 0.0F;
					this.blockHitDelay = 5;
				}
			} else {
				this.curBlockDamageMP = 0.0F;
				this.prevBlockDamageMP = 0.0F;
				this.stepSoundTickCounter = 0.0F;
				this.currentBlockX = var1;
				this.currentBlockY = var2;
				this.currentBlockZ = var3;
			}

		}
	}

	public void setPartialTime(float var1) {
		if(this.curBlockDamageMP <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float var2 = this.prevBlockDamageMP + (this.curBlockDamageMP - this.prevBlockDamageMP) * var1;
			this.mc.ingameGUI.damageGuiPartialTime = var2;
			this.mc.renderGlobal.damagePartialTime = var2;
		}

	}

	public float getBlockReachDistance() {
		return 4.0F;
	}

	public void onWorldChange(World var1) {
		super.onWorldChange(var1);
	}

	public void onUpdate() {
		this.syncCurrentPlayItem();
		this.prevBlockDamageMP = this.curBlockDamageMP;
	}

	private void syncCurrentPlayItem() {
		ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();
		int var2 = 0;
		if(var1 != null) {
			var2 = var1.itemID;
		}

		if(var2 != this.currentPlayerItem) {
			this.currentPlayerItem = var2;
			this.netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(0, this.currentPlayerItem));
		}

	}

	public boolean onPlayerRightClick(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
		this.syncCurrentPlayItem();
		this.netClientHandler.addToSendQueue(new Packet15Place(var3 != null ? var3.itemID : -1, var4, var5, var6, var7));
		return super.onPlayerRightClick(var1, var2, var3, var4, var5, var6, var7);
	}

	public EntityPlayer createPlayer(World var1) {
		return new EntityClientPlayerMP(this.mc, var1, this.mc.session, this.netClientHandler);
	}
}
