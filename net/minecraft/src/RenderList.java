package net.minecraft.src;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;

public class RenderList {
	private int posX;
	private int posY;
	private int posZ;
	private float playerPosX;
	private float playerPosY;
	private float playerPosZ;
	private IntBuffer buffer = GLAllocation.createDirectIntBuffer(65536);
	private boolean render = false;
	private boolean isCached = false;

	public void setLocation(int var1, int var2, int var3, double var4, double var6, double var8) {
		this.render = true;
		this.buffer.clear();
		this.posX = var1;
		this.posY = var2;
		this.posZ = var3;
		this.playerPosX = (float)var4;
		this.playerPosY = (float)var6;
		this.playerPosZ = (float)var8;
	}

	public boolean isRenderedAt(int var1, int var2, int var3) {
		return !this.render ? false : var1 == this.posX && var2 == this.posY && var3 == this.posZ;
	}

	public void render(int var1) {
		this.buffer.put(var1);
		if(this.buffer.remaining() == 0) {
			this.render();
		}

	}

	public void render() {
		if(this.render) {
			if(!this.isCached) {
				this.buffer.flip();
				this.isCached = true;
			}

			if(this.buffer.remaining() > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)this.posX - this.playerPosX, (float)this.posY - this.playerPosY, (float)this.posZ - this.playerPosZ);
				GL11.glCallLists(this.buffer);
				GL11.glPopMatrix();
			}

		}
	}

	public void reset() {
		this.render = false;
		this.isCached = false;
	}
}
