package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class GuiDeleteWorld extends GuiSelectWorld {
	public GuiDeleteWorld(GuiScreen var1) {
		super(var1);
		this.screenTitle = "Delete world";
	}

	public void initButtons() {
		this.controlList.add(new GuiButton(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}
	
	// Closes the delete world gui - retrorandom
	protected void actionPerformed(GuiButton button) {
	    if (button.id == 6) {
	        this.mc.displayGuiScreen(null); 
	    }
	}
	
	public void selectWorld(int var1) {
		String var2 = this.getSaveName(var1);
		if(var2 != null) {
			this.mc.displayGuiScreen(new GuiYesNo(this, "Are you sure you want to delete this world?", "\'" + var2 + "\' will be lost forever!", var1));
		}

	}

	public void deleteWorld(boolean var1, int var2) {
		if(var1) {
			File var3 = Minecraft.getMinecraftDir();
			World.deleteWorld(var3, this.getSaveName(var2));
		}

		this.mc.displayGuiScreen(this.parentScreen);
	}
}
