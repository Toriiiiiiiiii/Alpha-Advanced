package net.minecraft.src;

final class StepSoundGlass extends StepSound {
	StepSoundGlass(String var1, float var2, float var3) {
		super(var1, var2, var3);
	}

	public String getBreakSound() {
		return "random.glass";
	}
}
