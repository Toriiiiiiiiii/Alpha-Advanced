package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiInventory extends GuiContainer {
	private CraftingInventoryPlayerCB craftingInventory;
	private float xSize_lo;
	private float ySize_lo;

	public GuiInventory(IInventory var1, ItemStack[] var2) {
		this.allowUserInput = true;
		this.craftingInventory = new CraftingInventoryPlayerCB(var2);
		this.inventorySlots.add(new SlotCrafting(this, this.craftingInventory.craftMatrix, this.craftingInventory.craftResult, 0, 144, 36));

		int var3;
		int var4;
		for(var3 = 0; var3 < 2; ++var3) {
			for(var4 = 0; var4 < 2; ++var4) {
				this.inventorySlots.add(new SlotInventory(this, this.craftingInventory.craftMatrix, var4 + var3 * 2, 88 + var4 * 18, 26 + var3 * 18));
			}
		}

		for(var3 = 0; var3 < 4; ++var3) {
			this.inventorySlots.add(new SlotArmor(this, this, var1, var1.getSizeInventory() - 1 - var3, 8, 8 + var3 * 18, var3));
		}

		for(var3 = 0; var3 < 3; ++var3) {
			for(var4 = 0; var4 < 9; ++var4) {
				this.inventorySlots.add(new SlotInventory(this, var1, var4 + (var3 + 1) * 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for(var3 = 0; var3 < 9; ++var3) {
			this.inventorySlots.add(new SlotInventory(this, var1, var3, 8 + var3 * 18, 142));
		}

	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Crafting", 86, 16, 4210752);
	}

	public void drawScreen(int var1, int var2, float var3) {
		super.drawScreen(var1, var2, var3);
		this.xSize_lo = (float)var1;
		this.ySize_lo = (float)var2;
	}

	protected void drawGuiContainerBackgroundLayer(float var1) {
		int var2;
		
		if(!this.mc.options.darkMode)
			var2 = this.mc.renderEngine.getTexture("/gui/inventory.png");
		else
			var2 = this.mc.renderEngine.getTexture("/gui/inventory_dark.png");
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var2);
		int var3 = (this.width - this.xSize) / 2;
		int var4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(var3 + 51), (float)(var4 + 75), 50.0F);
		float var5 = 30.0F;
		GL11.glScalef(-var5, var5, var5);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = this.mc.thePlayer.renderYawOffset;
		float var7 = this.mc.thePlayer.rotationYaw;
		float var8 = this.mc.thePlayer.rotationPitch;
		float var9 = (float)(var3 + 51) - this.xSize_lo;
		float var10 = (float)(var4 + 75 - 50) - this.ySize_lo;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan((double)(var10 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		this.mc.thePlayer.renderYawOffset = (float)Math.atan((double)(var9 / 40.0F)) * 20.0F;
		this.mc.thePlayer.rotationYaw = (float)Math.atan((double)(var9 / 40.0F)) * 40.0F;
		this.mc.thePlayer.rotationPitch = -((float)Math.atan((double)(var10 / 40.0F))) * 20.0F;
		GL11.glTranslatef(0.0F, this.mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		this.mc.thePlayer.renderYawOffset = var6;
		this.mc.thePlayer.rotationYaw = var7;
		this.mc.thePlayer.rotationPitch = var8;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
