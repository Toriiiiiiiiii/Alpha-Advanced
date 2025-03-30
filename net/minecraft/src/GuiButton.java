package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiButton extends Gui {
	protected int width;
	protected int height;
	public int xPosition;
	public int yPosition;
	public String displayString;
	public int id;
	public boolean enabled;
	public boolean visible;

	public GuiButton(int var1, int var2, int var3, String var4) {
		this(var1, var2, var3, 200, 20, var4);
	}

	protected GuiButton(int var1, int var2, int var3, int var4, int var5, String var6) {
		this.width = 200;
		this.height = 20;
		this.enabled = true;
		this.visible = true;
		this.id = var1;
		this.xPosition = var2;
		this.yPosition = var3;
		this.width = var4;
		this.height = var5;
		this.displayString = var6;
	}

	protected int getHoverState(boolean var1) {
		byte var2 = 1;
		if(!this.enabled) {
			var2 = 0;
		} else if(var1) {
			var2 = 2;
		}

		return var2;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(this.visible) {
			FontRenderer fontRenderer = mc.fontRenderer;
			if(mc.options.darkMode) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gui/gui_dark.png"));
			} else {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gui/gui.png"));
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int hoverState = this.getHoverState(hovered);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + hoverState * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + hoverState * 20, this.width / 2, this.height);
			this.mouseDragged(mc, mouseX, mouseY);
			if(!this.enabled) {
				this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
			} else if(hovered) {
				this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
			} else {
				this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
			}

		}
	}

	protected void mouseDragged(Minecraft var1, int var2, int var3) {
	}

	public void mouseReleased(int var1, int var2) {
	}

	public boolean mousePressed(Minecraft var1, int var2, int var3) {
		return this.enabled && var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height;
	}
}
