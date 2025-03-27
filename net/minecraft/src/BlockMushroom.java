package net.minecraft.src;

public class BlockMushroom extends BlockFlower {
	protected BlockMushroom(int var1, int var2) {
		super(var1, var2);
		float var3 = 0.2F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int var1) {
		return Block.opaqueCubeLookup[var1];
	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
		return var1.getBlockLightValue(var2, var3, var4) <= 13 && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
	}
}
