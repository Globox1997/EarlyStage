package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.earlystage.item.BarkItem;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockTransformer.class)
public class BlockTransformerMixin {

    @Inject(method = "transformBlock*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0), require = 0)
    private void transformBlockMixin(CallbackInfoReturnable<InteractionResult> info, @Local(argsOnly = true) UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide() || !ConfigInit.CONFIG.stripBarkDrops) {
            return;
        }
        Item barkItem = BarkItem.getBarkItem(world.getBlockState(context.getClickedPos()).getBlock());
        if (barkItem != null) {
            ItemEntity itemEntity = new ItemEntity(world, context.getClickLocation().x(), context.getClickLocation().y(), context.getClickLocation().z(), new ItemStack(barkItem));
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }

}
