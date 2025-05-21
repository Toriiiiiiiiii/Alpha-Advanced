package net.minecraft.src;

import java.io.File;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class GuiNewWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Create world";
	private boolean selected = false;
	
	private int worldType;
	private int worldID;
	
	private String seedText = "";
	private int updateCounter = 0;
	
	private int state = 0;

	public GuiNewWorld(GuiScreen var1, int id) {
		this.parentScreen = var1;
		this.worldID = id;
	}

	public void initGui() {
		File var1 = Minecraft.getMinecraftDir();

		this.controlList.clear();
		if(state == 0) {
			this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 56, "Season: Random"));
			this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 86, "Next"));
		}
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

	private void actionState0(GuiButton var1) {
		if(var1.id == 0) {
			this.worldType++;
			if(this.worldType > 4) this.worldType = 0;
			if(this.worldType == 4) 
				var1.displayString = "Season: Winter";
			else if(this.worldType == 3)
				var1.displayString = "Season: Autumn";
			else if(this.worldType == 1) 
				var1.displayString = "Season: Spring";
			else if(this.worldType == 2)
				var1.displayString = "Season: Summer";
			else
				var1.displayString = "Season: Random";
		}
		
		if(var1.id == 1) {
			int type = this.worldType;
			if(type == 0) {
				type = new Random().nextInt(4) + 1;
			}
			this.selectWorld(type == 4, type == 3, type == 1, this.seedText.length() > 0 ? (int)(Long.parseLong(this.seedText)) : 0);
		}
		
		if(var1.id == 6) {
			this.mc.displayGuiScreen(parentScreen);
		}
	}
	
	protected void actionPerformed(GuiButton var1) {
		if(state == 0) this.actionState0(var1);
		
	}

	public void selectWorld(boolean snowy, boolean autumn, boolean spring, int seed) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("World" + this.worldID, snowy, autumn, spring, seed);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		this.drawCenteredString(this.fontRenderer, "Seed (leave blank for random)", this.width / 2, 60, 16777215);
		int var4 = this.width / 2 - 100;
		int var5 = this.height / 6 + 32;
		short var6 = 200;
		byte var7 = 20;
		this.drawRect(var4 - 1, var5 - 1, var4 + var6 + 1, var5 + var7 + 1, -6250336);
		this.drawRect(var4, var5, var4 + var6, var5 + var7, -16777216);
		this.drawString(this.fontRenderer, this.seedText + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), var4 + 4, var5 + (var7 - 8) / 2, 14737632);
		super.drawScreen(var1, var2, var3);
	}
	
	protected void keyTyped(char var1, int var2) {
		if(var1 == 22) {
			String var3 = GuiScreen.getClipboardString();
			if(var3 == null) {
				var3 = "";
			}

			int var4 = 11 - this.seedText.length();
			if(var4 > var3.length()) {
				var4 = var3.length();
			}

			if(var4 > 0) {
				this.seedText = this.seedText + var3.substring(0, var4);
			}
		}

		if(var1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		if(var2 == 14 && this.seedText.length() > 0) {
			this.seedText = this.seedText.substring(0, this.seedText.length() - 1);
		}

		if("-0123456789".indexOf(var1) >= 0 && this.seedText.length() < 11) {
			this.seedText = this.seedText + var1;
		}

		// ((GuiButton)this.controlList.get(0)).enabled = this.seedText.length() > 0;
	}
}
