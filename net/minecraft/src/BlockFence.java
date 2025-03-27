package net.minecraft.src;

import java.util.ArrayList;

public class BlockFence extends Block {
	public BlockFence(int var1, int var2) {
		super(var1, var2, Material.wood);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		var6.add(AxisAlignedBB.getBoundingBoxFromPool((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)var3 + 1.5D, (double)(var4 + 1)));
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.getBlockId(var2, var3 - 1, var4) == this.blockID ? false : (!var1.getBlockMaterial(var2, var3 - 1, var4).isSolid() ? false : super.canPlaceBlockAt(var1, var2, var3, var4));
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 11;
	}
}
