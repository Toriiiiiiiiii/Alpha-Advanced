package net.minecraft.src;

import java.util.Random;

public class BlockSapling extends BlockFlower {
	protected BlockSapling(int var1, int var2) {
		super(var1, var2);
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if(var5.inventory.getCurrentItem() == null) return true;
		
		if(var5.inventory.getCurrentItem().itemID == Item.fertilizer.shiftedIndex) {
			Random rand = new Random();
		
			var1.setBlock(var2, var3, var4, 0);
			Object var7 = new WorldGenTrees();
			((WorldGenTrees)var7).autumn = var1.isAutumn;
			if(rand.nextInt(10) == 0) {
				var7 = new WorldGenBigTree();
			}

			if(!((WorldGenerator)var7).generate(var1, rand, var2, var3, var4)) {
				var1.setBlock(var2, var3, var4, this.blockID);
			} else {
				var5.inventory.getCurrentItem().stackSize--;
			}
		}
		
		return true;
	}
	
	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		super.updateTick(var1, var2, var3, var4, var5);
		if(var1.getBlockLightValue(var2, var3 + 1, var4) >= 9 && var5.nextInt(5) == 0) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if(var6 < 15) {
				var1.setBlockMetadataWithNotify(var2, var3, var4, var6 + 1);
			} else {
				var1.setBlock(var2, var3, var4, 0);
				Object var7 = new WorldGenTrees();
				((WorldGenTrees)var7).autumn = var1.isAutumn;
				if(var5.nextInt(10) == 0) {
					var7 = new WorldGenBigTree();
				}

				if(!((WorldGenerator)var7).generate(var1, var5, var2, var3, var4)) {
					var1.setBlock(var2, var3, var4, this.blockID);
				}
			}
		}

	}
}
