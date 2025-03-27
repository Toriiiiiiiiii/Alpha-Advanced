package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderItem extends Render {
	private RenderBlocks itemRenderBlocks = new RenderBlocks();
	private Random random = new Random();

	public RenderItem() {
		this.shadowSize = 0.15F;
		this.shadowOpaque = 12.0F / 16.0F;
	}

	public void doRenderItem(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
		this.random.setSeed(187L);
		ItemStack var10 = var1.item;
		GL11.glPushMatrix();
		float var11 = MathHelper.sin(((float)var1.age + var9) / 10.0F + var1.hoverStart) * 0.1F + 0.1F;
		float var12 = (((float)var1.age + var9) / 20.0F + var1.hoverStart) * (180.0F / (float)Math.PI);
		byte var13 = 1;
		if(var1.item.stackSize > 1) {
			var13 = 2;
		}

		if(var1.item.stackSize > 5) {
			var13 = 3;
		}

		if(var1.item.stackSize > 20) {
			var13 = 4;
		}

		GL11.glTranslatef((float)var2, (float)var4 + var11, (float)var6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float var16;
		float var17;
		float var18;
		if(var10.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType())) {
			GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
			this.loadTexture("/terrain.png");
			float var27 = 0.25F;
			if(!Block.blocksList[var10.itemID].renderAsNormalBlock() && var10.itemID != Block.stairSingle.blockID) {
				var27 = 0.5F;
			}

			GL11.glScalef(var27, var27, var27);

			for(int var28 = 0; var28 < var13; ++var28) {
				GL11.glPushMatrix();
				if(var28 > 0) {
					var16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var27;
					var17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var27;
					var18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var27;
					GL11.glTranslatef(var16, var17, var18);
				}

				this.itemRenderBlocks.renderBlockOnInventory(Block.blocksList[var10.itemID]);
				GL11.glPopMatrix();
			}
		} else {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			int var14 = var10.getIconIndex();
			if(var10.itemID < 256) {
				this.loadTexture("/terrain.png");
			} else {
				this.loadTexture("/gui/items.png");
			}

			Tessellator var15 = Tessellator.instance;
			var16 = (float)(var14 % 16 * 16 + 0) / 256.0F;
			var17 = (float)(var14 % 16 * 16 + 16) / 256.0F;
			var18 = (float)(var14 / 16 * 16 + 0) / 256.0F;
			float var19 = (float)(var14 / 16 * 16 + 16) / 256.0F;
			float var20 = 1.0F;
			float var21 = 0.5F;
			float var22 = 0.25F;

			for(int var23 = 0; var23 < var13; ++var23) {
				GL11.glPushMatrix();
				if(var23 > 0) {
					float var24 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var25 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var26 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(var24, var25, var26);
				}

				GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				var15.startDrawingQuads();
				var15.setNormal(0.0F, 1.0F, 0.0F);
				var15.addVertexWithUV((double)(0.0F - var21), (double)(0.0F - var22), 0.0D, (double)var16, (double)var19);
				var15.addVertexWithUV((double)(var20 - var21), (double)(0.0F - var22), 0.0D, (double)var17, (double)var19);
				var15.addVertexWithUV((double)(var20 - var21), (double)(1.0F - var22), 0.0D, (double)var17, (double)var18);
				var15.addVertexWithUV((double)(0.0F - var21), (double)(1.0F - var22), 0.0D, (double)var16, (double)var18);
				var15.draw();
				GL11.glPopMatrix();
			}
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	public void renderItemIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
		if(var3 != null) {
			if(var3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType())) {
				int var6 = var3.itemID;
				var2.bindTexture(var2.getTexture("/terrain.png"));
				Block var7 = Block.blocksList[var6];
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(var4 - 2), (float)(var5 + 3), 0.0F);
				GL11.glScalef(10.0F, 10.0F, 10.0F);
				GL11.glTranslatef(1.0F, 0.5F, 8.0F);
				GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				this.itemRenderBlocks.renderBlockOnInventory(var7);
				GL11.glPopMatrix();
			} else if(var3.getIconIndex() >= 0) {
				GL11.glDisable(GL11.GL_LIGHTING);
				if(var3.itemID < 256) {
					var2.bindTexture(var2.getTexture("/terrain.png"));
				} else {
					var2.bindTexture(var2.getTexture("/gui/items.png"));
				}

				this.renderIcon(var4, var5, var3.getIconIndex() % 16 * 16, var3.getIconIndex() / 16 * 16, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	public void renderItemOverlayIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
		if(var3 != null) {
			if(var3.stackSize > 1) {
				String var6 = "" + var3.stackSize;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				var1.drawStringWithShadow(var6, var4 + 19 - 2 - var1.getStringWidth(var6), var5 + 6 + 3, 16777215);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}

			if(var3.itemDmg > 0) {
				int var11 = 13 - var3.itemDmg * 13 / var3.getMaxDamage();
				int var7 = 255 - var3.itemDmg * 255 / var3.getMaxDamage();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				Tessellator var8 = Tessellator.instance;
				int var9 = 255 - var7 << 16 | var7 << 8;
				int var10 = (255 - var7) / 4 << 16 | 16128;
				this.renderQuad(var8, var4 + 2, var5 + 13, 13, 2, 0);
				this.renderQuad(var8, var4 + 2, var5 + 13, 12, 1, var10);
				this.renderQuad(var8, var4 + 2, var5 + 13, var11, 1, var9);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

		}
	}

	private void renderQuad(Tessellator var1, int var2, int var3, int var4, int var5, int var6) {
		var1.startDrawingQuads();
		var1.setColorOpaque_I(var6);
		var1.addVertex((double)(var2 + 0), (double)(var3 + 0), 0.0D);
		var1.addVertex((double)(var2 + 0), (double)(var3 + var5), 0.0D);
		var1.addVertex((double)(var2 + var4), (double)(var3 + var5), 0.0D);
		var1.addVertex((double)(var2 + var4), (double)(var3 + 0), 0.0D);
		var1.draw();
	}

	public void renderIcon(int var1, int var2, int var3, int var4, int var5, int var6) {
		float var7 = 0.0F;
		float var8 = 0.00390625F;
		float var9 = 0.00390625F;
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), (double)var7, (double)((float)(var3 + 0) * var8), (double)((float)(var4 + var6) * var9));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), (double)var7, (double)((float)(var3 + var5) * var8), (double)((float)(var4 + var6) * var9));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), (double)var7, (double)((float)(var3 + var5) * var8), (double)((float)(var4 + 0) * var9));
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), (double)var7, (double)((float)(var3 + 0) * var8), (double)((float)(var4 + 0) * var9));
		var10.draw();
	}

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.doRenderItem((EntityItem)var1, var2, var4, var6, var8, var9);
	}
}
