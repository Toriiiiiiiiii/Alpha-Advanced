package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block {
	int dropped;
	
	public BlockStone(int var1, int var2) {
		super(var1, var2, Material.rock);
		this.dropped = 0;
	}
	
	public BlockStone(int var1, int var2, int dropID) {
		super(var1, var2, Material.rock);
		this.dropped = dropID;
	}

	public int idDropped(int var1, Random var2) {
		if(this.dropped == 0) return Block.cobblestone.blockID;
		
		return this.dropped;
	}
}
