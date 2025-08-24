package net.earlystage.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 1001)
public class ItemMixin {

    @Inject(method = "isCorrectForDrops", at = @At("HEAD"), cancellable = true)
    private void isCorrectForDropsMixin(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (state.isIn(BlockTags.LOGS)) {
            if (stack.isIn(ItemTags.AXES)) {
                info.setReturnValue(true);
            } else {
                info.setReturnValue(false);
            }
        }
    }
}
