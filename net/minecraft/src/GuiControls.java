package net.minecraft.src;

public class GuiControls extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Controls";
	private GameSettings options;
	private int buttonId = -1;

	public GuiControls(GuiScreen var1, GameSettings var2) {
		this.parentScreen = var1;
		this.options = var2;
	}

	public void initGui() {
		this.controlList.clear();
		for(int var1 = 0; var1 < this.options.keyBindings.length; ++var1) {
			this.controlList.add(new GuiSmallButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.options.getKeyBindingDescription(var1)));
		}

		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
	}

	protected void actionPerformed(GuiButton var1) {
		for(int var2 = 0; var2 < this.options.keyBindings.length; ++var2) {
			((GuiButton)this.controlList.get(var2)).displayString = this.options.getKeyBindingDescription(var2);
		}

		if(var1.id == 200) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else {
			this.buttonId = var1.id;
			var1.displayString = "> " + this.options.getKeyBindingDescription(var1.id) + " <";
		}

	}

	protected void keyTyped(char var1, int var2) {
		if(this.buttonId >= 0) {
			this.options.setKeyBinding(this.buttonId, var2);
			((GuiButton)this.controlList.get(this.buttonId)).displayString = this.options.getKeyBindingDescription(this.buttonId);
			this.buttonId = -1;
		} else {
			super.keyTyped(var1, var2);
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
