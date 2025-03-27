package net.minecraft.src;

public class EntitySlime extends EntityLiving implements IMobs {
	public float squishFactor;
	public float prevSquishFactor;
	private int slimeJumpDelay = 0;
	public int size = 1;

	public EntitySlime(World var1) {
		super(var1);
		this.texture = "/mob/slime.png";
		this.size = 1 << this.rand.nextInt(3);
		this.yOffset = 0.0F;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;
		this.setSlimeSize(this.size);
	}

	public void setSlimeSize(int var1) {
		this.size = var1;
		this.setSize(0.6F * (float)var1, 0.6F * (float)var1);
		this.health = var1 * var1;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setInteger("Size", this.size - 1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.size = var1.getInteger("Size") + 1;
	}

	public void onUpdate() {
		this.prevSquishFactor = this.squishFactor;
		boolean var1 = this.onGround;
		super.onUpdate();
		if(this.onGround && !var1) {
			for(int var2 = 0; var2 < this.size * 8; ++var2) {
				float var3 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var4 = this.rand.nextFloat() * 0.5F + 0.5F;
				float var5 = MathHelper.sin(var3) * (float)this.size * 0.5F * var4;
				float var6 = MathHelper.cos(var3) * (float)this.size * 0.5F * var4;
				this.worldObj.spawnParticle("slime", this.posX + (double)var5, this.boundingBox.minY, this.posZ + (double)var6, 0.0D, 0.0D, 0.0D);
			}

			if(this.size > 2) {
				this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}

			this.squishFactor = -0.5F;
		}

		this.squishFactor *= 0.6F;
	}

	protected void updateEntityActionState() {
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
		if(var1 != null) {
			this.faceEntity(var1, 10.0F);
		}

		if(this.onGround && this.slimeJumpDelay-- <= 0) {
			this.slimeJumpDelay = this.rand.nextInt(20) + 10;
			if(var1 != null) {
				this.slimeJumpDelay /= 3;
			}

			this.isJumping = true;
			if(this.size > 1) {
				this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}

			this.squishFactor = 1.0F;
			this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
			this.moveForward = (float)(1 * this.size);
		} else {
			this.isJumping = false;
			if(this.onGround) {
				this.moveStrafing = this.moveForward = 0.0F;
			}
		}

	}

	public void setEntityDead() {
		if(this.size > 1 && this.health == 0) {
			for(int var1 = 0; var1 < 4; ++var1) {
				float var2 = ((float)(var1 % 2) - 0.5F) * (float)this.size / 4.0F;
				float var3 = ((float)(var1 / 2) - 0.5F) * (float)this.size / 4.0F;
				EntitySlime var4 = new EntitySlime(this.worldObj);
				var4.setSlimeSize(this.size / 2);
				var4.setLocationAndAngles(this.posX + (double)var2, this.posY + 0.5D, this.posZ + (double)var3, this.rand.nextFloat() * 360.0F, 0.0F);
				this.worldObj.spawnEntityInWorld(var4);
			}
		}

		super.setEntityDead();
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
		if(this.size > 1 && this.canEntityBeSeen(var1) && (double)this.getDistanceToEntity(var1) < 0.6D * (double)this.size && var1.attackEntityFrom(this, this.size)) {
			this.worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}

	}

	protected String getHurtSound() {
		return "mob.slime";
	}

	protected String getDeathSound() {
		return "mob.slime";
	}

	protected int getDropItemId() {
		return this.size == 1 ? Item.slimeBall.shiftedIndex : 0;
	}

	public boolean getCanSpawnHere() {
		Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY));
		return (this.size == 1 || this.worldObj.difficultySetting > 0) && this.rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 16.0D;
	}

	protected float getSoundVolume() {
		return 0.6F;
	}
}
