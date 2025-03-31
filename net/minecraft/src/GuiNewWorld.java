package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class GuiNewWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Create world";
	private boolean selected = false;
	
	private int worldType;
	private int worldID;
	
	private String seedText = "";
	private int updateCounter = 0;

	public GuiNewWorld(GuiScreen var1, int id) {
		this.parentScreen = var1;
		this.worldID = id;
	}

	public void initGui() {
		File var1 = Minecraft.getMinecraftDir();

		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 56, "World Type: Normal"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 86, "Create!"));
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
			this.selectWorld(this.worldType == 1, this.worldType == 2, this.seedText.length() > 0 ? Integer.parseInt(this.seedText) : 0);
		}
		
		if(var1.id == 6) {
			this.mc.displayGuiScreen(parentScreen);
		}
	}

	public void selectWorld(boolean snowy, boolean autumn, int seed) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("World" + this.worldID, snowy, autumn, seed);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		this.drawCenteredString(this.fontRenderer, "Seed (leave blank for random)", this.width / 2, 60, 16777215);
		int var4 = this.width / 2 - 100;
		int var5 = this.height / 4 + 10;
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

			int var4 = 32 - this.seedText.length();
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

		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(var1) >= 0 && this.seedText.length() < 32) {
			this.seedText = this.seedText + var1;
		}

		// ((GuiButton)this.controlList.get(0)).enabled = this.seedText.length() > 0;
	}
}
