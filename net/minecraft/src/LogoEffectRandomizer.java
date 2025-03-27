package net.minecraft.src;

class LogoEffectRandomizer {
	public double height;
	public double prevHeight;
	public double dropSpeed;
	final GuiMainMenu mainMenu;

	public LogoEffectRandomizer(GuiMainMenu var1, int var2, int var3) {
		this.mainMenu = var1;
		this.height = this.prevHeight = (double)(10 + var3) + GuiMainMenu.getRandom().nextDouble() * 32.0D + (double)var2;
	}

	public void updateLogoEffects() {
		this.prevHeight = this.height;
		if(this.height > 0.0D) {
			this.dropSpeed -= 0.6D;
		}

		this.height += this.dropSpeed;
		this.dropSpeed *= 0.9D;
		if(this.height < 0.0D) {
			this.height = 0.0D;
			this.dropSpeed = 0.0D;
		}

	}
}
