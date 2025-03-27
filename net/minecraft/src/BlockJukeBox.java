package net.minecraft.src;

public class BlockJukeBox extends Block {
	protected BlockJukeBox(int var1, int var2) {
		super(var1, var2, Material.wood);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.blockIndexInTexture + (var1 == 1 ? 1 : 0);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if(var6 > 0) {
			this.ejectRecord(var1, var2, var3, var4, var6);
			return true;
		} else {
			return false;
		}
	}

	public void ejectRecord(World var1, int var2, int var3, int var4, int var5) {
		var1.playRecord((String)null, var2, var3, var4);
		var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
		int var6 = Item.record13.shiftedIndex + var5 - 1;
		float var7 = 0.7F;
		double var8 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		double var10 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.2D + 0.6D;
		double var12 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		EntityItem var14 = new EntityItem(var1, (double)var2 + var8, (double)var3 + var10, (double)var4 + var12, new ItemStack(var6));
		var14.delayBeforeCanPickup = 10;
		var1.spawnEntityInWorld(var14);
	}

	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
		if(!var1.multiplayerWorld) {
			if(var5 > 0) {
				this.ejectRecord(var1, var2, var3, var4, var5);
			}

			super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6);
		}
	}
}
