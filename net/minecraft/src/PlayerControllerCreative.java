package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerCreative extends PlayerController {
	public PlayerControllerCreative(Minecraft var1) {
		super(var1);
		this.isInTestMode = true;
	}

	public void onRespawn(EntityPlayer var1) {
		for(int var2 = 0; var2 < 9; ++var2) {
			if(var1.inventory.mainInventory[var2] == null) {
				this.mc.thePlayer.inventory.mainInventory[var2] = new ItemStack(((Block)Session.registeredBlocksList.get(var2)).blockID);
			} else {
				this.mc.thePlayer.inventory.mainInventory[var2].stackSize = 1;
			}
		}

	}

	public boolean shouldDrawHUD() {
		return false;
	}

	public void onWorldChange(World var1) {
		super.onWorldChange(var1);
	}

	public void onUpdate() {
	}
}
