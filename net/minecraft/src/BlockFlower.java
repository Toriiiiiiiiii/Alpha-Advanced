package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block {
	public boolean canReproduce;
	
	protected BlockFlower(int var1, int var2) {
		super(var1, Material.plants);
		this.blockIndexInTexture = var2;
		
		this.setTickOnLoad(true);
		float var3 = 0.2F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int var1) {
		return var1 == Block.grass.blockID || var1 == Block.dirt.blockID || var1 == Block.tilledField.blockID;
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		this.checkFlowerChange(var1, var2, var3, var4);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		this.checkFlowerChange(var1, var2, var3, var4);
	}
	
	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {		
		if(var1.multiplayerWorld) return true;
		
		if(this.blockID != Block.plantBlack.blockID && this.blockID != Block.plantBlue.blockID && this.blockID != Block.plantPink.blockID && this.blockID != Block.plantRed.blockID && this.blockID != Block.plantYellow.blockID && this.blockID != Block.plantTea.blockID)
			return true;
		
		ItemStack currentItem = var5.inventory.getCurrentItem();
		if(currentItem != null && currentItem.itemID == Item.fertilizer.shiftedIndex) {
			if(this.reproduce(var1, var2, var3, var4)) var5.inventory.getCurrentItem().stackSize--;
		}
		
		return true;
	}
	
	public boolean reproduce(World var1, int var2, int var3, int var4) {
		Random rand = new Random();
		boolean done = false;
		int maxTries = 100;
		int tries = 0;
		while(!done && tries < maxTries) {
			int x = var2 + rand.nextInt(3) - 1;
			int z = var4 + rand.nextInt(3) - 1;
			
			if(var1.getBlockId(x,  var3,  z) == 0 && var1.getBlockId(x,  var3-1, z) == Block.grass.blockID) {
				var1.setBlockWithNotify(x, var3, z, this.blockID);
				done = true;
			} else if(var1.getBlockId(x,  var3-1,  z) == 0 && var1.getBlockId(x,  var3-2, z) == Block.grass.blockID) {
				var1.setBlockWithNotify(x, var3-1, z, this.blockID);
				done = true;
			} else if(var1.getBlockId(x,  var3+1,  z) == 0 && var1.getBlockId(x,  var3, z) == Block.grass.blockID) {
				var1.setBlockWithNotify(x, var3+1, z, this.blockID);
				done = true;
			}
			
			if(done) return true;
			tries++;
		}
		
		return false;
	}
	
	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
	}

	protected final void checkFlowerChange(World var1, int var2, int var3, int var4) {
		if(!this.canBlockStay(var1, var2, var3, var4)) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, 0);
		}

	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
//		return (var1.getBlockLightValue(var2, var3, var4) >= 8 || var1.canBlockSeeTheSky(var2, var3, var4)) && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
		return this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 1;
	}
}
