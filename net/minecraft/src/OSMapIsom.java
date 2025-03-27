package net.minecraft.src;

class OSMapIsom {
	static final int[] osValues = new int[EnumOSIsom.values().length];

	static {
		try {
			osValues[EnumOSIsom.linux.ordinal()] = 1;
		} catch (NoSuchFieldError var4) {
		}

		try {
			osValues[EnumOSIsom.solaris.ordinal()] = 2;
		} catch (NoSuchFieldError var3) {
		}

		try {
			osValues[EnumOSIsom.windows.ordinal()] = 3;
		} catch (NoSuchFieldError var2) {
		}

		try {
			osValues[EnumOSIsom.macos.ordinal()] = 4;
		} catch (NoSuchFieldError var1) {
		}

	}
}
