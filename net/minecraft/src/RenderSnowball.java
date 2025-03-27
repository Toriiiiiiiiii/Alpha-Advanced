package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSnowball extends Render {
	public void doRenderSnowball(EntitySnowball var1, double var2, double var4, double var6, float var8, float var9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		int var10 = Item.snowball.getIconIndex((ItemStack)null);
		this.loadTexture("/gui/items.png");
		Tessellator var11 = Tessellator.instance;
		float var12 = (float)(var10 % 16 * 16 + 0) / 256.0F;
		float var13 = (float)(var10 % 16 * 16 + 16) / 256.0F;
		float var14 = (float)(var10 / 16 * 16 + 0) / 256.0F;
		float var15 = (float)(var10 / 16 * 16 + 16) / 256.0F;
		float var16 = 1.0F;
		float var17 = 0.5F;
		float var18 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var11.startDrawingQuads();
		var11.setNormal(0.0F, 1.0F, 0.0F);
		var11.addVertexWithUV((double)(0.0F - var17), (double)(0.0F - var18), 0.0D, (double)var12, (double)var15);
		var11.addVertexWithUV((double)(var16 - var17), (double)(0.0F - var18), 0.0D, (double)var13, (double)var15);
		var11.addVertexWithUV((double)(var16 - var17), (double)(1.0F - var18), 0.0D, (double)var13, (double)var14);
		var11.addVertexWithUV((double)(0.0F - var17), (double)(1.0F - var18), 0.0D, (double)var12, (double)var14);
		var11.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.doRenderSnowball((EntitySnowball)var1, var2, var4, var6, var8, var9);
	}
}
