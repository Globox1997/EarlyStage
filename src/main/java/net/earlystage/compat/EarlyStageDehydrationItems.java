package net.earlystage.compat;

import net.earlystage.init.ItemInit;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EarlyStageDehydrationItems {

    public static final Identifier PURIFIED_WATER_ID = Identifier.of("dehydration", "purified_water");

    public static Item PURIFIED_WATER_WOODEN_BUCKET = null;
    public static Item PURIFIED_WATER_BRICK_BUCKET = null;

    public static void init() {
        Fluid purifiedWater = Registries.FLUID.get(PURIFIED_WATER_ID);
        PURIFIED_WATER_WOODEN_BUCKET = ItemInit.register("purified_water_wooden_bucket",
                new WoodenBucketItem(purifiedWater, new Item.Settings().maxCount(1)));
        PURIFIED_WATER_BRICK_BUCKET = ItemInit.register("purified_water_brick_bucket",
                new BrickBucketItem(purifiedWater, new Item.Settings().maxCount(1)));
    }

    public static boolean isPurifiedWater(FluidState fluidState) {
        return PURIFIED_WATER_WOODEN_BUCKET != null && fluidState.isOf(Registries.FLUID.get(PURIFIED_WATER_ID));
    }

}
