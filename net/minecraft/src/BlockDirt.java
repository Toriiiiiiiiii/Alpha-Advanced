package net.minecraft.src;

public class BlockDirt extends Block {
	protected BlockDirt(int var1, int var2) {
		super(var1, var2, Material.grass);
	}
	
	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {		
		ItemStack currentItem = var5.inventory.getCurrentItem();
		if(currentItem != null && currentItem.itemID == Item.fertilizer.shiftedIndex) {
			var1.setBlockWithNotify(var2, var3, var4, Block.grass.blockID);
			var5.inventory.getCurrentItem().stackSize--;
		}
		
		return true;
	}
}
