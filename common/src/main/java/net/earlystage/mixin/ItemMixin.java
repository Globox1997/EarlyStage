package net.earlystage.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 1001)
public class ItemMixin {

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void isCorrectForDropsMixin(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (state.is(BlockTags.LOGS)) {
            if (stack.is(ItemTags.AXES)) {
                info.setReturnValue(true);
            } else {
                info.setReturnValue(false);
            }
        }
    }
}
