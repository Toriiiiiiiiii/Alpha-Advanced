package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable {
	public BlockIce(int var1, int var2) {
		super(var1, var2, Material.ice, false);
		this.slipperiness = 0.98F;
		this.setTickOnLoad(true);
	}

	public int getRenderBlockPass() {
		return 1;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return super.shouldSideBeRendered(var1, var2, var3, var4, 1 - var5);
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		Material var5 = var1.getBlockMaterial(var2, var3 - 1, var4);
		if(var5.getIsSolid() || var5.getIsLiquid()) {
			var1.setBlockWithNotify(var2, var3, var4, Block.waterMoving.blockID);
		}

	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11 - Block.lightOpacity[this.blockID]) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, Block.waterStill.blockID);
		}

	}
}
