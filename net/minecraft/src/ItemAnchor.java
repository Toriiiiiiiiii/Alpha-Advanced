package net.minecraft.src;

public class ItemAnchor extends Item {
	private Material material;

	public ItemAnchor(int var1) {
		super(var1);
		this.maxDamage = 1;
		this.maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int var7) {
		if(var7 != 1) {
			return false;
		} else {
			if(world.multiplayerWorld) return false;
			if(world.getBlockMaterial(x, y+1, z) != Material.air) return false;
			
			world.spawnX = x;
			world.spawnY = y+1;
			world.spawnZ = z;
			
			item.stackSize--;
			((EntityPlayerSP)player).mc.ingameGUI.addChatMessage("Set spawn point to " + world.spawnX + ", " + world.spawnY + ", " + world.spawnZ);
			
			return true;
		}
	}
}
