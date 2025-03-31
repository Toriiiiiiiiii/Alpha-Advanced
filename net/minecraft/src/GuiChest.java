package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer {
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows = 0;

	public GuiChest(IInventory var1, IInventory var2) {
		this.upperChestInventory = var1;
		this.lowerChestInventory = var2;
		this.allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		this.inventoryRows = var2.getSizeInventory() / 9;
		this.ySize = var4 + this.inventoryRows * 18;
		int var5 = (this.inventoryRows - 4) * 18;

		int var6;
		int var7;
		for(var6 = 0; var6 < this.inventoryRows; ++var6) {
			for(var7 = 0; var7 < 9; ++var7) {
				this.inventorySlots.add(new SlotInventory(this, var2, var7 + var6 * 9, 8 + var7 * 18, 18 + var6 * 18));
			}
		}

		for(var6 = 0; var6 < 3; ++var6) {
			for(var7 = 0; var7 < 9; ++var7) {
				this.inventorySlots.add(new SlotInventory(this, var1, var7 + (var6 + 1) * 9, 8 + var7 * 18, 103 + var6 * 18 + var5));
			}
		}

		for(var6 = 0; var6 < 9; ++var6) {
			this.inventorySlots.add(new SlotInventory(this, var1, var6, 8 + var6 * 18, 161 + var5));
		}

	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString(this.lowerChestInventory.getInvName(), 8, 6, 4210752);
		this.fontRenderer.drawString(this.upperChestInventory.getInvName(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float var1) {
		int var2;
		
		if(!this.mc.options.darkMode)
			var2 = this.mc.renderEngine.getTexture("/gui/container.png");
		else
			var2 = this.mc.renderEngine.getTexture("/gui/container_dark.png");
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var2);
		int var3 = (this.width - this.xSize) / 2;
		int var4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(var3, var4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}
}
