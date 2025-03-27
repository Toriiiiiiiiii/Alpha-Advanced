package net.minecraft.src;

import java.util.Random;

public class BlockLog extends Block {
	protected BlockLog(int var1) {
		super(var1, Material.wood);
		this.blockIndexInTexture = 20;
	}

	public int quantityDropped(Random var1) {
		return 1;
	}

	public int idDropped(int var1, Random var2) {
		return Block.wood.blockID;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? 21 : (var1 == 0 ? 21 : 20);
	}
}
