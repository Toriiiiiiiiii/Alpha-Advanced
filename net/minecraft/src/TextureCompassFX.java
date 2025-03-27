package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureCompassFX extends TextureFX {
	protected float[] currentFireFrame = new float[320];
	protected float[] lastFireFrame = new float[320];
	private Minecraft mc;
	private int[] compassIconImageData = new int[256];
	private double currentAngle;
	private double angleDelta;

	public TextureCompassFX(Minecraft var1) {
		super(Item.compass.getIconIndex((ItemStack)null));
		this.mc = var1;
		this.tileImage = 1;

		try {
			BufferedImage var2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
			int var3 = this.iconIndex % 16 * 16;
			int var4 = this.iconIndex / 16 * 16;
			var2.getRGB(var3, var4, 16, 16, this.compassIconImageData, 0, 16);
		} catch (IOException var5) {
			var5.printStackTrace();
		}

	}

	public void onTick() {
		for(int var1 = 0; var1 < 256; ++var1) {
			int var2 = this.compassIconImageData[var1] >> 24 & 255;
			int var3 = this.compassIconImageData[var1] >> 16 & 255;
			int var4 = this.compassIconImageData[var1] >> 8 & 255;
			int var5 = this.compassIconImageData[var1] >> 0 & 255;
			if(this.anaglyphEnabled) {
				int var6 = (var3 * 30 + var4 * 59 + var5 * 11) / 100;
				int var7 = (var3 * 30 + var4 * 70) / 100;
				int var8 = (var3 * 30 + var5 * 70) / 100;
				var3 = var6;
				var4 = var7;
				var5 = var8;
			}

			this.imageData[var1 * 4 + 0] = (byte)var3;
			this.imageData[var1 * 4 + 1] = (byte)var4;
			this.imageData[var1 * 4 + 2] = (byte)var5;
			this.imageData[var1 * 4 + 3] = (byte)var2;
		}

		double var20 = 0.0D;
		double var21;
		double var22;
		if(this.mc.theWorld != null && this.mc.thePlayer != null) {
			var21 = (double)this.mc.theWorld.spawnX - this.mc.thePlayer.posX;
			var22 = (double)this.mc.theWorld.spawnZ - this.mc.thePlayer.posZ;
			var20 = (double)(this.mc.thePlayer.rotationYaw - 90.0F) * Math.PI / 180.0D - Math.atan2(var22, var21);
		}

		for(var21 = var20 - this.currentAngle; var21 < -Math.PI; var21 += Math.PI * 2.0D) {
		}

		while(var21 >= Math.PI) {
			var21 -= Math.PI * 2.0D;
		}

		if(var21 < -1.0D) {
			var21 = -1.0D;
		}

		if(var21 > 1.0D) {
			var21 = 1.0D;
		}

		this.angleDelta += var21 * 0.1D;
		this.angleDelta *= 0.8D;
		this.currentAngle += this.angleDelta;
		var22 = Math.sin(this.currentAngle);
		double var23 = Math.cos(this.currentAngle);

		int var9;
		int var10;
		int var11;
		int var12;
		int var13;
		int var14;
		int var15;
		short var16;
		int var17;
		int var18;
		int var19;
		for(var9 = -4; var9 <= 4; ++var9) {
			var10 = (int)(8.5D + var23 * (double)var9 * 0.3D);
			var11 = (int)(7.5D - var22 * (double)var9 * 0.3D * 0.5D);
			var12 = var11 * 16 + var10;
			var13 = 100;
			var14 = 100;
			var15 = 100;
			var16 = 255;
			if(this.anaglyphEnabled) {
				var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
				var18 = (var13 * 30 + var14 * 70) / 100;
				var19 = (var13 * 30 + var15 * 70) / 100;
				var13 = var17;
				var14 = var18;
				var15 = var19;
			}

			this.imageData[var12 * 4 + 0] = (byte)var13;
			this.imageData[var12 * 4 + 1] = (byte)var14;
			this.imageData[var12 * 4 + 2] = (byte)var15;
			this.imageData[var12 * 4 + 3] = (byte)var16;
		}

		for(var9 = -8; var9 <= 16; ++var9) {
			var10 = (int)(8.5D + var22 * (double)var9 * 0.3D);
			var11 = (int)(7.5D + var23 * (double)var9 * 0.3D * 0.5D);
			var12 = var11 * 16 + var10;
			var13 = var9 >= 0 ? 255 : 100;
			var14 = var9 >= 0 ? 20 : 100;
			var15 = var9 >= 0 ? 20 : 100;
			var16 = 255;
			if(this.anaglyphEnabled) {
				var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
				var18 = (var13 * 30 + var14 * 70) / 100;
				var19 = (var13 * 30 + var15 * 70) / 100;
				var13 = var17;
				var14 = var18;
				var15 = var19;
			}

			this.imageData[var12 * 4 + 0] = (byte)var13;
			this.imageData[var12 * 4 + 1] = (byte)var14;
			this.imageData[var12 * 4 + 2] = (byte)var15;
			this.imageData[var12 * 4 + 3] = (byte)var16;
		}

	}
}
