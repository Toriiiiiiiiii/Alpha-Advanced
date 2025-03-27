package net.minecraft.src;

import java.util.Random;

public class EntityPig extends EntityAnimal {
	public boolean saddled = false;

	public EntityPig(World var1) {
		super(var1);
		
		Random rand = new Random();
		
		if(rand.nextInt(2) == 0)
			this.texture = "/mob/pig.png";
		else
			this.texture = "/mob/pig2.png";
		
		this.setSize(0.9F, 0.9F);
		this.saddled = false;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Saddle", this.saddled);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.saddled = var1.getBoolean("Saddle");
	}

	protected String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public boolean interact(EntityPlayer var1) {
		if(this.saddled) {
			var1.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	protected int getDropItemId() {
		return Item.porkRaw.shiftedIndex;
	}
}
