package net.minecraft.src;

public class SlotCrafting extends SlotInventory {
	private final IInventory craftMatrix;

	public SlotCrafting(GuiContainer var1, IInventory var2, IInventory var3, int var4, int var5, int var6) {
		super(var1, var3, var4, var5, var6);
		this.craftMatrix = var2;
	}

	public boolean isItemValid(ItemStack var1) {
		return false;
	}

	public void onPickupFromSlot() {
		for(int var1 = 0; var1 < this.craftMatrix.getSizeInventory(); ++var1) {
			if(this.craftMatrix.getStackInSlot(var1) != null) {
				this.craftMatrix.decrStackSize(var1, 1);
			}
		}

	}
}
