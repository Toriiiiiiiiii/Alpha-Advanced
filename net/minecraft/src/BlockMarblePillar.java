package net.minecraft.src;

import java.util.Random;

public class BlockMarblePillar extends Block {
	public boolean autumn;
	
	protected BlockMarblePillar(int var1) {
		super(var1, Material.rock);
		this.blockIndexInTexture = Block.cobbleMarble.blockIndexInTexture + 2;
		this.setTickOnLoad(true);
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(var5 == 1) {
			return Block.cobbleMarble.blockIndexInTexture + 1;
		} else if(var5 == 0) {
			return Block.cobbleMarble.blockIndexInTexture + 1;
		} else {
			return Block.cobbleMarble.blockIndexInTexture + 2;
		}
	}

	public int idDropped(int var1, Random var2) {
		return this.blockID;
	}
}
