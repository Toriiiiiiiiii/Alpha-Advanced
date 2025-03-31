package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiCrafting extends GuiContainer {
	public CraftingInventoryWorkbenchCB craftingInventory = new CraftingInventoryWorkbenchCB();

	public GuiCrafting(InventoryPlayer var1) {
		this.inventorySlots.add(new SlotCrafting(this, this.craftingInventory.craftMatrix, this.craftingInventory.craftResult, 0, 124, 35));

		int var2;
		int var3;
		for(var2 = 0; var2 < 3; ++var2) {
			for(var3 = 0; var3 < 3; ++var3) {
				this.inventorySlots.add(new SlotInventory(this, this.craftingInventory.craftMatrix, var3 + var2 * 3, 30 + var3 * 18, 17 + var2 * 18));
			}
		}

		for(var2 = 0; var2 < 3; ++var2) {
			for(var3 = 0; var3 < 9; ++var3) {
				this.inventorySlots.add(new SlotInventory(this, var1, var3 + (var2 + 1) * 9, 8 + var3 * 18, 84 + var2 * 18));
			}
		}

		for(var2 = 0; var2 < 9; ++var2) {
			this.inventorySlots.add(new SlotInventory(this, var1, var2, 8 + var2 * 18, 142));
		}

	}

	public void onGuiClosed() {
		super.onGuiClosed();
		this.craftingInventory.onCraftGuiClosed(this.mc.thePlayer);
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Crafting", 28, 6, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float var1) {
		int var2;
		
		if(!this.mc.options.darkMode)
			var2 = this.mc.renderEngine.getTexture("/gui/crafting.png");
		else
			var2 = this.mc.renderEngine.getTexture("/gui/crafting_dark.png");
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var2);
		int var3 = (this.width - this.xSize) / 2;
		int var4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
	}
}
