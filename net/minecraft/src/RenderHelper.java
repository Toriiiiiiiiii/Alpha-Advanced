package net.minecraft.src;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);

	public static void disableStandardItemLighting() {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_LIGHT0);
		GL11.glDisable(GL11.GL_LIGHT1);
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
	}

	public static void enableStandardItemLighting() {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_LIGHT1);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		float var0 = 0.4F;
		float var1 = 0.6F;
		float var2 = 0.0F;
		Vec3D var3 = Vec3D.createVector((double)0.2F, 1.0D, (double)-0.7F).normalize();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(var3.xCoord, var3.yCoord, var3.zCoord, 0.0D));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(var1, var1, var1, 1.0F));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(var2, var2, var2, 1.0F));
		var3 = Vec3D.createVector((double)-0.2F, 1.0D, (double)0.7F).normalize();
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, setColorBuffer(var3.xCoord, var3.yCoord, var3.zCoord, 0.0D));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, setColorBuffer(var1, var1, var1, 1.0F));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(var2, var2, var2, 1.0F));
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(var0, var0, var0, 1.0F));
	}

	private static FloatBuffer setColorBuffer(double var0, double var2, double var4, double var6) {
		return setColorBuffer((float)var0, (float)var2, (float)var4, (float)var6);
	}

	private static FloatBuffer setColorBuffer(float var0, float var1, float var2, float var3) {
		colorBuffer.clear();
		colorBuffer.put(var0).put(var1).put(var2).put(var3);
		colorBuffer.flip();
		return colorBuffer;
	}
}
