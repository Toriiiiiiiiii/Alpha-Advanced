package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {
	private Block modelBlock;

	protected BlockStairs(int var1, Block var2) {
		super(var1, var2.blockIndexInTexture, var2.material);
		this.modelBlock = var2;
		this.setHardness(var2.hardness);
		this.setResistance(var2.resistance / 3.0F);
		this.setStepSound(var2.stepSound);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 10;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return super.shouldSideBeRendered(var1, var2, var3, var4, var5);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		int var7 = var1.getBlockMetadata(var2, var3, var4);
		if(var7 == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 1) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!var1.multiplayerWorld) {
			this.modelBlock.onNeighborBlockChange(var1, var2, var3, var4, var5);
		}
	}

	public void onBlockPlaced(World world, int x, int y, int z, int var5) {
		if(!world.multiplayerWorld) {
			EntityPlayer plr = (EntityPlayer)world.playerEntities.get(0);
			double playerAngle = (int)plr.rotationYaw % 360;
			System.out.println(playerAngle);
			
			byte direction = 0;
			if(playerAngle > 0) {
				if(playerAngle > 45 && playerAngle <= 135) {
					direction = 1;
				} if(playerAngle > 135 && playerAngle <= 225) {
					direction = 3;
				} if(playerAngle > 225 && playerAngle <= 315) {
					direction = 0;
				} if(playerAngle > 315 || playerAngle < 45) {
					direction = 2;
				}
			} else {
				playerAngle = Math.abs(playerAngle);
				if(playerAngle > 45 && playerAngle <= 135) {
					direction = 0;
				} if(playerAngle > 135 && playerAngle <= 225) {
					direction = 3;
				} if(playerAngle > 225 && playerAngle <= 315) {
					direction = 1;
				} if(playerAngle > 315 || playerAngle < 45) {
					direction = 2;
				}
			}
			
			world.setBlockMetadataWithNotify(x, y, z, direction);
		}
	}

	private boolean isBlockSolid(World var1, int var2, int var3, int var4) {
		return var1.getBlockMaterial(var2, var3, var4).isSolid();
	}

	private boolean isBlockStair(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3, var4);
		return var5 == 0 ? false : Block.blocksList[var5].getRenderType() == 10;
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		this.modelBlock.randomDisplayTick(var1, var2, var3, var4, var5);
	}

	public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		this.modelBlock.onBlockClicked(var1, var2, var3, var4, var5);
	}

	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
		this.modelBlock.onBlockDestroyedByPlayer(var1, var2, var3, var4, var5);
	}

	public float getBlockBrightness(IBlockAccess var1, int var2, int var3, int var4) {
		return this.modelBlock.getBlockBrightness(var1, var2, var3, var4);
	}

	public float getExplosionResistance(Entity var1) {
		return this.modelBlock.getExplosionResistance(var1);
	}

	public int getRenderBlockPass() {
		return this.modelBlock.getRenderBlockPass();
	}

	public int idDropped(int var1, Random var2) {
		return this.blockID;
	}

	public int quantityDropped(Random var1) {
		return this.modelBlock.quantityDropped(var1);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.modelBlock.getBlockTextureFromSideAndMetadata(var1, var2);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.modelBlock.getBlockTextureFromSide(var1);
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return this.modelBlock.getBlockTexture(var1, var2, var3, var4, var5);
	}

	public int tickRate() {
		return this.modelBlock.tickRate();
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return this.modelBlock.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
		this.modelBlock.velocityToAddToEntity(var1, var2, var3, var4, var5, var6);
	}

	public boolean isCollidable() {
		return this.modelBlock.isCollidable();
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return this.modelBlock.canCollideCheck(var1, var2);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return this.modelBlock.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		this.onNeighborBlockChange(var1, var2, var3, var4, 0);
		this.modelBlock.onBlockAdded(var1, var2, var3, var4);
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		this.modelBlock.onBlockRemoval(var1, var2, var3, var4);
	}
//
//	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
//		this.modelBlock.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6);
//	}
//
//	public void dropBlockAsItem(World var1, int var2, int var3, int var4, int var5) {
//		this.modelBlock.dropBlockAsItem(var1, var2, var3, var4, var5);
//	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
		this.modelBlock.onEntityWalking(var1, var2, var3, var4, var5);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		this.modelBlock.updateTick(var1, var2, var3, var4, var5);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		return this.modelBlock.blockActivated(var1, var2, var3, var4, var5);
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
		this.modelBlock.onBlockDestroyedByExplosion(var1, var2, var3, var4);
	}
}
