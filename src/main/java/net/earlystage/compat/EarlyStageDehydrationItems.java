package net.earlystage.compat;

import net.earlystage.init.ItemInit;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class EarlyStageDehydrationItems {

    public static final ResourceLocation PURIFIED_WATER_ID = ResourceLocation.fromNamespaceAndPath("dehydration", "purified_water");

    public static Item PURIFIED_WATER_WOODEN_BUCKET = null;
    public static Item PURIFIED_WATER_BRICK_BUCKET = null;

    public static void init() {
        Fluid purifiedWater = BuiltInRegistries.FLUID.get(PURIFIED_WATER_ID);
        PURIFIED_WATER_WOODEN_BUCKET = ItemInit.register("purified_water_wooden_bucket",
                new WoodenBucketItem(purifiedWater, new Item.Properties().stacksTo(1)));
        PURIFIED_WATER_BRICK_BUCKET = ItemInit.register("purified_water_brick_bucket",
                new BrickBucketItem(purifiedWater, new Item.Properties().stacksTo(1)));
    }

    public static boolean isPurifiedWater(FluidState fluidState) {
        return PURIFIED_WATER_WOODEN_BUCKET != null && fluidState.is(BuiltInRegistries.FLUID.get(PURIFIED_WATER_ID));
    }

}
