package net.minecraft.src;

public class EntitySheep extends EntityAnimal {
	public boolean sheared = false;

	public EntitySheep(World var1) {
		super(var1);
		this.texture = "/mob/sheep.png";
		this.setSize(0.9F, 1.3F);
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		if(!this.sheared && var1 instanceof EntityLiving) {
			this.sheared = true;
			int var3 = 1 + this.rand.nextInt(1);

			for(int var4 = 0; var4 < var3; ++var4) {
				EntityItem var5 = this.entityDropItem(Block.cloth.blockID, 1, 1.0F);
				var5.motionY += (double)(this.rand.nextFloat() * 0.05F);
				var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
			}
		}

		return super.attackEntityFrom(var1, var2);
	}
	
	public boolean interact(EntityPlayer var1) {
		ItemStack var2 = var1.inventory.getCurrentItem();
		if(var2 != null && !this.sheared && var2.itemID == Item.shears.shiftedIndex) {
			this.sheared = true;
			var2.damageItem(1);
			int var3 = 3 + this.rand.nextInt(1);

			for(int var4 = 0; var4 < var3; ++var4) {
				EntityItem var5 = this.entityDropItem(Block.cloth.blockID, 1, 1.0F);
				var5.motionY += (double)(this.rand.nextFloat() * 0.05F);
				var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
			}
			
			return true;
		} else {
			return false;
		}
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Sheared", this.sheared);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.sheared = var1.getBoolean("Sheared");
	}

	protected String getLivingSound() {
		return "mob.sheep";
	}

	protected String getHurtSound() {
		return "mob.sheep";
	}

	protected String getDeathSound() {
		return "mob.sheep";
	}
}
