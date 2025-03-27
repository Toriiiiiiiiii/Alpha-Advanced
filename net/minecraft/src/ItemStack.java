package net.minecraft.src;

public final class ItemStack {
	public int stackSize;
	public int animationsToGo;
	public int itemID;
	public int itemDmg;

	public ItemStack(Block var1) {
		this((Block)var1, 1);
	}

	public ItemStack(Block var1, int var2) {
		this(var1.blockID, var2);
	}

	public ItemStack(Item var1) {
		this((Item)var1, 1);
	}

	public ItemStack(Item var1, int var2) {
		this(var1.shiftedIndex, var2);
	}

	public ItemStack(int var1) {
		this(var1, 1);
	}

	public ItemStack(int var1, int var2) {
		this.stackSize = 0;
		this.itemID = var1;
		this.stackSize = var2;
	}

	public ItemStack(int var1, int var2, int var3) {
		this.stackSize = 0;
		this.itemID = var1;
		this.stackSize = var2;
		this.itemDmg = var3;
	}

	public ItemStack(NBTTagCompound var1) {
		this.stackSize = 0;
		this.readFromNBT(var1);
	}

	public ItemStack splitStack(int var1) {
		this.stackSize -= var1;
		return new ItemStack(this.itemID, var1, this.itemDmg);
	}

	public Item getItem() {
		return Item.itemsList[this.itemID];
	}

	public int getIconIndex() {
		return this.getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer var1, World var2, int var3, int var4, int var5, int var6) {
		return this.getItem().onItemUse(this, var1, var2, var3, var4, var5, var6);
	}

	public float getStrVsBlock(Block var1) {
		return this.getItem().getStrVsBlock(this, var1);
	}

	public ItemStack useItemRightClick(World var1, EntityPlayer var2) {
		return this.getItem().onItemRightClick(this, var1, var2);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound var1) {
		var1.setShort("id", (short)this.itemID);
		var1.setByte("Count", (byte)this.stackSize);
		var1.setShort("Damage", (short)this.itemDmg);
		return var1;
	}

	public void readFromNBT(NBTTagCompound var1) {
		this.itemID = var1.getShort("id");
		this.stackSize = var1.getByte("Count");
		this.itemDmg = var1.getShort("Damage");
	}

	public int getMaxStackSize() {
		return this.getItem().getItemStackLimit();
	}

	public int getMaxDamage() {
		return Item.itemsList[this.itemID].getMaxDamage();
	}

	public void damageItem(int var1) {
		this.itemDmg += var1;
		if(this.itemDmg > this.getMaxDamage()) {
			--this.stackSize;
			if(this.stackSize < 0) {
				this.stackSize = 0;
			}

			this.itemDmg = 0;
		}

	}

	public void hitEntity(EntityLiving var1) {
		Item.itemsList[this.itemID].hitEntity(this, var1);
	}

	public void onDestroyBlock(int var1, int var2, int var3, int var4) {
		Item.itemsList[this.itemID].onBlockDestroyed(this, var1, var2, var3, var4);
	}

	public int getDamageVsEntity(Entity var1) {
		return Item.itemsList[this.itemID].getDamageVsEntity(var1);
	}

	public boolean canHarvestBlock(Block var1) {
		return Item.itemsList[this.itemID].canHarvestBlock(var1);
	}

	public void onItemDestroyedByUse(EntityPlayer var1) {
	}

	public void useItemOnEntity(EntityLiving var1) {
		Item.itemsList[this.itemID].saddleEntity(this, var1);
	}

	public ItemStack copy() {
		return new ItemStack(this.itemID, this.stackSize, this.itemDmg);
	}
}
