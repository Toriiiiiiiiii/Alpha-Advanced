package net.minecraft.src;

public class GuiOptions extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Options";
	private GameSettings options;

	public GuiOptions(GuiScreen var1, GameSettings var2) {
		this.parentScreen = var1;
		this.options = var2;
	}

	public void initGui() {
		this.controlList.clear();
		for(int var1 = 0; var1 < this.options.numberOfOptions; ++var1) {
			int var2 = this.options.isSlider(var1);
			if(var2 == 0) {
				this.controlList.add(new GuiSmallButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 8 + 24 * (var1 >> 1), this.options.getOptionDisplayString(var1)));
			} else {
				this.controlList.add(new GuiSlider(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 8 + 24 * (var1 >> 1), var1, this.options.getOptionDisplayString(var1), this.options.getOptionFloatValue(var1)));
			}
		}

		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 120 + 36, "Controls..."));
		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 12, "Done"));
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id < 100) {
				this.options.setOptionValue(var1.id, 1);
				var1.displayString = this.options.getOptionDisplayString(var1.id);
			}

			if(var1.id == 100) {
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if(var1.id == 200) {
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
