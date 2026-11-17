package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.earlystage.item.BarkItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends DiggerItem {

    public AxeItemMixin(Tier material, TagKey<Block> effectiveBlocks, Properties settings) {
        super(material, effectiveBlocks, settings);
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0), require = 0)
    private void useOnBlockMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        Level world = context.getLevel();
        if (world.isClientSide() || !ConfigInit.CONFIG.stripBarkDrops) {
            return;
        }
        Item barkItem = BarkItem.BARK_ITEMS.get(world.getBlockState(context.getClickedPos()).getBlock());
        if (barkItem != null) {
            ItemEntity itemEntity = new ItemEntity(world, context.getClickLocation().x(), context.getClickLocation().y(), context.getClickLocation().z(), new ItemStack(barkItem));
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }

}
