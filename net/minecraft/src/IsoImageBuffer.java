package net.minecraft.src;

import java.awt.image.BufferedImage;

public class IsoImageBuffer {
	public BufferedImage image;
	public World level;
	public int x;
	public int y;
	public boolean rendered = false;
	public boolean noContent = false;
	public int lastVisible = 0;
	public boolean addedToRenderQueue = false;

	public IsoImageBuffer(World var1, int var2, int var3) {
		this.level = var1;
		this.init(var2, var3);
	}

	public void init(int var1, int var2) {
		this.rendered = false;
		this.x = var1;
		this.y = var2;
		this.lastVisible = 0;
		this.addedToRenderQueue = false;
	}

	public void setLevel(World var1, int var2, int var3) {
		this.level = var1;
		this.init(var2, var3);
	}
}
