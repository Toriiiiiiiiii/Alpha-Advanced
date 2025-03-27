package net.minecraft.src;

public class ItemTool extends Item {
	private Block[] blocksEffectiveAgainst;
	private float efficiencyOnProperMaterial = 4.0F;
	private int damageVsEntity;
	protected int toolMaterial;

	public ItemTool(int var1, int var2, int var3, Block[] var4) {
		super(var1);
		this.toolMaterial = var3;
		this.blocksEffectiveAgainst = var4;
		this.maxStackSize = 1;
		this.maxDamage = 32 << var3;
		if(var3 == 3) {
			this.maxDamage *= 4;
		}

		this.efficiencyOnProperMaterial = (float)((var3+1) * 2);
		this.damageVsEntity = var2 + var3;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		for(int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3) {
			if(this.blocksEffectiveAgainst[var3] == var2) {
				return this.efficiencyOnProperMaterial;
			}
		}

		return 1.0F;
	}

	public void hitEntity(ItemStack var1, EntityLiving var2) {
		var1.damageItem(2);
	}

	public void onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5) {
		var1.damageItem(1);
	}

	public int getDamageVsEntity(Entity var1) {
		return this.damageVsEntity;
	}

	public boolean isFull3D() {
		return true;
	}
}
