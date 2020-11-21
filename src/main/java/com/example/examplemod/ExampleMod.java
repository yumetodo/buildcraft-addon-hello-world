package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
// import cpw.mods.fml.common.registry.GameRegistry;
import buildcraft.BuildCraftCore;
@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        System.out.println("BC Wrench >> "+BuildCraftCore.wrenchItem.getUnlocalizedName());
        NonReversedShapedRecipe.addShapedRecipe(new ItemStack(Blocks.stone),//east
                "WA",
                'A',Blocks.dirt,
                'W',BuildCraftCore.wrenchItem);
        NonReversedShapedRecipe.addShapedRecipe(new ItemStack(Blocks.cobblestone),//west
                "AW",
                'A',Blocks.dirt,
                'W',BuildCraftCore.wrenchItem);
        NonReversedShapedRecipe.addShapedRecipe(new ItemStack(Blocks.stone),//east
                "WA",
                'A',Blocks.dirt,
                'W',Blocks.grass);
        NonReversedShapedRecipe.addShapedRecipe(new ItemStack(Blocks.cobblestone),//west
                "AW",
                'A',Blocks.dirt,
                'W',Blocks.grass);
    }
}
