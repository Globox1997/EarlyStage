package net.earlystage.compat;

import net.dehydration.api.DehydrationAPI;
import net.dehydration.api.FluidBehavior;
import net.earlystage.init.CompatInit;
import net.earlystage.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EarlyStageDehydrationIntegration implements DehydrationAPI {

    @Override
    public int calculateDrinkThirst(ItemStack itemStack, PlayerEntity playerEntity) {
        return 0;
    }

    @Override
    public void registerFluidBehaviors() {
        FluidBehavior.registerWaterBucketForCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WATER_BRICK_BUCKET, ItemInit.BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.WOODEN_BUCKET, CompatInit.PURIFIED_WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.BRICK_BUCKET, CompatInit.PURIFIED_WATER_BRICK_BUCKET);
    }
}
