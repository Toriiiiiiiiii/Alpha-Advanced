package net.minecraft.src;

import java.util.List;

public class EntityBoat extends Entity {
	public int damageTaken;
	public int timeSinceHit;
	public int forwardDirection;

	public EntityBoat(World var1) {
		super(var1);
		this.damageTaken = 0;
		this.timeSinceHit = 0;
		this.forwardDirection = 1;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
		this.canTriggerWalking = false;
	}

	public AxisAlignedBB getCollisionBox(Entity var1) {
		return var1.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public EntityBoat(World var1, double var2, double var4, double var6) {
		this(var1);
		this.setPosition(var2, var4 + (double)this.yOffset, var6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = var2;
		this.prevPosY = var4;
		this.prevPosZ = var6;
	}

	public double getMountedYOffset() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		this.forwardDirection = -this.forwardDirection;
		this.timeSinceHit = 10;
		this.damageTaken += var2 * 10;
		if(this.damageTaken > 40) {
			this.entityDropItem(Item.boat.shiftedIndex, 1, 0.0F);
			this.setEntityDead();
		}

		return true;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.timeSinceHit > 0) {
			--this.timeSinceHit;
		}

		if(this.damageTaken > 0) {
			--this.damageTaken;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte var1 = 5;
		double var2 = 0.0D;

		for(int var4 = 0; var4 < var1; ++var4) {
			double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
			double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
			AxisAlignedBB var9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
			if(this.worldObj.isAABBInMaterial(var9, Material.water)) {
				var2 += 1.0D / (double)var1;
			}
		}

		double var23 = var2 * 2.0D - 1.0D;
		this.motionY += (double)0.04F * var23;
		if(this.riddenByEntity != null) {
			this.motionX += this.riddenByEntity.motionX * 0.8D;
			this.motionZ += this.riddenByEntity.motionZ * 0.8D;
		}

		double var6 = 0.4D;
		if(this.motionX < -var6) {
			this.motionX = -var6;
		}

		if(this.motionX > var6) {
			this.motionX = var6;
		}

		if(this.motionZ < -var6) {
			this.motionZ = -var6;
		}

		if(this.motionZ > var6) {
			this.motionZ = var6;
		}

		if(this.onGround) {
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		double var8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double var10;
		double var12;
		if(var8 > 0.15D) {
			var10 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
			var12 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

			for(int var14 = 0; (double)var14 < 1.0D + var8 * 60.0D; ++var14) {
				double var15 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
				double var17 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double var19;
				double var21;
				if(this.rand.nextBoolean()) {
					var19 = this.posX - var10 * var15 * 0.8D + var12 * var17;
					var21 = this.posZ - var12 * var15 * 0.8D - var10 * var17;
					this.worldObj.spawnParticle("splash", var19, this.posY - 0.125D, var21, this.motionX, this.motionY, this.motionZ);
				} else {
					var19 = this.posX + var10 + var12 * var15 * 0.7D;
					var21 = this.posZ + var12 - var10 * var15 * 0.7D;
					this.worldObj.spawnParticle("splash", var19, this.posY - 0.125D, var21, this.motionX, this.motionY, this.motionZ);
				}
			}
		}


		this.motionX *= (double)0.99F;
		this.motionY *= (double)0.95F;
		this.motionZ *= (double)0.99F;

		this.rotationPitch = 0.0F;
		var10 = (double)this.rotationYaw;
		var12 = this.prevPosX - this.posX;
		double var25 = this.prevPosZ - this.posZ;
		if(var12 * var12 + var25 * var25 > 0.001D) {
			var10 = (double)((float)(Math.atan2(var25, var12) * 180.0D / Math.PI));
		}

		double var16;
		for(var16 = var10 - (double)this.rotationYaw; var16 >= 180.0D; var16 -= 360.0D) {
		}

		while(var16 < -180.0D) {
			var16 += 360.0D;
		}

		if(var16 > 20.0D) {
			var16 = 20.0D;
		}

		if(var16 < -20.0D) {
			var16 = -20.0D;
		}

		this.rotationYaw = (float)((double)this.rotationYaw + var16);
		this.setRotation(this.rotationYaw, this.rotationPitch);
		List var18 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
		if(var18 != null && var18.size() > 0) {
			for(int var26 = 0; var26 < var18.size(); ++var26) {
				Entity var20 = (Entity)var18.get(var26);
				if(var20 != this.riddenByEntity && var20.canBePushed() && var20 instanceof EntityBoat) {
					var20.applyEntityCollision(this);
				}
			}
		}

		if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
			this.riddenByEntity = null;
		}

	}

	protected void updateRiderPosition() {
		double var1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
		double var3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
		this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public boolean interact(EntityPlayer var1) {
		var1.mountEntity(this);
		return true;
	}
}
