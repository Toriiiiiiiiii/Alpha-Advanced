package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class RenderManager {
	private Map entityRenderMap = new HashMap();
	public static RenderManager instance = new RenderManager();
	private FontRenderer fontRenderer;
	public static double renderPosX;
	public static double renderPosY;
	public static double renderPosZ;
	public RenderEngine renderEngine;
	public ItemRenderer itemRenderer;
	public World worldObj;
	public EntityPlayer player;
	public float playerViewY;
	public float playerViewX;
	public GameSettings options;
	public double viewerPosX;
	public double viewerPosY;
	public double viewerPosZ;

	private RenderManager() {
		this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
		this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
		this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep(), new ModelSheepFur(), 0.7F));
		this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
		this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
		this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
		this.entityRenderMap.put(EntitySkeleton.class, new RenderLiving(new ModelSkeleton(), 0.5F));
		this.entityRenderMap.put(EntityZombie.class, new RenderLiving(new ModelZombie(), 0.5F));
		this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
		this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
		this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5F));
		this.entityRenderMap.put(Entity.class, new RenderEntity());
		this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
		this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
		this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball());
		this.entityRenderMap.put(EntityItem.class, new RenderItem());
		this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
		this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
		this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
		this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
		Iterator var1 = this.entityRenderMap.values().iterator();

		while(var1.hasNext()) {
			Render var2 = (Render)var1.next();
			var2.setRenderManager(this);
		}

	}

	public Render getEntityClassRenderObject(Class var1) {
		Render var2 = (Render)this.entityRenderMap.get(var1);
		if(var2 == null && var1 != Entity.class) {
			var2 = this.getEntityClassRenderObject(var1.getSuperclass());
			this.entityRenderMap.put(var1, var2);
		}

		return var2;
	}

	public Render getEntityRenderObject(Entity var1) {
		return this.getEntityClassRenderObject(var1.getClass());
	}

	public void cacheActiveRenderInfo(World var1, RenderEngine var2, FontRenderer var3, EntityPlayer var4, GameSettings var5, float var6) {
		this.worldObj = var1;
		this.renderEngine = var2;
		this.options = var5;
		this.player = var4;
		this.fontRenderer = var3;
		this.playerViewY = var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var6;
		this.playerViewX = var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var6;
		this.viewerPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var6;
		this.viewerPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var6;
		this.viewerPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var6;
	}

	public void renderEntity(Entity var1, float var2) {
		double var3 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
		double var5 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
		double var7 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
		float var9 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
		float var10 = var1.getBrightness(var2);
		GL11.glColor3f(var10, var10, var10);
		this.renderEntityWithPosYaw(var1, var3 - renderPosX, var5 - renderPosY, var7 - renderPosZ, var9, var2);
	}

	public void renderEntityWithPosYaw(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		Render var10 = this.getEntityRenderObject(var1);
		if(var10 != null) {
			var10.doRender(var1, var2, var4, var6, var8, var9);
			var10.doRenderShadowAndFire(var1, var2, var4, var6, var8, var9);
		}

	}

	public void set(World var1) {
		this.worldObj = var1;
	}

	public double getDistanceToCamera(double var1, double var3, double var5) {
		double var7 = var1 - this.viewerPosX;
		double var9 = var3 - this.viewerPosY;
		double var11 = var5 - this.viewerPosZ;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
}
