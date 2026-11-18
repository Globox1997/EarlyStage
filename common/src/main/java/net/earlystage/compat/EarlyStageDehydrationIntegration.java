package net.earlystage.compat;

import net.dehydration.api.DehydrationAPI;
import net.dehydration.api.FluidBehavior;
import net.earlystage.init.ItemInit;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EarlyStageDehydrationIntegration implements DehydrationAPI {

    @Override
    public int calculateDrinkThirst(ItemStack itemStack, Player playerEntity) {
        return 0;
    }

    @Override
    public void registerFluidBehaviors() {
        if (EarlyStageDehydrationItems.PURIFIED_WATER_WOODEN_BUCKET == null) {
            EarlyStageDehydrationItems.register((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));
        }
        FluidBehavior.registerWaterBucketForCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WATER_BRICK_BUCKET, ItemInit.BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.WOODEN_BUCKET, EarlyStageDehydrationItems.PURIFIED_WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.BRICK_BUCKET, EarlyStageDehydrationItems.PURIFIED_WATER_BRICK_BUCKET);
    }
}
