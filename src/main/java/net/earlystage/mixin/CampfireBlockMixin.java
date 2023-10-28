package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BlockWithEntity {

    public CampfireBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUseMixin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
        if (!state.get(CampfireBlock.LIT) && player.getStackInHand(hand).isIn(TagInit.BARK_ITEMS)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(CampfireBlock.LIT, true));
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }
                player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                info.setReturnValue(ActionResult.SUCCESS);
            }
            info.setReturnValue(ActionResult.CONSUME);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.setBlockState(pos, state.with(CampfireBlock.LIT, false));
    }

}
