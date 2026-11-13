package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.earlystage.item.BarkItem;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem {

    public AxeItemMixin(ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material, effectiveBlocks, settings);
    }

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", ordinal = 0), require = 0)
    private void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        World world = context.getWorld();
        if (world.isClient() || !ConfigInit.CONFIG.stripBarkDrops) {
            return;
        }
        Item barkItem = BarkItem.BARK_ITEMS.get(world.getBlockState(context.getBlockPos()).getBlock());
        if (barkItem != null) {
            ItemEntity itemEntity = new ItemEntity(world, context.getHitPos().getX(), context.getHitPos().getY(), context.getHitPos().getZ(), new ItemStack(barkItem));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

}
