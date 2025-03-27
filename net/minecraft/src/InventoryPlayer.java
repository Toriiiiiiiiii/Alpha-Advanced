package net.minecraft.src;

public class InventoryPlayer implements IInventory {
	public ItemStack[] mainInventory = new ItemStack[37];
	public ItemStack[] armorInventory = new ItemStack[4];
	public ItemStack[] craftingInventory = new ItemStack[4];
	public int currentItem = 0;
	private EntityPlayer player;
	public ItemStack draggedItemStack;
	public boolean inventoryChanged = false;

	public InventoryPlayer(EntityPlayer var1) {
		this.player = var1;
	}

	public ItemStack getCurrentItem() {
		return this.mainInventory[this.currentItem];
	}

	private int getInventorySlotContainItem(int var1) {
		for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
			if(this.mainInventory[var2] != null && this.mainInventory[var2].itemID == var1) {
				return var2;
			}
		}

		return -1;
	}

	private int storeItemStack(int var1) {
		for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
			if(this.mainInventory[var2] != null && this.mainInventory[var2].itemID == var1 && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit()) {
				return var2;
			}
		}

		return -1;
	}

	private int getFirstEmptyStack() {
		for(int var1 = 0; var1 < this.mainInventory.length; ++var1) {
			if(this.mainInventory[var1] == null) {
				return var1;
			}
		}

		return -1;
	}

	public void setCurrentItem(int var1, boolean var2) {
		int var3 = this.getInventorySlotContainItem(var1);
		if(var3 >= 0 && var3 < 9) {
			this.currentItem = var3;
		}
	}

	public void changeCurrentItem(int var1) {
		if(var1 > 0) {
			var1 = 1;
		}

		if(var1 < 0) {
			var1 = -1;
		}

		for(this.currentItem -= var1; this.currentItem < 0; this.currentItem += 9) {
		}

		while(this.currentItem >= 9) {
			this.currentItem -= 9;
		}

	}

	private int storePartialItemStack(int var1, int var2) {
		int var3 = this.storeItemStack(var1);
		if(var3 < 0) {
			var3 = this.getFirstEmptyStack();
		}

		if(var3 < 0) {
			return var2;
		} else {
			if(this.mainInventory[var3] == null) {
				this.mainInventory[var3] = new ItemStack(var1, 0);
			}

			int var4 = var2;
			if(var2 > this.mainInventory[var3].getMaxStackSize() - this.mainInventory[var3].stackSize) {
				var4 = this.mainInventory[var3].getMaxStackSize() - this.mainInventory[var3].stackSize;
			}

			if(var4 > this.getInventoryStackLimit() - this.mainInventory[var3].stackSize) {
				var4 = this.getInventoryStackLimit() - this.mainInventory[var3].stackSize;
			}

			if(var4 == 0) {
				return var2;
			} else {
				var2 -= var4;
				this.mainInventory[var3].stackSize += var4;
				this.mainInventory[var3].animationsToGo = 5;
				return var2;
			}
		}
	}

	public void decrementAnimations() {
		for(int var1 = 0; var1 < this.mainInventory.length; ++var1) {
			if(this.mainInventory[var1] != null && this.mainInventory[var1].animationsToGo > 0) {
				--this.mainInventory[var1].animationsToGo;
			}
		}

	}

	public boolean consumeInventoryItem(int var1) {
		int var2 = this.getInventorySlotContainItem(var1);
		if(var2 < 0) {
			return false;
		} else {
			if(--this.mainInventory[var2].stackSize <= 0) {
				this.mainInventory[var2] = null;
			}

			return true;
		}
	}

	public boolean addItemStackToInventory(ItemStack var1) {
		if(var1.itemDmg == 0) {
			var1.stackSize = this.storePartialItemStack(var1.itemID, var1.stackSize);
			if(var1.stackSize == 0) {
				return true;
			}
		}

		int var2 = this.getFirstEmptyStack();
		if(var2 >= 0) {
			this.mainInventory[var2] = var1;
			this.mainInventory[var2].animationsToGo = 5;
			return true;
		} else {
			return false;
		}
	}

	public ItemStack decrStackSize(int var1, int var2) {
		ItemStack[] var3 = this.mainInventory;
		if(var1 >= this.mainInventory.length) {
			var3 = this.armorInventory;
			var1 -= this.mainInventory.length;
		}

		if(var3[var1] != null) {
			ItemStack var4;
			if(var3[var1].stackSize <= var2) {
				var4 = var3[var1];
				var3[var1] = null;
				return var4;
			} else {
				var4 = var3[var1].splitStack(var2);
				if(var3[var1].stackSize == 0) {
					var3[var1] = null;
				}

				return var4;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int var1, ItemStack var2) {
		ItemStack[] var3 = this.mainInventory;
		if(var1 >= var3.length) {
			var1 -= var3.length;
			var3 = this.armorInventory;
		}

		if(var1 >= var3.length) {
			var1 -= var3.length;
			var3 = this.craftingInventory;
		}

		var3[var1] = var2;
	}

	public float getStrVsBlock(Block var1) {
		float var2 = 1.0F;
		if(this.mainInventory[this.currentItem] != null) {
			var2 *= this.mainInventory[this.currentItem].getStrVsBlock(var1);
		}

		return var2;
	}

	public NBTTagList writeToNBT(NBTTagList var1) {
		int var2;
		NBTTagCompound var3;
		for(var2 = 0; var2 < this.mainInventory.length; ++var2) {
			if(this.mainInventory[var2] != null) {
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte)var2);
				this.mainInventory[var2].writeToNBT(var3);
				var1.setTag(var3);
			}
		}

		for(var2 = 0; var2 < this.armorInventory.length; ++var2) {
			if(this.armorInventory[var2] != null) {
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte)(var2 + 100));
				this.armorInventory[var2].writeToNBT(var3);
				var1.setTag(var3);
			}
		}

		for(var2 = 0; var2 < this.craftingInventory.length; ++var2) {
			if(this.craftingInventory[var2] != null) {
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte)(var2 + 80));
				this.craftingInventory[var2].writeToNBT(var3);
				var1.setTag(var3);
			}
		}

		return var1;
	}

	public void readFromNBT(NBTTagList var1) {
		this.mainInventory = new ItemStack[36];
		this.armorInventory = new ItemStack[4];
		this.craftingInventory = new ItemStack[4];

		for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
			NBTTagCompound var3 = (NBTTagCompound)var1.tagAt(var2);
			int var4 = var3.getByte("Slot") & 255;
			if(var4 >= 0 && var4 < this.mainInventory.length) {
				this.mainInventory[var4] = new ItemStack(var3);
			}

			if(var4 >= 80 && var4 < this.craftingInventory.length + 80) {
				this.craftingInventory[var4 - 80] = new ItemStack(var3);
			}

			if(var4 >= 100 && var4 < this.armorInventory.length + 100) {
				this.armorInventory[var4 - 100] = new ItemStack(var3);
			}
		}

	}

	public int getSizeInventory() {
		return this.mainInventory.length + 4;
	}

	public ItemStack getStackInSlot(int var1) {
		ItemStack[] var2 = this.mainInventory;
		if(var1 >= var2.length) {
			var1 -= var2.length;
			var2 = this.armorInventory;
		}

		if(var1 >= var2.length) {
			var1 -= var2.length;
			var2 = this.craftingInventory;
		}

		return var2[var1];
	}

	public String getInvName() {
		return "Inventory";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getDamageVsEntity(Entity var1) {
		ItemStack var2 = this.getStackInSlot(this.currentItem);
		return var2 != null ? var2.getDamageVsEntity(var1) : 1;
	}

	public boolean canHarvestBlock(Block var1) {
		if(var1.material != Material.rock && var1.material != Material.iron && var1.material != Material.craftedSnow && var1.material != Material.snow) {
			return true;
		} else {
			ItemStack var2 = this.getStackInSlot(this.currentItem);
			return var2 != null ? var2.canHarvestBlock(var1) : false;
		}
	}

	public ItemStack armorItemInSlot(int var1) {
		return this.armorInventory[var1];
	}

	public int getTotalArmorValue() {
		int var1 = 0;
		int var2 = 0;
		int var3 = 0;

		for(int var4 = 0; var4 < this.armorInventory.length; ++var4) {
			if(this.armorInventory[var4] != null && this.armorInventory[var4].getItem() instanceof ItemArmor) {
				int var5 = this.armorInventory[var4].getMaxDamage();
				int var6 = this.armorInventory[var4].itemDmg;
				int var7 = var5 - var6;
				var2 += var7;
				var3 += var5;
				int var8 = ((ItemArmor)this.armorInventory[var4].getItem()).damageReduceAmount;
				var1 += var8;
			}
		}

		if(var3 == 0) {
			return 0;
		} else {
			return (var1 - 1) * var2 / var3 + 1;
		}
	}

	public void damageArmor(int var1) {
		for(int var2 = 0; var2 < this.armorInventory.length; ++var2) {
			if(this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
				this.armorInventory[var2].damageItem(var1);
				if(this.armorInventory[var2].stackSize == 0) {
					this.armorInventory[var2].onItemDestroyedByUse(this.player);
					this.armorInventory[var2] = null;
				}
			}
		}

	}

	public void dropAllItems() {
		int var1;
		for(var1 = 0; var1 < this.mainInventory.length; ++var1) {
			if(this.mainInventory[var1] != null) {
				this.player.dropPlayerItemWithRandomChoice(this.mainInventory[var1], true);
				this.mainInventory[var1] = null;
			}
		}

		for(var1 = 0; var1 < this.armorInventory.length; ++var1) {
			if(this.armorInventory[var1] != null) {
				this.player.dropPlayerItemWithRandomChoice(this.armorInventory[var1], true);
				this.armorInventory[var1] = null;
			}
		}

	}

	public void onInventoryChanged() {
		this.inventoryChanged = true;
	}

	public boolean getInventoryEqual(InventoryPlayer var1) {
		int var2;
		for(var2 = 0; var2 < this.mainInventory.length; ++var2) {
			if(!this.getItemStacksEqual(var1.mainInventory[var2], this.mainInventory[var2])) {
				return false;
			}
		}

		for(var2 = 0; var2 < this.armorInventory.length; ++var2) {
			if(!this.getItemStacksEqual(var1.armorInventory[var2], this.armorInventory[var2])) {
				return false;
			}
		}

		for(var2 = 0; var2 < this.craftingInventory.length; ++var2) {
			if(!this.getItemStacksEqual(var1.craftingInventory[var2], this.craftingInventory[var2])) {
				return false;
			}
		}

		return true;
	}

	private boolean getItemStacksEqual(ItemStack var1, ItemStack var2) {
		return var1 == null && var2 == null ? true : (var1 != null && var2 != null ? var1.itemID == var2.itemID && var1.stackSize == var2.stackSize && var1.itemDmg == var2.itemDmg : false);
	}

	public InventoryPlayer copyInventory() {
		InventoryPlayer var1 = new InventoryPlayer((EntityPlayer)null);

		int var2;
		for(var2 = 0; var2 < this.mainInventory.length; ++var2) {
			var1.mainInventory[var2] = this.mainInventory[var2] != null ? this.mainInventory[var2].copy() : null;
		}

		for(var2 = 0; var2 < this.armorInventory.length; ++var2) {
			var1.armorInventory[var2] = this.armorInventory[var2] != null ? this.armorInventory[var2].copy() : null;
		}

		for(var2 = 0; var2 < this.craftingInventory.length; ++var2) {
			var1.craftingInventory[var2] = this.craftingInventory[var2] != null ? this.craftingInventory[var2].copy() : null;
		}

		return var1;
	}
}
