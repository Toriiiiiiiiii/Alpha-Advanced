package net.minecraft.src;

public class RecipesCrafting {
	public void addRecipes(CraftingManager var1) {
		var1.addRecipe(new ItemStack(Block.chest), new Object[]{"###", "# #", "###", Character.valueOf('#'), Block.planks});
		var1.addRecipe(new ItemStack(Block.stoneOvenIdle), new Object[]{"###", "# #", "###", Character.valueOf('#'), Block.cobblestone});
		var1.addRecipe(new ItemStack(Block.workbench), new Object[]{"##", "##", Character.valueOf('#'), Block.planks});
		var1.addRecipe(new ItemStack(Item.shears), new Object[]{"# ", " #", Character.valueOf('#'), Item.ingotIron});
		var1.addRecipe(new ItemStack(Block.blockLamp, 4), new Object[] {" X ", "X#X", " X ", Character.valueOf('#'), Block.blockCopper, Character.valueOf('X'), Block.glass});
//		var1.addRecipe(new ItemStack(Block.clothYellow), new Object[] {"#X", Character.valueOf('#'), Block.plantYellow, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothRed), new Object[] {"#X", Character.valueOf('#'), Block.plantRed, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothRose), new Object[] {"#X", Character.valueOf('#'), Block.plantPink, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothDarkGray), new Object[] {"#X", Character.valueOf('#'), Block.plantBlack, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothCyan), new Object[] {"#X", Character.valueOf('#'), Block.plantBlue, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothPurple), new Object[] {"#X", Character.valueOf('#'), Block.plantPurple, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothOrange), new Object[] {"#X", Character.valueOf('#'), Item.teaLeaves, Character.valueOf('X'), Block.cloth});
//		var1.addRecipe(new ItemStack(Block.clothGreen), new Object[] {"#X", Character.valueOf('#'), Block.cactus, Character.valueOf('X'), Block.cloth});
		var1.addRecipe(new ItemStack(Block.minecartTrackPowered, 4), new Object[] {"# #", "#X#", "# #", Character.valueOf('#'), Item.ingotCopper, Character.valueOf('X'), Item.stick});
		var1.addRecipe(new ItemStack(Block.brickStone, 4), new Object[] {"##","##", Character.valueOf('#'), Block.cobblestone});
		var1.addRecipe(new ItemStack(Block.brickSmooth, 4), new Object[] {"##","##", Character.valueOf('#'), Block.stone});
		//var1.addRecipe(new ItemStack(Block.minecartTrackPowered, 16), new Object[] {"#", Character.valueOf('#'), Block.dirt});
		var1.addRecipe(new ItemStack(Item.teaLeaves, 4), new Object[] {"#", Character.valueOf('#'), Block.plantTea});
		var1.addRecipe(new ItemStack(Item.mug), new Object[]{"# #", " # ", Character.valueOf('#'), Item.brick});
		var1.addRecipe(new ItemStack(Item.tea), new Object[] {"#X", Character.valueOf('#'), Item.mug, Character.valueOf('X'), Item.teaLeaves});

		var1.addRecipe(new ItemStack(Block.blockFlint), new Object[]{"##", "##", Character.valueOf('#'), Item.flint});
		var1.addRecipe(new ItemStack(Item.flint, 4), new Object[]{"#", Character.valueOf('#'), Block.blockFlint});
		
		Block wools[] = {Block.clothWhite, Block.clothYellow, Block.clothRed, Block.clothRose, Block.clothDarkGray, Block.clothCyan, Block.clothPurple, Block.clothOrange, Block.clothGreen};
		Object dyes[] = {null, Block.plantYellow, Block.plantRed, Block.plantPink, Block.plantBlack, Block.plantBlue, Block.plantPurple, Item.teaLeaves, Block.cactus};
		
		for(int i = 0; i < wools.length; ++i) {
			if(dyes[i] == null) continue;
			for(int j = 0; j < wools.length; ++i) {
				var1.addRecipe(new ItemStack(wools[i]), new Object[]{"#X", Character.valueOf('#'), dyes[i], Character.valueOf('X'), wools[j]});
			}
		}
	}
}
