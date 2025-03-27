package net.minecraft.src;
import java.util.Random;

public class BlockLamp extends Block {
	protected BlockLamp(int var1, int var2) {
		super(var1, var2, Material.glass);
		this.setTickOnLoad(true);
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 14;
	}
}
