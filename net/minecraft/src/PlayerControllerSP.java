package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerSP extends PlayerController {
	private int curBlockX = -1;
	private int curBlockY = -1;
	private int curBlockZ = -1;
	private float curBlockDamage = 0.0F;
	private float prevBlockDamage = 0.0F;
	private float blockDestroySoundCounter = 0.0F;
	private int blockHitWait = 0;
	private SpawnerAnimals monsterSpawner = new SpawnerClient(this, 200, IMobs.class, new Class[]{EntityZombie.class, EntitySkeleton.class, EntityCreeper.class, EntitySpider.class, EntitySlime.class});
	private SpawnerAnimals animalSpawner = new SpawnerAnimals(15, EntityAnimal.class, new Class[]{EntitySheep.class, EntityPig.class, EntityCow.class, EntityChicken.class});

	public PlayerControllerSP(Minecraft var1) {
		super(var1);
	}

	public void flipPlayer(EntityPlayer var1) {
		var1.rotationYaw = -180.0F;
	}

	public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		int var6 = this.mc.theWorld.getBlockMetadata(var1, var2, var3);
		boolean var7 = super.sendBlockRemoved(var1, var2, var3, var4);
		ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
		boolean var9 = this.mc.thePlayer.canHarvestBlock(Block.blocksList[var5]);
		if(var8 != null) {
			var8.onDestroyBlock(var5, var1, var2, var3);
			if(var8.stackSize == 0) {
				var8.onItemDestroyedByUse(this.mc.thePlayer);
				this.mc.thePlayer.destroyCurrentEquippedItem();
			}
		}

		if(var7 && var9) {
			Block.blocksList[var5].harvestBlock(this.mc.theWorld, var1, var2, var3, var6);
		}

		return var7;
	}

	public void clickBlock(int var1, int var2, int var3, int var4) {
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		if(var5 > 0 && this.curBlockDamage == 0.0F) {
			Block.blocksList[var5].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
		}

		if(var5 > 0 && Block.blocksList[var5].blockStrength(this.mc.thePlayer) >= 1.0F) {
			this.sendBlockRemoved(var1, var2, var3, var4);
		}

	}

	public void resetBlockRemoving() {
		this.curBlockDamage = 0.0F;
		this.blockHitWait = 0;
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
		if(this.blockHitWait > 0) {
			--this.blockHitWait;
		} else {
			if(var1 == this.curBlockX && var2 == this.curBlockY && var3 == this.curBlockZ) {
				int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.curBlockDamage += var6.blockStrength(this.mc.thePlayer);
				if(this.blockDestroySoundCounter % 4.0F == 0.0F && var6 != null) {
					this.mc.sndManager.playSound(var6.stepSound.getStepSound(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 8.0F, var6.stepSound.getPitch() * 0.5F);
				}

				++this.blockDestroySoundCounter;
				if(this.curBlockDamage >= 1.0F) {
					this.sendBlockRemoved(var1, var2, var3, var4);
					this.curBlockDamage = 0.0F;
					this.prevBlockDamage = 0.0F;
					this.blockDestroySoundCounter = 0.0F;
					this.blockHitWait = 5;
				}
			} else {
				this.curBlockDamage = 0.0F;
				this.prevBlockDamage = 0.0F;
				this.blockDestroySoundCounter = 0.0F;
				this.curBlockX = var1;
				this.curBlockY = var2;
				this.curBlockZ = var3;
			}

		}
	}

	public void setPartialTime(float var1) {
		if(this.curBlockDamage <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float var2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * var1;
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
		this.prevBlockDamage = this.curBlockDamage;
		this.monsterSpawner.onUpdate(this.mc.theWorld);
		this.animalSpawner.onUpdate(this.mc.theWorld);
		this.mc.sndManager.playRandomMusicIfReady();
	}
}
