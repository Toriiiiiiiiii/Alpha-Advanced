package net.minecraft.src;

import java.util.Random;

public class BlockTNT extends Block {
	public BlockTNT(int var1, int var2) {
		super(var1, var2, Material.tnt);
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 0 ? this.blockIndexInTexture + 2 : (var1 == 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(var5 > 0 && Block.blocksList[var5].canProvidePower() && var1.isBlockIndirectlyGettingPowered(var2, var3, var4)) {
			this.onBlockDestroyedByPlayer(var1, var2, var3, var4, 0);
			var1.setBlockWithNotify(var2, var3, var4, 0);
		}

	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
		EntityTNTPrimed var5 = new EntityTNTPrimed(var1, (float)var2 + 0.5F, (float)var3 + 0.5F, (float)var4 + 0.5F);
		var5.fuse = var1.rand.nextInt(var5.fuse / 4) + var5.fuse / 8;
		var1.spawnEntityInWorld(var5);
	}

	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
		EntityTNTPrimed var6 = new EntityTNTPrimed(var1, (float)var2 + 0.5F, (float)var3 + 0.5F, (float)var4 + 0.5F);
		var1.spawnEntityInWorld(var6);
		var1.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
	}
}
