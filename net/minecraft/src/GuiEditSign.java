package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign extends GuiScreen {
	protected String screenTitle = "Edit sign message:";
	private TileEntitySign entitySign;
	private int updateCounter;
	private int editLine = 0;

	public GuiEditSign(TileEntitySign var1) {
		this.entitySign = var1;
	}

	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 0) {
				this.entitySign.onInventoryChanged();
				this.mc.displayGuiScreen((GuiScreen)null);
			}

		}
	}

	protected void keyTyped(char var1, int var2) {
		if(var2 == 200) {
			this.editLine = this.editLine - 1 & 3;
		}

		if(var2 == 208 || var2 == 28) {
			this.editLine = this.editLine + 1 & 3;
		}

		if(var2 == 14 && this.entitySign.signText[this.editLine].length() > 0) {
			this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
		}

		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(var1) >= 0 && this.entitySign.signText[this.editLine].length() < 15) {
			this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine] + var1;
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2), (float)(this.height / 2), 50.0F);
		float var4 = 93.75F;
		GL11.glScalef(-var4, -var4, -var4);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		Block var5 = this.entitySign.getBlockType();
		if(var5 == Block.signStanding) {
			float var6 = (float)(this.entitySign.getBlockMetadata() * 360) / 16.0F;
			GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
		} else {
			int var8 = this.entitySign.getBlockMetadata();
			float var7 = 0.0F;
			if(var8 == 2) {
				var7 = 180.0F;
			}

			if(var8 == 4) {
				var7 = 90.0F;
			}

			if(var8 == 5) {
				var7 = -90.0F;
			}

			GL11.glRotatef(var7, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 5.0F / 16.0F, 0.0F);
		}

		if(this.updateCounter / 6 % 2 == 0) {
			this.entitySign.lineBeingEdited = this.editLine;
		}

		TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
		this.entitySign.lineBeingEdited = -1;
		GL11.glPopMatrix();
		super.drawScreen(var1, var2, var3);
	}
}
