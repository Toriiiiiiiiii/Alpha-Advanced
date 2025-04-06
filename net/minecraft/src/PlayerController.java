package net.minecraft.src;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class PlayerController {
	protected final Minecraft mc;
	public boolean isInTestMode = false;

	public PlayerController(Minecraft var1) {
		this.mc = var1;
	}

	public void onWorldChange(World var1) {
	}

	public void clickBlock(int var1, int var2, int var3, int var4) {
		this.sendBlockRemoved(var1, var2, var3, var4);
	}

	public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
		this.mc.effectRenderer.addBlockDestroyEffects(var1, var2, var3);
		World var5 = this.mc.theWorld;
		Block var6 = Block.blocksList[var5.getBlockId(var1, var2, var3)];
		int var7 = var5.getBlockMetadata(var1, var2, var3);
		boolean var8 = var5.setBlockWithNotify(var1, var2, var3, 0);
		if(var6 != null && var8) {
			this.mc.sndManager.playSound(var6.stepSound.getBreakSound(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 2.0F, var6.stepSound.getPitch() * 0.8F);
			var6.onBlockDestroyedByPlayer(var5, var1, var2, var3, var7);
		}

		return var8;
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
	}

	public void resetBlockRemoving() {
	}

	public void setPartialTime(float var1) {
	}

	public float getBlockReachDistance() {
		return 5.0F;
	}

	public void flipPlayer(EntityPlayer var1) {
	}

	public void onUpdate() {
	}

	public boolean shouldDrawHUD() {
		return true;
	}

	public void onRespawn(EntityPlayer var1) {
	}

	public boolean onPlayerRightClick(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
		int var8 = var2.getBlockId(var4, var5, var6);
		if(Keyboard.isKeyDown(this.mc.options.keyBindSneak.keyCode)) return var3 == null ? false : var3.useItem(var1, var2, var4, var5, var6, var7);
		return var8 > 0 && Block.blocksList[var8].blockActivated(var2, var4, var5, var6, var1) ? true : (var3 == null ? false : var3.useItem(var1, var2, var4, var5, var6, var7));
	}

	public EntityPlayer createPlayer(World var1) {
		return new EntityPlayerSP(this.mc, var1, this.mc.session);
	}
}
