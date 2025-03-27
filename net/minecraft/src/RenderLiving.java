package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderLiving extends Render {
	protected ModelBase mainModel;
	protected ModelBase renderPassModel;

	public RenderLiving(ModelBase var1, float var2) {
		this.mainModel = var1;
		this.shadowSize = var2;
	}

	public void setRenderPassModel(ModelBase var1) {
		this.renderPassModel = var1;
	}

	public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.mainModel.swingProgress = this.renderSwingProgress(var1, var9);
		this.mainModel.isRiding = var1.ridingEntity != null;
		if(this.renderPassModel != null) {
			this.renderPassModel.isRiding = this.mainModel.isRiding;
		}

		try {
			float var10 = var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var9;
			float var11 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9;
			float var12 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
			GL11.glTranslatef((float)var2, (float)var4, (float)var6);
			float var13 = this.handleRotationFloat(var1, var9);
			GL11.glRotatef(180.0F - var10, 0.0F, 1.0F, 0.0F);
			float var14;
			if(var1.deathTime > 0) {
				var14 = ((float)var1.deathTime + var9 - 1.0F) / 20.0F * 1.6F;
				var14 = MathHelper.sqrt_float(var14);
				if(var14 > 1.0F) {
					var14 = 1.0F;
				}

				GL11.glRotatef(var14 * this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
			}

			var14 = 1.0F / 16.0F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.preRenderCallback(var1, var9);
			GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
			float var15 = var1.prevLimbYaw + (var1.limbYaw - var1.prevLimbYaw) * var9;
			float var16 = var1.limbSwing - var1.limbYaw * (1.0F - var9);
			if(var15 > 1.0F) {
				var15 = 1.0F;
			}

			this.loadDownloadableImageTexture(var1.skinUrl, var1.getTexture());
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);

			for(int var17 = 0; var17 < 4; ++var17) {
				if(this.shouldRenderPass(var1, var17)) {
					this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
			}

			this.renderEquippedItems(var1, var9);
			float var25 = var1.getBrightness(var9);
			int var18 = this.getColorMultiplier(var1, var25, var9);
			if((var18 >> 24 & 255) > 0 || var1.hurtTime > 0 || var1.deathTime > 0) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDepthFunc(GL11.GL_EQUAL);
				if(var1.hurtTime > 0 || var1.deathTime > 0) {
					GL11.glColor4f(var25, 0.0F, 0.0F, 0.4F);
					this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);

					for(int var19 = 0; var19 < 4; ++var19) {
						if(this.shouldRenderPass(var1, var19)) {
							GL11.glColor4f(var25, 0.0F, 0.0F, 0.4F);
							this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
						}
					}
				}

				if((var18 >> 24 & 255) > 0) {
					float var26 = (float)(var18 >> 16 & 255) / 255.0F;
					float var20 = (float)(var18 >> 8 & 255) / 255.0F;
					float var21 = (float)(var18 & 255) / 255.0F;
					float var22 = (float)(var18 >> 24 & 255) / 255.0F;
					GL11.glColor4f(var26, var20, var21, var22);
					this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);

					for(int var23 = 0; var23 < 4; ++var23) {
						if(this.shouldRenderPass(var1, var23)) {
							GL11.glColor4f(var26, var20, var21, var22);
							this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
						}
					}
				}

				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		} catch (Exception var24) {
			var24.printStackTrace();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	protected float renderSwingProgress(EntityLiving var1, float var2) {
		return var1.getSwingProgress(var2);
	}

	protected float handleRotationFloat(EntityLiving var1, float var2) {
		return (float)var1.ticksExisted + var2;
	}

	protected void renderEquippedItems(EntityLiving var1, float var2) {
	}

	protected boolean shouldRenderPass(EntityLiving var1, int var2) {
		return false;
	}

	protected float getDeathMaxRotation(EntityLiving var1) {
		return 90.0F;
	}

	protected int getColorMultiplier(EntityLiving var1, float var2, float var3) {
		return 0;
	}

	protected void preRenderCallback(EntityLiving var1, float var2) {
	}

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.doRenderLiving((EntityLiving)var1, var2, var4, var6, var8, var9);
	}
}
