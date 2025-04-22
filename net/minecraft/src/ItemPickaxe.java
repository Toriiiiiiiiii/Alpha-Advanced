package net.minecraft.src;

public class ItemPickaxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.stoneOvenIdle, Block.stoneOvenActive, Block.oreCopper, Block.brick, Block.brickStone, Block.stairCompactStone, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.stairCompactStoneBrick, Block.stairCompactBrick, Block.pressurePlateStone, Block.brickSmooth, Block.stairCompactSmoothBrick, Block.blockCopper, Block.blockFlint, Block.marble, Block.cobbleMarble, Block.marblePillar};
	private int harvestLevel;

	public ItemPickaxe(int var1, int var2) {
		super(var1, 2, var2, blocksEffectiveAgainst);
		this.harvestLevel = var2;
	}

	public boolean canHarvestBlock(Block var1) {
		return var1 == Block.obsidian ? this.harvestLevel == 3 : (var1 != Block.blockDiamond && var1 != Block.oreDiamond ? (var1 != Block.blockGold && var1 != Block.oreGold ? (var1 != Block.blockSteel && var1 != Block.oreIron ? (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? (var1 != Block.oreCopper ? (var1.material == Material.rock ? true : var1.material == Material.iron) : this.harvestLevel >= 1) : this.harvestLevel >= 1) : this.harvestLevel >= 1) : this.harvestLevel >= 2) : this.harvestLevel >= 2);
	}
}
