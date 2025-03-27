package net.minecraft.src;

class SlotArmor extends SlotInventory {
	final int armorType;
	final GuiInventory guiInventory;

	SlotArmor(GuiInventory var1, GuiContainer var2, IInventory var3, int var4, int var5, int var6, int var7) {
		super(var2, var3, var4, var5, var6);
		this.guiInventory = var1;
		this.armorType = var7;
	}

	public boolean isItemValid(ItemStack var1) {
		return var1.getItem() instanceof ItemArmor ? ((ItemArmor)var1.getItem()).armorType == this.armorType : false;
	}

	public int getBackgroundIconIndex() {
		return 15 + this.armorType * 16;
	}
}
