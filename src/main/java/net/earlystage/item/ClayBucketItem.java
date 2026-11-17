package net.earlystage.item;

import net.earlystage.init.ItemInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ClayBucketItem extends Item {

    public ClayBucketItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = ClayBucketItem.getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResultHolder.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockPos);
            if (!blockState.getFluidState().isEmpty() && blockState.getFluidState().is(Fluids.LAVA)) {
                user.awardStat(Stats.ITEM_USED.get(this));

                world.playSound(user, blockPos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.playSound(user, blockPos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS, 0.5f, 1.0f);

                world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                ItemStack itemStack2 = new ItemStack(ItemInit.LAVA_BRICK_BUCKET);
                ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, user, itemStack2);
                if (!world.isClientSide()) {
                    world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, itemStack2);
                }
                return InteractionResultHolder.sidedSuccess(itemStack3, world.isClientSide());
            }
            return InteractionResultHolder.fail(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

}
