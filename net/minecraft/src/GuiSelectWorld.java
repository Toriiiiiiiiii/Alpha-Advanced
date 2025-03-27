package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class GuiSelectWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected = false;
	private int numWorlds = 0;
	private int firstEmptyWorld = 0;

	public GuiSelectWorld(GuiScreen var1) {
		this.parentScreen = var1;
	}

	public void initGui() {
		this.controlList.clear();
		this.numWorlds = 0;
		
		File var1 = Minecraft.getMinecraftDir();
		boolean emptyFound = false;

		for(int var2 = 0; var2 < 5; ++var2) {
			NBTTagCompound var3 = World.getLevelData(var1, "World" + (var2 + 1));
			if(var3 == null) {
				if(!emptyFound) {
					this.firstEmptyWorld = var2;
					emptyFound = true;
				}
			} else {
				String var4 = "World " + (var2 + 1);
				long var5 = var3.getLong("SizeOnDisk");
				var4 = var4 + " (" + (float)(var5 / 1024L * 100L / 1024L) / 100.0F + " MB)";
				this.controlList.add(new GuiButton(var2, this.width / 2 - 100, this.height / 6 + 24 * this.numWorlds, var4));
				this.numWorlds++;
			}
		}

		this.screenTitle = "Select world (" + this.numWorlds + "/5)";
		this.initButtons();
	}

	protected String getSaveName(int var1) {
		File var2 = Minecraft.getMinecraftDir();
		return World.getLevelData(var2, "World" + var1) != null ? "World" + var1 : null;
	}

	public void initButtons() {
		if(this.numWorlds < 5) this.controlList.add(new GuiButton(5, this.width / 2 - 100, this.height / 6 + 122, "Create world..."));
		this.controlList.add(new GuiButton(6, this.width / 2 - 100, this.height / 6 + 120 + 24, "Delete world..."));
		this.controlList.add(new GuiButton(7, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id < 5) {
				this.selectWorld(var1.id + 1);
			} else if(var1.id == 5) {
				this.mc.displayGuiScreen(new GuiNewWorld(this, this.firstEmptyWorld+1));
			} if(var1.id == 6) {
				this.mc.displayGuiScreen(new GuiDeleteWorld(this));
			} else if(var1.id == 7) {
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void selectWorld(int var1) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("World" + var1, false);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
