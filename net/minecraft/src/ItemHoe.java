package net.minecraft.src;

public class ItemHoe extends Item {
	public ItemHoe(int var1, int var2) {
		super(var1);
		this.maxStackSize = 1;
		this.maxDamage = 32 << var2;
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		int var8 = var3.getBlockId(var4, var5, var6);
		Material var9 = var3.getBlockMaterial(var4, var5 + 1, var6);
		if((var9.isSolid() || var8 != Block.grass.blockID) && var8 != Block.dirt.blockID) {
			return false;
		} else {
			Block var10 = Block.tilledField;
			var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);
			var3.setBlockWithNotify(var4, var5, var6, var10.blockID);
			var1.damageItem(1);
			if(var3.rand.nextInt(4) == 0 && var8 == Block.grass.blockID) {
				byte var11 = 1;

				for(int var12 = 0; var12 < var11; ++var12) {
					float var13 = 0.7F;
					float var14 = var3.rand.nextFloat() * var13 + (1.0F - var13) * 0.5F;
					float var15 = 1.2F;
					float var16 = var3.rand.nextFloat() * var13 + (1.0F - var13) * 0.5F;
					EntityItem var17 = new EntityItem(var3, (double)((float)var4 + var14), (double)((float)var5 + var15), (double)((float)var6 + var16), new ItemStack(Item.seeds));
					var17.delayBeforeCanPickup = 10;
					var3.spawnEntityInWorld(var17);
				}
			}

			return true;
		}
	}

	public boolean isFull3D() {
		return true;
	}
}
