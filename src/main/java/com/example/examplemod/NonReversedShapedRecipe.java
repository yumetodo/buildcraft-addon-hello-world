package com.example.examplemod;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class NonReversedShapedRecipe {
	public static ShapedRecipes addShapedRecipe(ItemStack output, Object... params) {
		int i = 0;
		int col = 0;
		int row = 0;
		StringBuilder sb = new StringBuilder();

		if (params[i] instanceof String[]) {
			String[] astring = (String[]) ((String[]) params[i++]);
			for (String str : astring) {
				col = str.length();
				sb.append(str);
			}
			row = astring.length;
		} else {
			while (params[i] instanceof String) {
				String s2 = (String) params[i++];
				++row;
				col = s2.length();
				sb.append(s2);
			}
		}
		String s = sb.toString();

		HashMap<Character, ItemStack> hashmap;

		for (hashmap = new HashMap<>(); i < params.length; i += 2) {
			Character character = (Character) params[i];
			ItemStack itemstack1 = null;

			if (params[i + 1] instanceof Item) {
				itemstack1 = new ItemStack((Item) params[i + 1]);
			} else if (params[i + 1] instanceof Block) {
				itemstack1 = new ItemStack((Block) params[i + 1], 1, 32767);
			} else if (params[i + 1] instanceof ItemStack) {
				itemstack1 = (ItemStack) params[i + 1];
			}

			hashmap.put(character, itemstack1);
		}

		ItemStack[] aitemstack = new ItemStack[col * row];

		for (int i1 = 0; i1 < col * row; ++i1) {
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0))) {
				aitemstack[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
			} else {
				aitemstack[i1] = null;
			}
		}

		ShapedRecipes shapedrecipes = new ShapedRecipes(col, row, aitemstack, output) {
			@Override
			public boolean matches(InventoryCrafting inv, World worldIn) {
				for (int i = 0; i <= 3 - this.recipeWidth; ++i) {
					for (int j = 0; j <= 3 - this.recipeHeight; ++j) {
						// When you uncomment this, you can get vanilla's behavior.
						// if (this.checkMatch(inv, i, j, true)) {
						// return true;
						// }

						if (this.checkMatch(inv, i, j, false)) {
							return true;
						}

					}
				}
				return false;
			}

			private boolean checkMatch(InventoryCrafting inv, int i, int j, boolean invert) {
				for (int k = 0; k < 3; ++k) {
					for (int l = 0; l < 3; ++l) {
						int i1 = k - i;
						int j1 = l - j;
						ItemStack itemstack = null;

						if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
							if (invert) {
								itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
							} else {
								itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
							}
						}

						ItemStack itemstack1 = inv.getStackInRowAndColumn(k, l);

						if (itemstack1 != null || itemstack != null) {
							if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
								return false;
							}

							if (itemstack.getItem() != itemstack1.getItem()) {
								return false;
							}

							if (itemstack.getItemDamage() != 32767
									&& itemstack.getItemDamage() != itemstack1.getItemDamage()) {
								return false;
							}
						}
					}
				}

				return true;
			}
		};
		GameRegistry.addRecipe(shapedrecipes);
		return shapedrecipes;
	}
}