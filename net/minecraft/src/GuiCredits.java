package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class GuiCredits extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected = false;
	private int numWorlds = 0;
	private int firstEmptyWorld = 0;

	public GuiCredits(GuiScreen var1) {
		this.parentScreen = var1;
	}

	public void initGui() {
		this.controlList.clear();

		this.screenTitle = "Credits";
		this.initButtons();
	}

	protected String getSaveName(int var1) {
		File var2 = Minecraft.getMinecraftDir();
		return World.getLevelData(var2, "World" + var1) != null ? "World" + var1 : null;
	}

	public void initButtons() {
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 168, "Back"));
		
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6, "Tori - Lead Developer"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 6 + 24, "retrorandom - Contributor"));
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height / 6 + 2*24, "i3riced - Contributor"));
		this.controlList.add(new GuiButton(4, this.width / 2 - 100, this.height / 6 + 3*24, "ChimneyEgg - Contributor"));
		this.controlList.add(new GuiButton(5, this.width / 2 - 100, this.height / 6 + 4*24, "Mongster - Art"));
		
		((GuiButton)this.controlList.get(1)).enabled = false;
		((GuiButton)this.controlList.get(2)).enabled = false;
		((GuiButton)this.controlList.get(3)).enabled = false;
		((GuiButton)this.controlList.get(4)).enabled = false;
		((GuiButton)this.controlList.get(5)).enabled = false;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 0) this.mc.displayGuiScreen(parentScreen);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
