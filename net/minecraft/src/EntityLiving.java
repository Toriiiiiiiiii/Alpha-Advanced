package net.minecraft.src;

import java.util.List;

public class EntityLiving extends Entity {
	public int heartsHalvesLife = 20;
	public float unusedRotationPitch2;
	public float unusedFloat;
	public float unusedRotationPitch;
	public float renderYawOffset = 0.0F;
	public float prevRenderYawOffset = 0.0F;
	protected float ridingRotUnused;
	protected float prevRidingRotUnused;
	protected float rotationUnused;
	protected float prevRotationUnused;
	protected boolean unusedBool1 = true;
	protected String texture = "/char.png";
	protected boolean unusedBool2 = true;
	protected float unusedRotation = 0.0F;
	protected String entityType = null;
	protected float unusedFloat1 = 1.0F;
	protected int scoreValue = 0;
	protected float unusedFloat2 = 0.0F;
	public boolean isAIEnabled = false;
	public float prevSwingProgress;
	public float swingProgress;
	public int health = 10;
	public int prevHealth;
	private int livingSoundTime;
	public int hurtTime;
	public int maxHurtTime;
	public float attackedAtYaw = 0.0F;
	public int deathTime = 0;
	public int attackTime = 0;
	public float prevCameraPitch;
	public float cameraPitch;
	protected boolean dead = false;
	public int unusedInt = -1;
	public float unusedFloat4 = (float)(Math.random() * (double)0.9F + (double)0.1F);
	public float prevLimbYaw;
	public float limbYaw;
	public float limbSwing;
	private int newPosRotationIncrements;
	private double newPosX;
	private double newPosY;
	private double newPosZ;
	private double newRotationYaw;
	private double newRotationPitch;
	float unusedFloat3 = 0.0F;
	protected int entityAge = 0;
	protected float moveStrafing;
	protected float moveForward;
	protected float randomYawVelocity;
	protected boolean isJumping = false;
	protected float defaultPitch = 0.0F;
	protected float moveSpeed = 0.7F;
	private Entity currentTarget;
	private int numTicksToChaseTarget = 0;

	public EntityLiving(World var1) {
		super(var1);
		this.preventEntitySpawning = true;
		this.unusedRotationPitch = (float)(Math.random() + 1.0D) * 0.01F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.unusedRotationPitch2 = (float)Math.random() * 12398.0F;
		this.rotationYaw = (float)(Math.random() * (double)((float)Math.PI) * 2.0D);
		this.unusedFloat = 1.0F;
		this.stepHeight = 0.5F;
	}

	protected boolean canEntityBeSeen(Entity var1) {
		return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3D.createVector(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
	}

	public String getTexture() {
		return this.texture;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public boolean canBePushed() {
		return !this.isDead;
	}

	protected float getEyeHeight() {
		return this.height * 0.85F;
	}

	public int getTalkInterval() {
		return 80;
	}

	public void onEntityUpdate() {
		this.prevSwingProgress = this.swingProgress;
		super.onEntityUpdate();
		if(this.rand.nextInt(1000) < this.livingSoundTime++) {
			this.livingSoundTime = -this.getTalkInterval();
			String var1 = this.getLivingSound();
			if(var1 != null) {
				this.worldObj.playSoundAtEntity(this, var1, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}
		}

		if(this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
			this.attackEntityFrom((Entity)null, 1);
		}

		int var8;
		if(this.isEntityAlive() && this.isInsideOfMaterial(Material.water)) {
			--this.air;
			if(this.air == -20) {
				this.air = 0;

				for(var8 = 0; var8 < 8; ++var8) {
					float var2 = this.rand.nextFloat() - this.rand.nextFloat();
					float var3 = this.rand.nextFloat() - this.rand.nextFloat();
					float var4 = this.rand.nextFloat() - this.rand.nextFloat();
					this.worldObj.spawnParticle("bubble", this.posX + (double)var2, this.posY + (double)var3, this.posZ + (double)var4, this.motionX, this.motionY, this.motionZ);
				}

				this.attackEntityFrom((Entity)null, 2);
			}

			this.fire = 0;
		} else {
			this.air = this.maxAir;
		}

		this.prevCameraPitch = this.cameraPitch;
		if(this.attackTime > 0) {
			--this.attackTime;
		}

		if(this.hurtTime > 0) {
			--this.hurtTime;
		}

		if(this.heartsLife > 0) {
			--this.heartsLife;
		}

		if(this.health <= 0) {
			++this.deathTime;
			if(this.deathTime > 20) {
				this.onEntityDeath();
				this.setEntityDead();

				for(var8 = 0; var8 < 20; ++var8) {
					double var9 = this.rand.nextGaussian() * 0.02D;
					double var10 = this.rand.nextGaussian() * 0.02D;
					double var6 = this.rand.nextGaussian() * 0.02D;
					this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var9, var10, var6);
				}
			}
		}

		this.prevRotationUnused = this.rotationUnused;
		this.prevRenderYawOffset = this.renderYawOffset;
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	public void spawnExplosionParticle() {
		for(int var1 = 0; var1 < 20; ++var1) {
			double var2 = this.rand.nextGaussian() * 0.02D;
			double var4 = this.rand.nextGaussian() * 0.02D;
			double var6 = this.rand.nextGaussian() * 0.02D;
			double var8 = 10.0D;
			this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var6 * var8, var2, var4, var6);
		}

	}

	public void updateRidden() {
		super.updateRidden();
		this.ridingRotUnused = this.prevRidingRotUnused;
		this.prevRidingRotUnused = 0.0F;
	}

	public void setPositionAndRotation(double var1, double var3, double var5, float var7, float var8, int var9) {
		this.yOffset = 0.0F;
		this.newPosX = var1;
		this.newPosY = var3;
		this.newPosZ = var5;
		this.newRotationYaw = (double)var7;
		this.newRotationPitch = (double)var8;
		this.newPosRotationIncrements = var9;
	}

	public void onUpdate() {
		super.onUpdate();
		this.onLivingUpdate();
		double var1 = this.posX - this.prevPosX;
		double var3 = this.posZ - this.prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		float var6 = this.renderYawOffset;
		float var7 = 0.0F;
		this.ridingRotUnused = this.prevRidingRotUnused;
		float var8 = 0.0F;
		if(var5 > 0.05F) {
			var8 = 1.0F;
			var7 = var5 * 3.0F;
			var6 = (float)Math.atan2(var3, var1) * 180.0F / (float)Math.PI - 90.0F;
		}

		if(this.swingProgress > 0.0F) {
			var6 = this.rotationYaw;
		}

		if(!this.onGround) {
			var8 = 0.0F;
		}

		this.prevRidingRotUnused += (var8 - this.prevRidingRotUnused) * 0.3F;

		float var9;
		for(var9 = var6 - this.renderYawOffset; var9 < -180.0F; var9 += 360.0F) {
		}

		while(var9 >= 180.0F) {
			var9 -= 360.0F;
		}

		this.renderYawOffset += var9 * 0.3F;

		float var10;
		for(var10 = this.rotationYaw - this.renderYawOffset; var10 < -180.0F; var10 += 360.0F) {
		}

		while(var10 >= 180.0F) {
			var10 -= 360.0F;
		}

		boolean var11 = var10 < -90.0F || var10 >= 90.0F;
		if(var10 < -75.0F) {
			var10 = -75.0F;
		}

		if(var10 >= 75.0F) {
			var10 = 75.0F;
		}

		this.renderYawOffset = this.rotationYaw - var10;
		if(var10 * var10 > 2500.0F) {
			this.renderYawOffset += var10 * 0.2F;
		}

		if(var11) {
			var7 *= -1.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
			this.prevRenderYawOffset -= 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
			this.prevRenderYawOffset += 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		this.rotationUnused += var7;
	}

	protected void setSize(float var1, float var2) {
		super.setSize(var1, var2);
	}

	public void heal(int var1) {
		if(this.health > 0) {
			this.health += var1;
			if(this.health > 20) {
				this.health = 20;
			}

			this.heartsLife = this.heartsHalvesLife / 2;
		}
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		if(this.worldObj.multiplayerWorld) {
			var2 = 0;
		}

		this.entityAge = 0;
		if(this.health <= 0) {
			return false;
		} else {
			this.limbYaw = 1.5F;
			if((float)this.heartsLife > (float)this.heartsHalvesLife / 2.0F) {
				if(this.prevHealth - var2 >= this.health) {
					return false;
				}

				this.health = this.prevHealth - var2;
			} else {
				this.prevHealth = this.health;
				this.heartsLife = this.heartsHalvesLife;
				this.health -= var2;
				this.hurtTime = this.maxHurtTime = 10;
			}

			this.attackedAtYaw = 0.0F;
			if(var1 != null) {
				double var3 = var1.posX - this.posX;

				double var5;
				for(var5 = var1.posZ - this.posZ; var3 * var3 + var5 * var5 < 1.0E-4D; var5 = (Math.random() - Math.random()) * 0.01D) {
					var3 = (Math.random() - Math.random()) * 0.01D;
				}

				this.attackedAtYaw = (float)(Math.atan2(var5, var3) * 180.0D / (double)((float)Math.PI)) - this.rotationYaw;
				this.knockBack(var1, var2, var3, var5);
			} else {
				this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
			}

			if(this.health <= 0) {
				this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.onDeath(var1);
			} else {
				this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			return true;
		}
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return "random.hurt";
	}

	protected String getDeathSound() {
		return "random.hurt";
	}

	public void knockBack(Entity var1, int var2, double var3, double var5) {
		float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
		float var8 = 0.4F;
		this.motionX /= 2.0D;
		this.motionY /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= var3 / (double)var7 * (double)var8;
		this.motionY += (double)0.4F;
		this.motionZ -= var5 / (double)var7 * (double)var8;
		if(this.motionY > (double)0.4F) {
			this.motionY = (double)0.4F;
		}

	}

	public void onDeath(Entity var1) {
		if(this.scoreValue > 0 && var1 != null) {
			var1.addToPlayerScore(this, this.scoreValue);
		}

		this.dead = true;
		int var2 = this.getDropItemId();
		if(var2 > 0) {
			int var3 = this.rand.nextInt(3);

			for(int var4 = 0; var4 < var3; ++var4) {
				this.dropItem(var2, 1);
			}
		}

	}

	protected int getDropItemId() {
		return 0;
	}

	protected void fall(float var1) {
		int var2 = (int)Math.ceil((double)(var1 - 3.0F));
		if(var2 > 0) {
			this.attackEntityFrom((Entity)null, var2);
			int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset), MathHelper.floor_double(this.posZ));
			if(var3 > 0) {
				StepSound var4 = Block.blocksList[var3].stepSound;
				this.worldObj.playSoundAtEntity(this, var4.getStepSound(), var4.getVolume() * 0.5F, var4.getPitch() * (12.0F / 16.0F));
			}
		}

	}

	public void moveEntityWithHeading(float var1, float var2) {
		double var3;
		if(this.handleWaterMovement()) {
			var3 = this.posY;
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else if(this.handleLavaMovement()) {
			var3 = this.posY;
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else {
			float var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var4 > 0) {
					var8 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}

			float var9 = 0.16277136F / (var8 * var8 * var8);
			this.moveFlying(var1, var2, this.onGround ? 0.1F * var9 : 0.02F);
			var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var5 > 0) {
					var8 = Block.blocksList[var5].slipperiness * 0.91F;
				}
			}

			if(this.isOnLadder()) {
				this.fallDistance = 0.0F;
				if(this.motionY < -0.15D) {
					this.motionY = -0.15D;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			if(this.isCollidedHorizontally && this.isOnLadder()) {
				this.motionY = 0.2D;
			}

			this.motionY -= 0.08D;
			this.motionY *= (double)0.98F;
			this.motionX *= (double)var8;
			this.motionZ *= (double)var8;
		}

		this.prevLimbYaw = this.limbYaw;
		var3 = this.posX - this.prevPosX;
		double var10 = this.posZ - this.prevPosZ;
		float var7 = MathHelper.sqrt_double(var3 * var3 + var10 * var10) * 4.0F;
		if(var7 > 1.0F) {
			var7 = 1.0F;
		}

		this.limbYaw += (var7 - this.limbYaw) * 0.4F;
		this.limbSwing += this.limbYaw;
	}

	public boolean isOnLadder() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getBlockId(var1, var2, var3) == Block.ladder.blockID || this.worldObj.getBlockId(var1, var2 + 1, var3) == Block.ladder.blockID;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("Health", (short)this.health);
		var1.setShort("HurtTime", (short)this.hurtTime);
		var1.setShort("DeathTime", (short)this.deathTime);
		var1.setShort("AttackTime", (short)this.attackTime);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.health = var1.getShort("Health");
		if(!var1.hasKey("Health")) {
			this.health = 10;
		}

		this.hurtTime = var1.getShort("HurtTime");
		this.deathTime = var1.getShort("DeathTime");
		this.attackTime = var1.getShort("AttackTime");
	}

	public boolean isEntityAlive() {
		return !this.isDead && this.health > 0;
	}

	public void onLivingUpdate() {
		if(this.newPosRotationIncrements > 0) {
			double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
			double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
			double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;

			double var7;
			for(var7 = this.newRotationYaw - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
			}

			while(var7 >= 180.0D) {
				var7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(var1, var3, var5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		if(this.health <= 0) {
			this.isJumping = false;
			this.moveStrafing = 0.0F;
			this.moveForward = 0.0F;
			this.randomYawVelocity = 0.0F;
		} else if(!this.isAIEnabled) {
			this.updateEntityActionState();
		}

		boolean var9 = this.handleWaterMovement();
		boolean var2 = this.handleLavaMovement();
		if(this.isJumping) {
			if(var9) {
				this.motionY += (double)0.04F;
			} else if(var2) {
				this.motionY += (double)0.04F;
			} else if(this.onGround) {
				this.jump();
			}
		}

		this.moveStrafing *= 0.98F;
		this.moveForward *= 0.98F;
		this.randomYawVelocity *= 0.9F;
		this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
		List var10 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
		if(var10 != null && var10.size() > 0) {
			for(int var4 = 0; var4 < var10.size(); ++var4) {
				Entity var11 = (Entity)var10.get(var4);
				if(var11.canBePushed()) {
					var11.applyEntityCollision(this);
				}
			}
		}

	}

	protected void jump() {
		this.motionY = (double)0.42F;
	}

	protected void updateEntityActionState() {
		++this.entityAge;
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		if(var1 != null) {
			double var2 = var1.posX - this.posX;
			double var4 = var1.posY - this.posY;
			double var6 = var1.posZ - this.posZ;
			double var8 = var2 * var2 + var4 * var4 + var6 * var6;
			if(var8 > 16384.0D) {
				this.setEntityDead();
			}

			if(this.entityAge > 600 && this.rand.nextInt(800) == 0) {
				if(var8 < 1024.0D) {
					this.entityAge = 0;
				} else {
					this.setEntityDead();
				}
			}
		}

		this.moveStrafing = 0.0F;
		this.moveForward = 0.0F;
		float var10 = 8.0F;
		if(this.rand.nextFloat() < 0.02F) {
			var1 = this.worldObj.getClosestPlayerToEntity(this, (double)var10);
			if(var1 != null) {
				this.currentTarget = var1;
				this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
			} else {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}
		}

		if(this.currentTarget != null) {
			this.faceEntity(this.currentTarget, 10.0F);
			if(this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(var10 * var10)) {
				this.currentTarget = null;
			}
		} else {
			if(this.rand.nextFloat() < 0.05F) {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}

			this.rotationYaw += this.randomYawVelocity;
			this.rotationPitch = this.defaultPitch;
		}

		boolean var3 = this.handleWaterMovement();
		boolean var11 = this.handleLavaMovement();
		if(var3 || var11) {
			this.isJumping = this.rand.nextFloat() < 0.8F;
		}

	}

	public void faceEntity(Entity var1, float var2) {
		double var3 = var1.posX - this.posX;
		double var7 = var1.posZ - this.posZ;
		double var5;
		if(var1 instanceof EntityLiving) {
			EntityLiving var9 = (EntityLiving)var1;
			var5 = var9.posY + (double)var9.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
		} else {
			var5 = (var1.boundingBox.minY + var1.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
		}

		double var13 = (double)MathHelper.sqrt_double(var3 * var3 + var7 * var7);
		float var11 = (float)(Math.atan2(var7, var3) * 180.0D / (double)((float)Math.PI)) - 90.0F;
		float var12 = (float)(Math.atan2(var5, var13) * 180.0D / (double)((float)Math.PI));
		this.rotationPitch = this.updateRotation(this.rotationPitch, var12, var2);
		this.rotationYaw = this.updateRotation(this.rotationYaw, var11, var2);
	}

	private float updateRotation(float var1, float var2, float var3) {
		float var4;
		for(var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
		}

		while(var4 >= 180.0F) {
			var4 -= 360.0F;
		}

		if(var4 > var3) {
			var4 = var3;
		}

		if(var4 < -var3) {
			var4 = -var3;
		}

		return var1 + var4;
	}

	public void onEntityDeath() {
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	protected void kill() {
		this.attackEntityFrom((Entity)null, 4);
	}

	public float getSwingProgress(float var1) {
		float var2 = this.swingProgress - this.prevSwingProgress;
		if(var2 < 0.0F) {
			++var2;
		}

		return this.prevSwingProgress + var2 * var1;
	}

	public Vec3D getPosition(float var1) {
		if(var1 == 1.0F) {
			return Vec3D.createVector(this.posX, this.posY, this.posZ);
		} else {
			double var2 = this.prevPosX + (this.posX - this.prevPosX) * (double)var1;
			double var4 = this.prevPosY + (this.posY - this.prevPosY) * (double)var1;
			double var6 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var1;
			return Vec3D.createVector(var2, var4, var6);
		}
	}

	public Vec3D getLook(float var1) {
		float var2;
		float var3;
		float var4;
		float var5;
		if(var1 == 1.0F) {
			var2 = MathHelper.cos(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var3 = MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var4 = -MathHelper.cos(-this.rotationPitch * ((float)Math.PI / 180.0F));
			var5 = MathHelper.sin(-this.rotationPitch * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var3 * var4), (double)var5, (double)(var2 * var4));
		} else {
			var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * var1;
			var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * var1;
			var4 = MathHelper.cos(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var5 = MathHelper.sin(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			float var6 = -MathHelper.cos(-var2 * ((float)Math.PI / 180.0F));
			float var7 = MathHelper.sin(-var2 * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var5 * var6), (double)var7, (double)(var4 * var6));
		}
	}

	public MovingObjectPosition rayTrace(double var1, float var3) {
		Vec3D var4 = this.getPosition(var3);
		Vec3D var5 = this.getLook(var3);
		Vec3D var6 = var4.addVector(var5.xCoord * var1, var5.yCoord * var1, var5.zCoord * var1);
		return this.worldObj.rayTraceBlocks(var4, var6);
	}
}
