package net.minecraft.src;

public class CraftingInventoryPlayerCB extends CraftingInventoryCB {
	public InventoryCrafting craftMatrix;
	public IInventory craftResult = new InventoryCraftResult();

	public CraftingInventoryPlayerCB(ItemStack[] var1) {
		this.craftMatrix = new InventoryCrafting(this, var1);
		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory var1) {
		int[] var2 = new int[9];

		for(int var3 = 0; var3 < 3; ++var3) {
			for(int var4 = 0; var4 < 3; ++var4) {
				int var5 = -1;
				if(var3 < 2 && var4 < 2) {
					ItemStack var6 = this.craftMatrix.getStackInSlot(var3 + var4 * 2);
					if(var6 != null) {
						var5 = var6.itemID;
					}
				}

				var2[var3 + var4 * 3] = var5;
			}
		}

		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(var2));
	}

	public void onCraftGuiClosed(EntityPlayer var1) {
		super.onCraftGuiClosed(var1);

		for(int var2 = 0; var2 < 9; ++var2) {
			ItemStack var3 = this.craftMatrix.getStackInSlot(var2);
			if(var3 != null) {
				var1.dropPlayerItem(var3);
			}
		}

	}
}
