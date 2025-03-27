package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class BlockLeaves extends BlockLeavesBase {
	private int leafTexIndex;
	private int decayCounter = 0;
	private boolean brokenWithShears = false;
	private boolean playerPlaced = false;

	protected BlockLeaves(int id, int blockIndex, boolean player) {
		super(id, blockIndex, Material.leaves, false);
		this.leafTexIndex = blockIndex;
		this.setTickOnLoad(true);
		this.playerPlaced = player;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int flag) {
		this.decayCounter = 0;
		this.updateCurrentLeaves(world, x, y, z);
		super.onNeighborBlockChange(world, x, y, z, flag);
	}

	public void updateConnectedLeaves(World world, int x, int y, int z, int i5) {
		if(world.getBlockId(x, y, z) == this.blockID) {
			int i6 = world.getBlockMetadata(x, y, z);
			if(i6 != 0 && i6 == i5 - 1) {
				this.updateCurrentLeaves(world, x, y, z);
			}
		}
	}

	public void updateCurrentLeaves(World world, int x, int y, int z) {
		if(this.playerPlaced) return;
		
		if(this.decayCounter++ < 100) {
			if(world.getBlockMetadata(x, y, z) != 1) {
				int[] i5 = new int[]{x, y, z};
				LinkedList linkedList6 = new LinkedList();
				linkedList6.add(i5);
				if(!this.leafToWoodConnection(world, 4, new HashSet(), linkedList6)) {
					world.setBlockMetadata(x, y, z, 1);
				}

			}
		}
	}
	
	private boolean leafToWoodConnection(World world, int i2, HashSet hashSet, LinkedList linkedList) {
		if(i2 >= 1 && linkedList != null && linkedList.size() != 0) {
			LinkedList linkedList5 = new LinkedList();
			Iterator iterator6 = linkedList.iterator();

			while(iterator6.hasNext()) {
				int[] i7 = (int[])iterator6.next();
				hashSet.add(i7);
				LinkedList linkedList8 = new LinkedList();
				int[][] i9 = new int[][]{{i7[0] - 1, i7[1], i7[2]}, {i7[0] + 1, i7[1], i7[2]}, {i7[0], i7[1] - 1, i7[2]}, {i7[0], i7[1] + 1, i7[2]}, {i7[0], i7[1], i7[2] - 1}, {i7[0], i7[1], i7[2] + 1}};
				int[][] i10 = i9;
				int i11 = i9.length;

				int i12;
				for(i12 = 0; i12 < i11; ++i12) {
					int[] i13 = i10[i12];
					linkedList8.add(i13);
				}

				Iterator iterator14 = linkedList8.iterator();

				while(iterator14.hasNext()) {
					int[] i15 = (int[])iterator14.next();
					if(!hashSet.contains(i15)) {
						hashSet.add(i15);
						i12 = world.getBlockId(i15[0], i15[1], i15[2]);
						if(i12 == 17) {
							return true;
						}

						if(i12 == 18) {
							linkedList5.add(i15);
						} else if(world.getBlockMaterial(i15[0], i15[1], i15[2]).isSolid()) {
							return true;
						}
					}
				}
			}

			return this.leafToWoodConnection(world, i2 - 1, hashSet, linkedList5);
		} else {
			return false;
		}
	}

	private int getConnectionStrength(World world, int x, int y, int z, int i5) {
		int i6 = world.getBlockId(x, y, z);
		if(i6 == Block.wood.blockID) {
			return 16;
		} else {
			if(i6 == this.blockID) {
				int i7 = world.getBlockMetadata(x, y, z);
				if(i7 != 0 && i7 > i5) {
					return i7;
				}
			}

			return i5;
		}
	}
	
	public void updateTick(World world, int x, int y, int z, Random random) {
		int i6 = world.getBlockMetadata(x, y, z);
		if(i6 == 0) {
			this.decayCounter = 0;
			this.updateCurrentLeaves(world, x, y, z);
		} else if(i6 == 1) {
			this.removeLeaves(world, x, y, z);
		} else if(random.nextInt(10) == 0) {
			this.updateCurrentLeaves(world, x, y, z);
		}

	}

	private void removeLeaves(World world, int x, int y, int z) {
		this.brokenWithShears = false;
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
		world.setBlockWithNotify(x, y, z, 0);
	}

	public int quantityDropped(Random random) {
		if(!this.brokenWithShears)
			return random.nextInt(20) == 0 ? 1 : 0;
		
		return 1;
	}

	public int idDropped(int count, Random random) {
		if(!this.brokenWithShears) {
			return random.nextInt(5) == 1 ? Item.appleRed.shiftedIndex : Block.sapling.blockID;
		}
		
		this.brokenWithShears = false;
		return Block.leavesPlr.blockID;
	}

	public boolean isOpaqueCube() {
		return !this.graphicsLevel;
	}

	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		super.onEntityWalking(world, x, y, z, entity);
	}

	public void setGraphicsLevel(boolean var1) {
		this.graphicsLevel = var1;
		
		this.blockIndexInTexture = this.leafTexIndex + (var1 ? 0 : 1);
	}
	
	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
		EntityPlayer plr = (EntityPlayer)var1.playerEntities.get(0);
		ItemStack current = plr.inventory.getCurrentItem();
		
		if(current != null && current.itemID == Item.shears.shiftedIndex) {
			this.brokenWithShears = true;
			plr.inventory.getCurrentItem().damageItem(1);
		}
	}
}
