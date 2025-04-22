package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CraftingManager {
	private static final CraftingManager instance = new CraftingManager();
	private List recipes = new ArrayList();

	public static final CraftingManager getInstance() {
		return instance;
	}

	private CraftingManager() {
		(new RecipesTools()).addRecipes(this);
		(new RecipesWeapons()).addRecipes(this);
		(new RecipesIngots()).addRecipes(this);
		(new RecipesFood()).addRecipes(this);
		(new RecipesCrafting()).addRecipes(this);
		(new RecipesArmor()).addRecipes(this);
		this.addRecipe(new ItemStack(Item.paper, 3), new Object[]{"###", Character.valueOf('#'), Item.reed});
		this.addRecipe(new ItemStack(Item.book, 1), new Object[]{"#", "#", "#", Character.valueOf('#'), Item.paper});
		this.addRecipe(new ItemStack(Block.fence, 2), new Object[]{"###", "###", Character.valueOf('#'), Item.stick});
		this.addRecipe(new ItemStack(Block.jukebox, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.diamond});
		this.addRecipe(new ItemStack(Block.bookshelf, 1), new Object[]{"###", "XXX", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.book});
		this.addRecipe(new ItemStack(Block.blockSnow, 1), new Object[]{"##", "##", Character.valueOf('#'), Item.snowball});
		this.addRecipe(new ItemStack(Block.blockClay, 1), new Object[]{"##", "##", Character.valueOf('#'), Item.clay});
		this.addRecipe(new ItemStack(Block.brick, 1), new Object[]{"##", "##", Character.valueOf('#'), Item.brick});
		this.addRecipe(new ItemStack(Block.cloth, 1), new Object[]{"###", "###", "###", Character.valueOf('#'), Item.silk});
		this.addRecipe(new ItemStack(Block.tnt, 1), new Object[]{"X#X", "#X#", "X#X", Character.valueOf('X'), Item.gunpowder, Character.valueOf('#'), Block.sand});
		this.addRecipe(new ItemStack(Block.stairSingle, 3), new Object[]{"###", Character.valueOf('#'), Block.cobblestone});
		this.addRecipe(new ItemStack(Block.ladder, 1), new Object[]{"# #", "###", "# #", Character.valueOf('#'), Item.stick});
		this.addRecipe(new ItemStack(Item.doorWood, 1), new Object[]{"##", "##", "##", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Item.doorSteel, 1), new Object[]{"##", "##", "##", Character.valueOf('#'), Item.ingotIron});
		this.addRecipe(new ItemStack(Item.sign, 1), new Object[]{"###", "###", " X ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.stick});
		this.addRecipe(new ItemStack(Block.planks, 4), new Object[]{"#", Character.valueOf('#'), Block.wood});
		this.addRecipe(new ItemStack(Item.stick, 4), new Object[]{"#", "#", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Block.torch, 4), new Object[]{"X", "#", Character.valueOf('X'), Item.coal, Character.valueOf('#'), Item.stick});
		this.addRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[]{"# #", " # ", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Block.minecartTrack, 16), new Object[]{"X X", "X#X", "X X", Character.valueOf('X'), Item.ingotIron, Character.valueOf('#'), Item.stick});
		this.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[]{"# #", "###", Character.valueOf('#'), Item.ingotIron});
		this.addRecipe(new ItemStack(Item.minecartBox, 1), new Object[]{"A", "B", Character.valueOf('A'), Block.chest, Character.valueOf('B'), Item.minecartEmpty});
		this.addRecipe(new ItemStack(Item.minecartEngine, 1), new Object[]{"A", "B", Character.valueOf('A'), Block.stoneOvenIdle, Character.valueOf('B'), Item.minecartEmpty});
		this.addRecipe(new ItemStack(Item.boat, 1), new Object[]{"# #", "###", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[]{"# #", " # ", Character.valueOf('#'), Item.ingotIron});
		this.addRecipe(new ItemStack(Item.striker, 1), new Object[]{"A ", " B", Character.valueOf('A'), Item.ingotIron, Character.valueOf('B'), Item.flint});
		this.addRecipe(new ItemStack(Item.bread, 2), new Object[]{"###", Character.valueOf('#'), Item.wheat});
		this.addRecipe(new ItemStack(Block.stairCompactWood, 6), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Item.fishingRod, 1), new Object[]{"  #", " #X", "# X", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.silk});
		this.addRecipe(new ItemStack(Block.stairCompactStone, 6), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Block.cobblestone});
		this.addRecipe(new ItemStack(Item.painting, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Block.cloth});
		this.addRecipe(new ItemStack(Item.appleGold, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Block.blockGold, Character.valueOf('X'), Item.appleRed});
		this.addRecipe(new ItemStack(Block.lever, 1), new Object[]{"X", "#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick});
		this.addRecipe(new ItemStack(Block.torchRedstoneActive, 1), new Object[]{"X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.redstone});
		this.addRecipe(new ItemStack(Item.compass, 1), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.redstone});
		this.addRecipe(new ItemStack(Block.button, 1), new Object[]{"#", "#", Character.valueOf('#'), Block.stone});
		this.addRecipe(new ItemStack(Block.pressurePlateStone, 1), new Object[]{"###", Character.valueOf('#'), Block.stone});
		this.addRecipe(new ItemStack(Block.pressurePlateWood, 1), new Object[]{"###", Character.valueOf('#'), Block.planks});
		this.addRecipe(new ItemStack(Block.stairCompactStoneBrick, 6), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Block.brickStone});
		this.addRecipe(new ItemStack(Block.stairCompactBrick, 6), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Block.brick});
		this.addRecipe(new ItemStack(Block.stairCompactSmoothBrick, 6), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Block.brickSmooth});
		
		Block wools[] = {Block.cloth, Block.clothYellow, Block.clothRed, Block.clothRose, Block.clothDarkGray, Block.clothCyan, Block.clothPurple, Block.clothOrange, Block.clothGreen};
		Object dyes[] = {Item.clay, Block.plantYellow, Block.plantRed, Block.plantPink, Block.plantBlack, Block.plantBlue, Block.plantPurple, Item.teaLeaves, Block.cactus};
		
		for(int i = 0; i < wools.length; ++i) {
			if(dyes[i] == null) continue;
			for(int j = 0; j < wools.length; ++j) {
				if(wools[i] == wools[j]) continue;
				this.addRecipe(new ItemStack(wools[i]), new Object[]{"#X", Character.valueOf('#'), dyes[i], Character.valueOf('X'), wools[j]});
			}
		}
		
		Collections.sort(this.recipes, new RecipeSorter(this));
		System.out.println(this.recipes.size() + " recipes");
	}

	void addRecipe(ItemStack var1, Object... var2) {
		String var3 = "";
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;
		if(var2[var4] instanceof String[]) {
			String[] var11 = (String[])((String[])var2[var4++]);

			for(int var8 = 0; var8 < var11.length; ++var8) {
				String var9 = var11[var8];
				++var6;
				var5 = var9.length();
				var3 = var3 + var9;
			}
		} else {
			while(var2[var4] instanceof String) {
				String var7 = (String)var2[var4++];
				++var6;
				var5 = var7.length();
				var3 = var3 + var7;
			}
		}

		HashMap var12;
		int var15;
		for(var12 = new HashMap(); var4 < var2.length; var4 += 2) {
			Character var13 = (Character)var2[var4];
			var15 = 0;
			if(var2[var4 + 1] instanceof Item) {
				var15 = ((Item)var2[var4 + 1]).shiftedIndex;
			} else if(var2[var4 + 1] instanceof Block) {
				var15 = ((Block)var2[var4 + 1]).blockID;
			}

			var12.put(var13, Integer.valueOf(var15));
		}

		int[] var14 = new int[var5 * var6];

		for(var15 = 0; var15 < var5 * var6; ++var15) {
			char var10 = var3.charAt(var15);
			if(var12.containsKey(Character.valueOf(var10))) {
				var14[var15] = ((Integer)var12.get(Character.valueOf(var10))).intValue();
			} else {
				var14[var15] = -1;
			}
		}

		this.recipes.add(new CraftingRecipe(var5, var6, var14, var1));
	}

	public ItemStack findMatchingRecipe(int[] var1) {
		for(int var2 = 0; var2 < this.recipes.size(); ++var2) {
			CraftingRecipe var3 = (CraftingRecipe)this.recipes.get(var2);
			if(var3.matches(var1)) {
				return var3.getCraftingResult(var1);
			}
		}

		return null;
	}
}
