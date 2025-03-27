package net.minecraft.src;

public class Slot {
	public final int slotIndex;
	public final IInventory inventory;

	public Slot(IInventory var1, int var2) {
		this.inventory = var1;
		this.slotIndex = var2;
	}

	public void onPickupFromSlot() {
		this.onSlotChanged();
	}

	public boolean isItemValid(ItemStack var1) {
		return true;
	}

	public ItemStack getStack() {
		return this.inventory.getStackInSlot(this.slotIndex);
	}

	public void putStack(ItemStack var1) {
		this.inventory.setInventorySlotContents(this.slotIndex, var1);
		this.onSlotChanged();
	}

	public int getBackgroundIconIndex() {
		return -1;
	}

	public void onSlotChanged() {
		this.inventory.onInventoryChanged();
	}
}
