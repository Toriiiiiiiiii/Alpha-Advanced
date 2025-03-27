package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator {
	final CraftingManager craftingManager;

	RecipeSorter(CraftingManager var1) {
		this.craftingManager = var1;
	}

	public int compareRecipes(CraftingRecipe var1, CraftingRecipe var2) {
		return var2.getRecipeSize() < var1.getRecipeSize() ? -1 : (var2.getRecipeSize() > var1.getRecipeSize() ? 1 : 0);
	}

	public int compare(Object var1, Object var2) {
		return this.compareRecipes((CraftingRecipe)var1, (CraftingRecipe)var2);
	}
}
