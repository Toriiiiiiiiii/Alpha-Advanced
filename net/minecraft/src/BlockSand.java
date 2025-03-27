package net.minecraft.src;

import java.util.Random;

public class BlockSand extends Block {
	public static boolean fallInstantly = false;

	public BlockSand(int var1, int var2) {
		super(var1, var2, Material.sand);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		this.tryToFall(var1, var2, var3, var4);
	}

	private void tryToFall(World var1, int var2, int var3, int var4) {
		if(canFallBelow(var1, var2, var3 - 1, var4) && var3 >= 0) {
			EntityFallingSand var8 = new EntityFallingSand(var1, (float)var2 + 0.5F, (float)var3 + 0.5F, (float)var4 + 0.5F, this.blockID);
			if(fallInstantly) {
				while(!var8.isDead) {
					var8.onUpdate();
				}
			} else {
				var1.spawnEntityInWorld(var8);
			}
		}

	}

	public int tickRate() {
		return 3;
	}

	public static boolean canFallBelow(World var0, int var1, int var2, int var3) {
		int var4 = var0.getBlockId(var1, var2, var3);
		if(var4 == 0) {
			return true;
		} else if(var4 == Block.fire.blockID) {
			return true;
		} else {
			Material var5 = Block.blocksList[var4].material;
			return var5 == Material.water ? true : var5 == Material.lava;
		}
	}
}
