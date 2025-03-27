package net.minecraft.src;

public class InventoryCrafting implements IInventory {
	private ItemStack[] stackList;
	private int gridSize;
	private CraftingInventoryCB craftingInventory;

	public InventoryCrafting(CraftingInventoryCB var1, int var2, int var3) {
		this.gridSize = var2 * var3;
		this.stackList = new ItemStack[this.gridSize];
		this.craftingInventory = var1;
	}

	public InventoryCrafting(CraftingInventoryCB var1, ItemStack[] var2) {
		this.gridSize = var2.length;
		this.stackList = var2;
		this.craftingInventory = var1;
	}

	public int getSizeInventory() {
		return this.gridSize;
	}

	public ItemStack getStackInSlot(int var1) {
		return this.stackList[var1];
	}

	public String getInvName() {
		return "Crafting";
	}

	public ItemStack decrStackSize(int var1, int var2) {
		if(this.stackList[var1] != null) {
			ItemStack var3;
			if(this.stackList[var1].stackSize <= var2) {
				var3 = this.stackList[var1];
				this.stackList[var1] = null;
				this.craftingInventory.onCraftMatrixChanged(this);
				return var3;
			} else {
				var3 = this.stackList[var1].splitStack(var2);
				if(this.stackList[var1].stackSize == 0) {
					this.stackList[var1] = null;
				}

				this.craftingInventory.onCraftMatrixChanged(this);
				return var3;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.stackList[var1] = var2;
		this.craftingInventory.onCraftMatrixChanged(this);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}
}
