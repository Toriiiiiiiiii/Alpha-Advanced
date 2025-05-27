package net.minecraft.src;

import java.util.Random;

public class WorldGenCoral extends WorldGenerator {
	private int coralBlockId = Block.coralRed.blockID;
	private int numberOfBlocks;

	public WorldGenCoral(int var1) {
		this.numberOfBlocks = var1;
	}

	public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
		this.coralBlockId = var2.nextInt(3) + Block.coralRed.blockID;
		for(int h = var4; h < var4+5; ++h) {
			if(var1.getBlockMaterial(var3, h+1, var5) != Material.water || var1.getBlockMaterial(var3, var4-1, var5) == Material.water) {
				return false;
			} else {
				for(int i = 0; i < h-var4; ++i) {
				int xoff = 0, zoff = 0;
					if(h-var4 > 1) {
						xoff = var2.nextInt(3) - 1;
						zoff = var2.nextInt(3) - 1;
					}
					var1.setBlock(var3+xoff, h, var5+zoff, (var2.nextInt(5) == 0? 1 : 0)*6 + coralBlockId);
				}
			}
		}
		
		return true;
	}
}
