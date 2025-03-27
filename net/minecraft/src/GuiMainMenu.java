package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen {
	private static final Random rand = new Random();
	String[] logoBlockLayers = new String[]{" *** *   *** * * ***  *** **  * * *** *   * *** *** **  ", 
			                                " * * *   * * * * * *  * * * * * * * * **  * *   *   * * ",
			                                " *** *   *** *** ***  *** * * * * *** * * * *   *** * * ", 
			                                " * * *   *   * * * *  * * * * * * * * *  ** *   *   * * ", 
			                                " * * *** *   * * * *  * * **   *  * * *   * *** *** **  "};
	
	private LogoEffectRandomizer[][] logoEffects;
	private float updateCounter = 0.0F;
	private String splashString = "missingno";
	private int bgIndex = 0;
	int millisToChangeBackground;
	
	private long lastTime;
	private int fadeState = 0;
	private int fadeTimer = 0;
	private long bgCol = 0xefefef;

	public GuiMainMenu() {
		try {
			ArrayList var1 = new ArrayList();
			BufferedReader var2 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt")));
			String var3 = "";

			while(true) {
				var3 = var2.readLine();
				if(var3 == null) {
					this.splashString = (String)var1.get(rand.nextInt(var1.size()));
					break;
				}

				var3 = var3.trim();
				if(var3.length() > 0) {
					var1.add(var3);
				}
			}
		} catch (Exception var4) {
		}

	}

	public void updateScreen() {
		++this.updateCounter;
		if(this.logoEffects != null) {
			for(int var1 = 0; var1 < this.logoEffects.length; ++var1) {
				for(int var2 = 0; var2 < this.logoEffects[var1].length; ++var2) {
					this.logoEffects[var1][var2].updateLogoEffects();
				}
			}
		}

	}

	protected void keyTyped(char var1, int var2) {
	}

	public void initGui() {
		Random rand = new Random();
		this.bgIndex = 1 + rand.nextInt(4);
		this.millisToChangeBackground = 5000;
		
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());
		if(var1.get(2) + 1 == 11 && var1.get(5) == 9) {
			this.splashString = "Happy birthday, ez!";
		} else if(var1.get(2) + 1 == 6 && var1.get(5) == 1) {
			this.splashString = "Happy birthday, Notch!";
		} else if(var1.get(2) + 1 == 12 && var1.get(5) == 24) {
			this.splashString = "Merry X-mas!";
		} else if(var1.get(2) + 1 == 1 && var1.get(5) == 1) {
			this.splashString = "Happy new year!";
		}

		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Singleplayer"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "Multiplayer"));
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96, "Play tutorial level"));
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Options..."));
		((GuiButton)this.controlList.get(1)).enabled = false;
		((GuiButton)this.controlList.get(2)).enabled = false;
		if(this.mc.session == null) {
			((GuiButton)this.controlList.get(1)).enabled = false;
		}

		this.lastTime = System.currentTimeMillis();
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.options));
		}

		if(var1.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if(var1.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

	}

	public void drawBack(int var1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var2 = Tessellator.instance;
		
		Random rand = new Random();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/title/" + this.bgIndex + ".png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var2.startDrawingQuads();
		var2.setColorOpaque_I((int)this.bgCol);
		var2.addVertexWithUV(0, this.height, 0, 0, 1);
		var2.addVertexWithUV(this.width, this.height, 0, 1, 1);
		var2.addVertexWithUV(this.width, 0, 0, 1, 0);
		var2.addVertexWithUV(0, 0, 0, 0, 0);
//		var2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / var3 + (float)var1));
//		var2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / var3), (double)((float)this.height / var3 + (float)var1));
//		var2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / var3), (double)(0 + var1));
//		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)(0 + var1));
		var2.draw();
		
		if(this.fadeState == 1) {
//			this.bgCol -= (long)((float)0x010101 * (float)System.currentTimeMillis() / 1000F);
			int change = System.currentTimeMillis() % 3 == 0 ? 1 : 0;
			this.bgCol -= change | (change << 8) | (change << 16);
			
			if(this.bgCol <= 0) {
				this.fadeState = 2;
				this.bgIndex++;
				if(this.bgIndex > 6) this.bgIndex -= 6;
			}
			
			return;
		}
		
		if(this.fadeState == 2) {
//			this.bgCol += (long)((float)0x010101 * (float)System.currentTimeMillis() / 1000F);
			int change = System.currentTimeMillis() % 3 == 0 ? 1 : 0;
			this.bgCol += change | (change << 8) | (change << 16);
			if(this.bgCol >= 0xefefef) {
				this.fadeState = 0;
			}
			
			return;
		}
		
		if(this.millisToChangeBackground > 0) {
			this.millisToChangeBackground -= System.currentTimeMillis() - this.lastTime;
			this.lastTime = System.currentTimeMillis();
			return;
		}
		
		this.fadeState = 1;
		this.millisToChangeBackground = 5000;
		System.currentTimeMillis();
	}
	
	public void drawScreen(int var1, int var2, float var3) {
		this.drawBack(0);
		
		Tessellator var4 = Tessellator.instance;
		this.drawLogo(var3);
		Random rand = new Random();
		int text = 1 + rand.nextInt(5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/logo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var4.setColorOpaque_I(16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float var5 = 1.8F - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
		var5 = var5 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashString) + 32);
		GL11.glScalef(var5, var5, var5);
		this.drawCenteredString(this.fontRenderer, this.splashString, 0, -8, 16776960);
		GL11.glPopMatrix();
		String var6 = "Alpha Advanced Public Beta Build b1.3.3 by Tori";
		this.drawString(this.fontRenderer, var6, this.width - this.fontRenderer.getStringWidth(var6) - 2, this.height - 10, 16777215);
		long var7 = Runtime.getRuntime().maxMemory();
		long var9 = Runtime.getRuntime().totalMemory();
		long var11 = Runtime.getRuntime().freeMemory();
		long var13 = var7 - var11;
		var6 = "Free memory: " + var13 * 100L / var7 + "% of " + var7 / 1024L / 1024L + "MB";
		this.drawString(this.fontRenderer, var6, this.width - this.fontRenderer.getStringWidth(var6) - 2, 2, 16777215);
		var6 = "Allocated memory: " + var9 * 100L / var7 + "% (" + var9 / 1024L / 1024L + "MB)";
		this.drawString(this.fontRenderer, var6, this.width - this.fontRenderer.getStringWidth(var6) - 2, 12, 16777215);
		
		super.drawScreen(var1, var2, var3);
		
	}

	private void drawLogo(float var1) {
		int var3;
		if(this.logoEffects == null) {
			this.logoEffects = new LogoEffectRandomizer[this.logoBlockLayers[0].length()][this.logoBlockLayers.length];

			for(int var2 = 0; var2 < this.logoEffects.length; ++var2) {
				for(var3 = 0; var3 < this.logoEffects[var2].length; ++var3) {
					this.logoEffects[var2][var3] = new LogoEffectRandomizer(this, var2, var3);
				}
			}
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution var14 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		var3 = 120 * var14.scaleFactor;
		GLU.gluPerspective(70.0F, (float)this.mc.displayWidth / (float)var3, 0.05F, 100.0F);
		GL11.glViewport(0, this.mc.displayHeight - var3, this.mc.displayWidth, var3);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDepthMask(true);

		for(int var4 = 0; var4 < 3; ++var4) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.4F, 0.6F, -12.0F);
			if(var4 == 0) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glTranslatef(0.0F, -0.4F, 0.0F);
				GL11.glScalef(0.98F, 1.0F, 1.0F);
//				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}
			
			if(var4 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}

			if(var4 == 2) {
				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(0.89F, 1.0F, 0.4F);
			GL11.glTranslatef((float)(-this.logoBlockLayers[0].length()) * 0.5F, (float)(-this.logoBlockLayers.length) * 0.5F, 0.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(var4 == 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/black.png"));
			}

			RenderBlocks var5 = new RenderBlocks();

			for(int var6 = 0; var6 < this.logoBlockLayers.length; ++var6) {
				for(int var7 = 0; var7 < this.logoBlockLayers[var6].length(); ++var7) {
					char var8 = this.logoBlockLayers[var6].charAt(var7);
					if(var8 != 32) {
						GL11.glPushMatrix();
						LogoEffectRandomizer var9 = this.logoEffects[var7][var6];
						float var10 = (float)(var9.prevHeight + (var9.height - var9.prevHeight) * (double)var1);
						float var11 = 1.0F;
						float var12 = 1.0F;
						float var13 = 0.0F;
						if(var4 == 0) {
							var11 = var10 * 0.04F + 1.0F;
							var12 = 1.0F / var11;
							var10 = 0.0F;
						}

						GL11.glTranslatef((float)var7, (float)var6, var10);
						GL11.glScalef(var11, var11, var11);
						GL11.glRotatef(var13, 0.0F, 1.0F, 0.0F);
						var5.renderBlockAsItem(Block.stone, var12);
						GL11.glPopMatrix();
					}
				}
			}

			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	static Random getRandom() {
		return rand;
	}
}
