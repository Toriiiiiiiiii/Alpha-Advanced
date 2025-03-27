package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderCreeper extends RenderLiving {
	public RenderCreeper() {
		super(new ModelCreeper(), 0.5F);
	}

	protected void updateCreeperScale(EntityCreeper var1, float var2) {
		float var4 = var1.getCreeperFlashTime(var2);
		float var5 = 1.0F + MathHelper.sin(var4 * 100.0F) * var4 * 0.01F;
		if(var4 < 0.0F) {
			var4 = 0.0F;
		}

		if(var4 > 1.0F) {
			var4 = 1.0F;
		}

		var4 *= var4;
		var4 *= var4;
		float var6 = (1.0F + var4 * 0.4F) * var5;
		float var7 = (1.0F + var4 * 0.1F) / var5;
		GL11.glScalef(var6, var7, var6);
	}

	protected int updateCreeperColorMultiplier(EntityCreeper var1, float var2, float var3) {
		float var5 = var1.getCreeperFlashTime(var3);
		if((int)(var5 * 10.0F) % 2 == 0) {
			return 0;
		} else {
			int var6 = (int)(var5 * 0.2F * 255.0F);
			if(var6 < 0) {
				var6 = 0;
			}

			if(var6 > 255) {
				var6 = 255;
			}

			short var7 = 255;
			short var8 = 255;
			short var9 = 255;
			return var6 << 24 | var7 << 16 | var8 << 8 | var9;
		}
	}

	protected void preRenderCallback(EntityLiving var1, float var2) {
		this.updateCreeperScale((EntityCreeper)var1, var2);
	}

	protected int getColorMultiplier(EntityLiving var1, float var2, float var3) {
		return this.updateCreeperColorMultiplier((EntityCreeper)var1, var2, var3);
	}
}
