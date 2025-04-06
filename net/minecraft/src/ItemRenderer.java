package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemRenderer {
	private Minecraft mc;
	private ItemStack itemToRender = null;
	private float equippedProgress = 0.0F;
	private float prevEquippedProgress = 0.0F;
	private RenderBlocks renderBlocksInstance = new RenderBlocks();

	public ItemRenderer(Minecraft var1) {
		this.mc = var1;
	}

	public void renderItem(ItemStack var1) {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();
		if(var1.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var1.itemID].getRenderType())) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			this.renderBlocksInstance.renderBlockOnInventory(Block.blocksList[var1.itemID]);
		} else {
			if(var1.itemID < 256) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			} else {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/items.png"));
			}

			Tessellator var2 = Tessellator.instance;
			float var3 = (float)(var1.getIconIndex() % 16 * 16 + 0) / 256.0F;
			float var4 = (float)(var1.getIconIndex() % 16 * 16 + 16) / 256.0F;
			float var5 = (float)(var1.getIconIndex() / 16 * 16 + 0) / 256.0F;
			float var6 = (float)(var1.getIconIndex() / 16 * 16 + 16) / 256.0F;
			float var7 = 1.0F;
			float var8 = 0.0F;
			float var9 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef(-var8, -var9, 0.0F);
			float var10 = 1.5F;
			GL11.glScalef(var10, var10, var10);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-(15.0F / 16.0F), -(1.0F / 16.0F), 0.0F);
			float var11 = 1.0F / 16.0F;
			var2.startDrawingQuads();
			var2.setNormal(0.0F, 0.0F, 1.0F);
			var2.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)var4, (double)var6);
			var2.addVertexWithUV((double)var7, 0.0D, 0.0D, (double)var3, (double)var6);
			var2.addVertexWithUV((double)var7, 1.0D, 0.0D, (double)var3, (double)var5);
			var2.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)var4, (double)var5);
			var2.draw();
			var2.startDrawingQuads();
			var2.setNormal(0.0F, 0.0F, -1.0F);
			var2.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - var11), (double)var4, (double)var5);
			var2.addVertexWithUV((double)var7, 1.0D, (double)(0.0F - var11), (double)var3, (double)var5);
			var2.addVertexWithUV((double)var7, 0.0D, (double)(0.0F - var11), (double)var3, (double)var6);
			var2.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - var11), (double)var4, (double)var6);
			var2.draw();
			var2.startDrawingQuads();
			var2.setNormal(-1.0F, 0.0F, 0.0F);

			int var12;
			float var13;
			float var14;
			float var15;
			for(var12 = 0; var12 < 16; ++var12) {
				var13 = (float)var12 / 16.0F;
				var14 = var4 + (var3 - var4) * var13 - 0.001953125F;
				var15 = var7 * var13;
				var2.addVertexWithUV((double)var15, 0.0D, (double)(0.0F - var11), (double)var14, (double)var6);
				var2.addVertexWithUV((double)var15, 0.0D, 0.0D, (double)var14, (double)var6);
				var2.addVertexWithUV((double)var15, 1.0D, 0.0D, (double)var14, (double)var5);
				var2.addVertexWithUV((double)var15, 1.0D, (double)(0.0F - var11), (double)var14, (double)var5);
			}

			var2.draw();
			var2.startDrawingQuads();
			var2.setNormal(1.0F, 0.0F, 0.0F);

			for(var12 = 0; var12 < 16; ++var12) {
				var13 = (float)var12 / 16.0F;
				var14 = var4 + (var3 - var4) * var13 - 0.001953125F;
				var15 = var7 * var13 + 1.0F / 16.0F;
				var2.addVertexWithUV((double)var15, 1.0D, (double)(0.0F - var11), (double)var14, (double)var5);
				var2.addVertexWithUV((double)var15, 1.0D, 0.0D, (double)var14, (double)var5);
				var2.addVertexWithUV((double)var15, 0.0D, 0.0D, (double)var14, (double)var6);
				var2.addVertexWithUV((double)var15, 0.0D, (double)(0.0F - var11), (double)var14, (double)var6);
			}

			var2.draw();
			var2.startDrawingQuads();
			var2.setNormal(0.0F, 1.0F, 0.0F);

			for(var12 = 0; var12 < 16; ++var12) {
				var13 = (float)var12 / 16.0F;
				var14 = var6 + (var5 - var6) * var13 - 0.001953125F;
				var15 = var7 * var13 + 1.0F / 16.0F;
				var2.addVertexWithUV(0.0D, (double)var15, 0.0D, (double)var4, (double)var14);
				var2.addVertexWithUV((double)var7, (double)var15, 0.0D, (double)var3, (double)var14);
				var2.addVertexWithUV((double)var7, (double)var15, (double)(0.0F - var11), (double)var3, (double)var14);
				var2.addVertexWithUV(0.0D, (double)var15, (double)(0.0F - var11), (double)var4, (double)var14);
			}

			var2.draw();
			var2.startDrawingQuads();
			var2.setNormal(0.0F, -1.0F, 0.0F);

			for(var12 = 0; var12 < 16; ++var12) {
				var13 = (float)var12 / 16.0F;
				var14 = var6 + (var5 - var6) * var13 - 0.001953125F;
				var15 = var7 * var13;
				var2.addVertexWithUV((double)var7, (double)var15, 0.0D, (double)var3, (double)var14);
				var2.addVertexWithUV(0.0D, (double)var15, 0.0D, (double)var4, (double)var14);
				var2.addVertexWithUV(0.0D, (double)var15, (double)(0.0F - var11), (double)var4, (double)var14);
				var2.addVertexWithUV((double)var7, (double)var15, (double)(0.0F - var11), (double)var3, (double)var14);
			}

			var2.draw();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}

	public void renderItemInFirstPerson(float var1) {
		float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1;
		EntityPlayerSP var3 = this.mc.thePlayer;
		GL11.glPushMatrix();
		GL11.glRotatef(var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var1, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var1, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		float var4 = this.mc.theWorld.getBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
		GL11.glColor4f(var4, var4, var4, 1.0F);
		float var5;
		float var6;
		float var7;
		float var8;
		if(this.itemToRender != null) {
			GL11.glPushMatrix();
			var5 = 0.8F;
			var6 = var3.getSwingProgress(var1);
			var7 = MathHelper.sin(var6 * (float)Math.PI);
			var8 = MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI);
			GL11.glTranslatef(-var8 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI * 2.0F) * 0.2F, -var7 * 0.2F);
			GL11.glTranslatef(0.7F * var5, -0.65F * var5 - (1.0F - var2) * 0.6F, -0.9F * var5);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			var6 = var3.getSwingProgress(var1);
			var7 = MathHelper.sin(var6 * var6 * (float)Math.PI);
			var8 = MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI);
			GL11.glRotatef(-var7 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var8 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-var8 * 80.0F, 1.0F, 0.0F, 0.0F);
			var6 = 0.4F;
			GL11.glScalef(var6, var6, var6);
			this.renderItem(this.itemToRender);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			var5 = 0.8F;
			var6 = var3.getSwingProgress(var1);
			var7 = MathHelper.sin(var6 * (float)Math.PI);
			var8 = MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI);
			GL11.glTranslatef(-var8 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI * 2.0F) * 0.4F, -var7 * 0.4F);
			GL11.glTranslatef(0.8F * var5, -(12.0F / 16.0F) * var5 - (1.0F - var2) * 0.6F, -0.9F * var5);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			var6 = var3.getSwingProgress(var1);
			var7 = MathHelper.sin(var6 * var6 * (float)Math.PI);
			var8 = MathHelper.sin(MathHelper.sqrt_float(var6) * (float)Math.PI);
			GL11.glRotatef(var8 * 70.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var7 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getTexture()));
			GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
			GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(5.6F, 0.0F, 0.0F);
			Render var9 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
			RenderPlayer var10 = (RenderPlayer)var9;
			var8 = 1.0F;
			GL11.glScalef(var8, var8, var8);
			var10.drawFirstPersonHand();
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}

	public void renderOverlays(float var1) {
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		int var2;
		if(this.mc.thePlayer.fire > 0) {
			var2 = this.mc.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2);
			this.renderFireInFirstPerson(var1);
		}

		if(this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
			var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
			int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
			int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
			int var5 = this.mc.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var5);
			int var6 = this.mc.theWorld.getBlockId(var2, var3, var4);
			if(Block.blocksList[var6] != null) {
				this.renderInsideOfBlock(var1, Block.blocksList[var6].getBlockTextureFromSide(2));
			}
		}

		if(this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
			var2 = this.mc.renderEngine.getTexture("/water.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2);
			this.renderWarpedTextureOverlay(var1);
		}

		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	private void renderInsideOfBlock(float var1, int var2) {
		Tessellator var3 = Tessellator.instance;
		this.mc.thePlayer.getBrightness(var1);
		float var4 = 0.1F;
		GL11.glColor4f(var4, var4, var4, 0.5F);
		GL11.glPushMatrix();
		float var5 = -1.0F;
		float var6 = 1.0F;
		float var7 = -1.0F;
		float var8 = 1.0F;
		float var9 = -0.5F;
		float var10 = 0.0078125F;
		float var11 = (float)(var2 % 16) / 256.0F - var10;
		float var12 = ((float)(var2 % 16) + 15.99F) / 256.0F + var10;
		float var13 = (float)(var2 / 16) / 256.0F - var10;
		float var14 = ((float)(var2 / 16) + 15.99F) / 256.0F + var10;
		var3.startDrawingQuads();
		var3.addVertexWithUV((double)var5, (double)var7, (double)var9, (double)var12, (double)var14);
		var3.addVertexWithUV((double)var6, (double)var7, (double)var9, (double)var11, (double)var14);
		var3.addVertexWithUV((double)var6, (double)var8, (double)var9, (double)var11, (double)var13);
		var3.addVertexWithUV((double)var5, (double)var8, (double)var9, (double)var12, (double)var13);
		var3.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderWarpedTextureOverlay(float var1) {
		Tessellator var2 = Tessellator.instance;
		float var3 = this.mc.thePlayer.getBrightness(var1);
		GL11.glColor4f(var3, var3, var3, 0.5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		float var4 = 4.0F;
		float var5 = -1.0F;
		float var6 = 1.0F;
		float var7 = -1.0F;
		float var8 = 1.0F;
		float var9 = -0.5F;
		float var10 = -this.mc.thePlayer.rotationYaw / 64.0F;
		float var11 = this.mc.thePlayer.rotationPitch / 64.0F;
		var2.startDrawingQuads();
		var2.addVertexWithUV((double)var5, (double)var7, (double)var9, (double)(var4 + var10), (double)(var4 + var11));
		var2.addVertexWithUV((double)var6, (double)var7, (double)var9, (double)(0.0F + var10), (double)(var4 + var11));
		var2.addVertexWithUV((double)var6, (double)var8, (double)var9, (double)(0.0F + var10), (double)(0.0F + var11));
		var2.addVertexWithUV((double)var5, (double)var8, (double)var9, (double)(var4 + var10), (double)(0.0F + var11));
		var2.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void renderFireInFirstPerson(float var1) {
		Tessellator var2 = Tessellator.instance;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		float var3 = 1.0F;

		for(int var4 = 0; var4 < 2; ++var4) {
			GL11.glPushMatrix();
			int var5 = Block.fire.blockIndexInTexture + var4 * 16;
			int var6 = (var5 & 15) << 4;
			int var7 = var5 & 240;
			float var8 = (float)var6 / 256.0F;
			float var9 = ((float)var6 + 15.99F) / 256.0F;
			float var10 = (float)var7 / 256.0F;
			float var11 = ((float)var7 + 15.99F) / 256.0F;
			float var12 = (0.0F - var3) / 2.0F;
			float var13 = var12 + var3;
			float var14 = 0.0F - var3 / 2.0F;
			float var15 = var14 + var3;
			float var16 = -0.5F;
			GL11.glTranslatef((float)(-(var4 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
			GL11.glRotatef((float)(var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
			var2.startDrawingQuads();
			var2.addVertexWithUV((double)var12, (double)var14, (double)var16, (double)var9, (double)var11);
			var2.addVertexWithUV((double)var13, (double)var14, (double)var16, (double)var8, (double)var11);
			var2.addVertexWithUV((double)var13, (double)var15, (double)var16, (double)var8, (double)var10);
			var2.addVertexWithUV((double)var12, (double)var15, (double)var16, (double)var9, (double)var10);
			var2.draw();
			GL11.glPopMatrix();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void updateEquippedItem() {
		this.prevEquippedProgress = this.equippedProgress;
		EntityPlayerSP var1 = this.mc.thePlayer;
		ItemStack var2 = var1.inventory.getCurrentItem();
		float var4 = 0.4F;
		float var5 = var2 == this.itemToRender ? 1.0F : 0.0F;
		float var6 = var5 - this.equippedProgress;
		if(var6 < -var4) {
			var6 = -var4;
		}

		if(var6 > var4) {
			var6 = var4;
		}

		this.equippedProgress += var6;
		if(this.equippedProgress < 0.1F) {
			this.itemToRender = var2;
		}

	}

	public void resetEquippedProgress() {
		this.equippedProgress = 0.0F;
	}

	public void resetEquippedProgress2() {
		this.equippedProgress = 0.0F;
	}
}
