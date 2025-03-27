package net.minecraft.src;

public class RenderPig extends RenderLiving {
	public RenderPig(ModelBase var1, ModelBase var2, float var3) {
		super(var1, var3);
		this.setRenderPassModel(var2);
	}

	protected boolean renderSaddledPig(EntityPig var1, int var2) {
		this.loadTexture("/mob/saddle.png");
		return var2 == 0 && var1.saddled;
	}

	protected boolean shouldRenderPass(EntityLiving var1, int var2) {
		return this.renderSaddledPig((EntityPig)var1, var2);
	}
}
