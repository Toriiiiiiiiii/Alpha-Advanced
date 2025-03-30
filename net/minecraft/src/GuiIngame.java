package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIngame extends Gui {
	private static RenderItem itemRenderer = new RenderItem();
	private List chatMessageList = new ArrayList();
	private Random rand = new Random();
	private Minecraft mc;
	public String testMessage = null;
	private int updateCounter = 0;
	private String recordPlaying = "";
	private int recordPlayingUpFor = 0;
	public float damageGuiPartialTime;
	float prevVignetteBrightness = 1.0F;

	public GuiIngame(Minecraft var1) {
		this.mc = var1;
	}

	public void renderGameOverlay(float var1, boolean var2, int var3, int var4) {
		
		ScaledResolution var5 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		int var6 = var5.getScaledWidth();
		int var7 = var5.getScaledHeight();
		FontRenderer var8 = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);
		if(this.mc.options.fancyGraphics) {
			this.renderVignette(this.mc.thePlayer.getBrightness(var1), var6, var7);
		}

		if(this.mc.hideUI) return;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
		InventoryPlayer var9 = this.mc.thePlayer.inventory;
		this.zLevel = -90.0F;
		this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
		this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var9.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
		GL11.glDisable(GL11.GL_BLEND);
		boolean var10 = this.mc.thePlayer.heartsLife / 3 % 2 == 1;
		if(this.mc.thePlayer.heartsLife < 10) {
			var10 = false;
		}

		int var11 = this.mc.thePlayer.health;
		int var12 = this.mc.thePlayer.prevHealth;
		this.rand.setSeed((long)(this.updateCounter * 312871));
		int var13;
		int var14;
		int var15;
		if(this.mc.playerController.shouldDrawHUD()) {
			var13 = this.mc.thePlayer.getPlayerArmorValue();

			int var16;
			for(var14 = 0; var14 < 10; ++var14) {
				var15 = var7 - 32;
				if(var13 > 0) {
					var16 = var6 / 2 + 91 - var14 * 8 - 9;
					if(var14 * 2 + 1 < var13) {
						this.drawTexturedModalRect(var16, var15, 34, 9, 9, 9);
					}

					if(var14 * 2 + 1 == var13) {
						this.drawTexturedModalRect(var16, var15, 25, 9, 9, 9);
					}

					if(var14 * 2 + 1 > var13) {
						this.drawTexturedModalRect(var16, var15, 16, 9, 9, 9);
					}
				}

				byte var25 = 0;
				if(var10) {
					var25 = 1;
				}

				int var17 = var6 / 2 - 91 + var14 * 8;
				if(var11 <= 4) {
					var15 += this.rand.nextInt(2);
				}

				this.drawTexturedModalRect(var17, var15, 16 + var25 * 9, 0, 9, 9);
				if(var10) {
					if(var14 * 2 + 1 < var12) {
						this.drawTexturedModalRect(var17, var15, 70, 0, 9, 9);
					}

					if(var14 * 2 + 1 == var12) {
						this.drawTexturedModalRect(var17, var15, 79, 0, 9, 9);
					}
				}

				if(var14 * 2 + 1 < var11) {
					this.drawTexturedModalRect(var17, var15, 52, 0, 9, 9);
				}

				if(var14 * 2 + 1 == var11) {
					this.drawTexturedModalRect(var17, var15, 61, 0, 9, 9);
				}
			}

			if(this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
				var14 = (int)Math.ceil((double)(this.mc.thePlayer.air - 2) * 10.0D / 300.0D);
				var15 = (int)Math.ceil((double)this.mc.thePlayer.air * 10.0D / 300.0D) - var14;

				for(var16 = 0; var16 < var14 + var15; ++var16) {
					if(var16 < var14) {
						this.drawTexturedModalRect(var6 / 2 - 91 + var16 * 8, var7 - 32 - 9, 16, 18, 9, 9);
					} else {
						this.drawTexturedModalRect(var6 / 2 - 91 + var16 * 8, var7 - 32 - 9, 25, 18, 9, 9);
					}
				}
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();

		for(var13 = 0; var13 < 9; ++var13) {
			var14 = var6 / 2 - 90 + var13 * 20 + 2;
			var15 = var7 - 16 - 3;
			this.renderInventorySlot(var13, var14, var15, var1);
		}

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		String var21;
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			var8.drawStringWithShadow("Minecraft Alpha Advanced (" + this.mc.debug + ")", 2, 2, 16777215);
			var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
			var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
			var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
			var8.drawStringWithShadow("Seed: " + this.mc.theWorld.randomSeed, 2, 42, 16777215);
			var8.drawStringWithShadow("Spawn: " + this.mc.theWorld.spawnX + ", " + this.mc.theWorld.spawnY + ", " + this.mc.theWorld.spawnZ, 2, 52, 16777215);
			var8.drawStringWithShadow("X: " + this.mc.thePlayer.posX, 2, 62, 16777215);
			var8.drawStringWithShadow("Y: " + this.mc.thePlayer.posY, 2, 72, 16777215);
			var8.drawStringWithShadow("Z: " + this.mc.thePlayer.posZ, 2, 82, 16777215);
			long var22 = Runtime.getRuntime().maxMemory();
			long var27 = Runtime.getRuntime().totalMemory();
			long var28 = Runtime.getRuntime().freeMemory();
			long var19 = var27 - var28;
			var21 = "Used memory: " + var19 * 100L / var22 + "% (" + var19 / 1024L / 1024L + "MB) of " + var22 / 1024L / 1024L + "MB";
			this.drawString(var8, var21, var6 - var8.getStringWidth(var21) - 2, 2, 14737632);
			var21 = "Allocated memory: " + var27 * 100L / var22 + "% (" + var27 / 1024L / 1024L + "MB)";
			this.drawString(var8, var21, var6 - var8.getStringWidth(var21) - 2, 12, 14737632);
		} else {
			var8.drawStringWithShadow("Minecraft Alpha Advanced", 2, 2, 16777215);
		}

		if(this.recordPlayingUpFor > 0) {
			float var23 = (float)this.recordPlayingUpFor - var1;
			var14 = (int)(var23 * 256.0F / 20.0F);
			if(var14 > 255) {
				var14 = 255;
			}

			if(var14 > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				var15 = Color.HSBtoRGB(var23 / 50.0F, 0.7F, 0.6F) & 16777215;
				var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var15 + (var14 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}

		byte var24 = 10;
		boolean var26 = false;
		if(this.mc.currentScreen instanceof GuiChat) {
			var24 = 20;
			var26 = true;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);

		for(var15 = 0; var15 < this.chatMessageList.size() && var15 < var24; ++var15) {
			if(((ChatLine)this.chatMessageList.get(var15)).updateCounter < 200 || var26) {
				double var29 = (double)((ChatLine)this.chatMessageList.get(var15)).updateCounter / 200.0D;
				var29 = 1.0D - var29;
				var29 *= 10.0D;
				if(var29 < 0.0D) {
					var29 = 0.0D;
				}

				if(var29 > 1.0D) {
					var29 = 1.0D;
				}

				var29 *= var29;
				int var18 = (int)(255.0D * var29);
				if(var26) {
					var18 = 255;
				}

				if(var18 > 0) {
					byte var30 = 2;
					int var20 = -var15 * 9;
					var21 = ((ChatLine)this.chatMessageList.get(var15)).message;
					this.drawRect(var30, var20 - 1, var30 + 320, var20 + 8, var18 / 2 << 24);
					GL11.glEnable(GL11.GL_BLEND);
					var8.drawStringWithShadow(var21, var30, var20, 16777215 + (var18 << 24));
				}
			}
		}

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void renderVignette(float var1, int var2, int var3) {
		var1 = 1.0F - var1;
		if(var1 < 0.0F) {
			var1 = 0.0F;
		}

		if(var1 > 1.0F) {
			var1 = 1.0F;
		}

		this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(var1 - this.prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/misc/vignette.png"));
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		var4.addVertexWithUV(0.0D, (double)var3, -90.0D, 0.0D, 1.0D);
		var4.addVertexWithUV((double)var2, (double)var3, -90.0D, 1.0D, 1.0D);
		var4.addVertexWithUV((double)var2, 0.0D, -90.0D, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void renderInventorySlot(int var1, int var2, int var3, float var4) {
		ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[var1];
		if(var5 != null) {
			float var6 = (float)var5.animationsToGo - var4;
			if(var6 > 0.0F) {
				GL11.glPushMatrix();
				float var7 = 1.0F + var6 / 5.0F;
				GL11.glTranslatef((float)(var2 + 8), (float)(var3 + 12), 0.0F);
				GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(var2 + 8)), (float)(-(var3 + 12)), 0.0F);
			}

			itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
			if(var6 > 0.0F) {
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
		}
	}

	public void updateTick() {
		if(this.recordPlayingUpFor > 0) {
			--this.recordPlayingUpFor;
		}

		++this.updateCounter;

		for(int var1 = 0; var1 < this.chatMessageList.size(); ++var1) {
			++((ChatLine)this.chatMessageList.get(var1)).updateCounter;
		}

	}

	public void addChatMessage(String var1) {
		while(this.mc.fontRenderer.getStringWidth(var1) > 320) {
			int var2;
			for(var2 = 1; var2 < var1.length() && this.mc.fontRenderer.getStringWidth(var1.substring(0, var2 + 1)) <= 320; ++var2) {
			}

			this.addChatMessage(var1.substring(0, var2));
			var1 = var1.substring(var2);
		}

		this.chatMessageList.add(0, new ChatLine(var1));

		while(this.chatMessageList.size() > 50) {
			this.chatMessageList.remove(this.chatMessageList.size() - 1);
		}

	}

	public void setRecordPlayingMessage(String var1) {
		this.recordPlaying = "Now playing: " + var1;
		this.recordPlayingUpFor = 60;
	}
}
