package net.minecraft.src;

public class BlockOreBlock extends Block {
	public BlockOreBlock(int var1, int var2) {
		super(var1, Material.iron);
		this.blockIndexInTexture = var2;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? this.blockIndexInTexture - 16 : (var1 == 0 ? this.blockIndexInTexture + 16 : this.blockIndexInTexture);
	}
}
