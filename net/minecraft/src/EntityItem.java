package net.minecraft.src;

public class EntityItem extends Entity {
	public ItemStack item;
	private int age2;
	public int age = 0;
	public int delayBeforeCanPickup;
	private int health = 5;
	public float hoverStart = (float)(Math.random() * Math.PI * 2.0D);

	public EntityItem(World var1, double var2, double var4, double var6, ItemStack var8) {
		super(var1);
		this.setSize(0.25F, 0.25F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(var2, var4, var6);
		this.item = var8;
		this.rotationYaw = (float)(Math.random() * 360.0D);
		this.motionX = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
		this.motionY = (double)0.2F;
		this.motionZ = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
		this.canTriggerWalking = false;
	}

	public EntityItem(World var1) {
		super(var1);
		this.setSize(0.25F, 0.25F);
		this.yOffset = this.height / 2.0F;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.delayBeforeCanPickup > 0) {
			--this.delayBeforeCanPickup;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= (double)0.04F;
		if(this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
			this.motionY = (double)0.2F;
			this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.worldObj.playSoundAtEntity(this, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
		}

		this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
		this.handleWaterMovement();
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		float var1 = 0.98F;
		if(this.onGround) {
			var1 = 0.1F * 0.1F * 58.8F;
			int var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
			if(var2 > 0) {
				var1 = Block.blocksList[var2].slipperiness * 0.98F;
			}
		}

		this.motionX *= (double)var1;
		this.motionY *= (double)0.98F;
		this.motionZ *= (double)var1;
		if(this.onGround) {
			this.motionY *= -0.5D;
		}

		++this.age2;
		++this.age;
		if(this.age >= 6000) {
			this.setEntityDead();
		}

	}

	public boolean handleWaterMovement() {
		return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
	}

	private boolean pushOutOfBlocks(double var1, double var3, double var5) {
		int var7 = MathHelper.floor_double(var1);
		int var8 = MathHelper.floor_double(var3);
		int var9 = MathHelper.floor_double(var5);
		double var10 = var1 - (double)var7;
		double var12 = var3 - (double)var8;
		double var14 = var5 - (double)var9;
		if(Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9)]) {
			boolean var16 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7 - 1, var8, var9)];
			boolean var17 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7 + 1, var8, var9)];
			boolean var18 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8 - 1, var9)];
			boolean var19 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8 + 1, var9)];
			boolean var20 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9 - 1)];
			boolean var21 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9 + 1)];
			byte var22 = -1;
			double var23 = 9999.0D;
			if(var16 && var10 < var23) {
				var23 = var10;
				var22 = 0;
			}

			if(var17 && 1.0D - var10 < var23) {
				var23 = 1.0D - var10;
				var22 = 1;
			}

			if(var18 && var12 < var23) {
				var23 = var12;
				var22 = 2;
			}

			if(var19 && 1.0D - var12 < var23) {
				var23 = 1.0D - var12;
				var22 = 3;
			}

			if(var20 && var14 < var23) {
				var23 = var14;
				var22 = 4;
			}

			if(var21 && 1.0D - var14 < var23) {
				var23 = 1.0D - var14;
				var22 = 5;
			}

			float var25 = this.rand.nextFloat() * 0.2F + 0.1F;
			if(var22 == 0) {
				this.motionX = (double)(-var25);
			}

			if(var22 == 1) {
				this.motionX = (double)var25;
			}

			if(var22 == 2) {
				this.motionY = (double)(-var25);
			}

			if(var22 == 3) {
				this.motionY = (double)var25;
			}

			if(var22 == 4) {
				this.motionZ = (double)(-var25);
			}

			if(var22 == 5) {
				this.motionZ = (double)var25;
			}
		}

		return false;
	}

	protected void dealFireDamage(int var1) {
		this.attackEntityFrom((Entity)null, var1);
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		this.health -= var2;
		if(this.health <= 0) {
			this.setEntityDead();
		}

		return false;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("Health", (short)((byte)this.health));
		var1.setShort("Age", (short)this.age);
		var1.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.health = var1.getShort("Health") & 255;
		this.age = var1.getShort("Age");
		NBTTagCompound var2 = var1.getCompoundTag("Item");
		this.item = new ItemStack(var2);
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
		if(!this.worldObj.multiplayerWorld) {
			int var2 = this.item.stackSize;
			if(this.delayBeforeCanPickup == 0 && var1.inventory.addItemStackToInventory(this.item)) {
				this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				var1.onItemPickup(this, var2);
				this.setEntityDead();
			}

		}
	}
}
