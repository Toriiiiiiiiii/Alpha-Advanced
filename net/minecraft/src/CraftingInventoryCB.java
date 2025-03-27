package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CraftingInventoryCB {
	protected List list = new ArrayList();

	public void onCraftGuiClosed(EntityPlayer var1) {
		InventoryPlayer var2 = var1.inventory;
		if(var2.draggedItemStack != null) {
			var1.dropPlayerItem(var2.draggedItemStack);
		}

	}

	public void onCraftMatrixChanged(IInventory var1) {
	}
}
