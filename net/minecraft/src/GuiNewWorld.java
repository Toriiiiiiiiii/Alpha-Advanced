package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class GuiNewWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Create world";
	private boolean selected = false;
	
	private int worldType;
	private int worldID;

	public GuiNewWorld(GuiScreen var1, int id) {
		this.parentScreen = var1;
		this.worldID = id;
	}

	public void initGui() {
		File var1 = Minecraft.getMinecraftDir();

		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 26, "World Type: Normal"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 56, "Create!"));
//		for(int var2 = 0; var2 < 5; ++var2) {
//			NBTTagCompound var3 = World.getLevelData(var1, "World" + (var2 + 1));
//			if(var3 == null) {
//				this.controlList.add(new GuiButton(var2, this.width / 2 - 100, this.height / 6 + 24 * var2, "- empty -"));
//			} else {
//				String var4 = "World " + (var2 + 1);
//				long var5 = var3.getLong("SizeOnDisk");
//				var4 = var4 + " (" + (float)(var5 / 1024L * 100L / 1024L) / 100.0F + " MB)";
//				this.controlList.add(new GuiButton(var2, this.width / 2 - 100, this.height / 6 + 24 * var2, var4));
//			}
//		}

		this.initButtons();
	}

	protected String getSaveName(int var1) {
		File var2 = Minecraft.getMinecraftDir();
		return World.getLevelData(var2, "World" + var1) != null ? "World" + var1 : null;
	}

	public void initButtons() {
		this.controlList.add(new GuiButton(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.id == 0) {
			this.worldType++;
			if(this.worldType > 2) this.worldType = 0;
			if(this.worldType == 1) 
				var1.displayString = "World Type: Winter";
			else if(this.worldType == 2)
				var1.displayString = "World Type: Autumn";
			else
				var1.displayString = "World Type: Normal";
		}
		
		if(var1.id == 1) {
			this.selectWorld(this.worldType == 1, this.worldType == 2);
		}
		
		if(var1.id == 6) {
			this.mc.displayGuiScreen(parentScreen);
		}
	}

	public void selectWorld(boolean snowy, boolean autumn) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("World" + this.worldID, snowy, autumn);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
