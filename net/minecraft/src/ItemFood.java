package net.minecraft.src;

public class ItemFood extends Item {
	private int healAmount;

	public ItemFood(int var1, int var2, int max) {
		super(var1);
		this.healAmount = var2;
		this.maxStackSize = max;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		if(var3.health == 20) return var1;
		
		var3.heal(this.healAmount);
		
		if(this.shiftedIndex == Item.tea.shiftedIndex) return new ItemStack(Item.mug, 1);
		
		--var1.stackSize;
		return var1;
	}
}
