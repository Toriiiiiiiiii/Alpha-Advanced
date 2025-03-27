package net.minecraft.src;

public class ItemSaddle extends Item {
	public ItemSaddle(int var1) {
		super(var1);
		this.maxStackSize = 1;
		this.maxDamage = 64;
	}

	public void saddleEntity(ItemStack var1, EntityLiving var2) {
		if(var2 instanceof EntityPig) {
			EntityPig var3 = (EntityPig)var2;
			if(!var3.saddled) {
				var3.saddled = true;
				--var1.stackSize;
			}
		}

	}

	public void hitEntity(ItemStack var1, EntityLiving var2) {
		this.saddleEntity(var1, var2);
	}
}
