package net.earlystage.item;

import net.earlystage.compat.EarlyStageDehydrationItems;
import net.earlystage.init.ItemInit;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WoodenBucketItem extends Item implements DispensibleContainerItem {
    private final Fluid fluid;

    public WoodenBucketItem(Fluid fluid, Item.Properties settings) {
        super(settings);
        this.fluid = fluid;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = WoodenBucketItem.getPlayerPOVHitResult(world, user, this.fluid == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResult.FAIL;
            }
            if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(blockPos);
                boolean isPurifiedWater = EarlyStageDehydrationItems.isPurifiedWater(blockState.getFluidState());
                if (blockState.getBlock() instanceof BucketPickup drainable && (blockState.getFluidState().is(Fluids.WATER) || isPurifiedWater) && blockState.hasProperty(LiquidBlock.LEVEL)
                        && blockState.getValue(LiquidBlock.LEVEL) == 0) {

                    user.awardStat(Stats.ITEM_USED.get(this));
                    user.playSound(SoundEvents.BUCKET_FILL, 1.0f, 1.0f);
                    world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);

                    ItemStack itemStack2 = new ItemStack(isPurifiedWater ? EarlyStageDehydrationItems.PURIFIED_WATER_WOODEN_BUCKET : ItemInit.WATER_WOODEN_BUCKET, 1);
                    ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, user, itemStack2);

                    if (!world.isClientSide()) {
                        drainable.pickupBlock(user, world, blockPos, blockState);

                        CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, itemStack2);
                    }
                    return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack3);
                }
                return InteractionResult.FAIL;
            }
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos3 = blockState.getBlock() instanceof LiquidBlockContainer && this.fluid == Fluids.WATER ? blockPos : blockPos2;
            if (this.emptyContents(user, world, blockPos3, blockHitResult)) {
                this.checkExtraContent(user, world, itemStack, blockPos3);
                if (user instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, blockPos3, itemStack);
                }
                user.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS.heldItemTransformedTo(WoodenBucketItem.getEmptiedStack(itemStack, user));
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    public static ItemStack getEmptiedStack(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            return ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    public void checkExtraContent(@Nullable LivingEntity player, Level world, ItemStack stack, BlockPos pos) {
        if (!(player instanceof Player playerEntity) || !playerEntity.isCreative()) {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BREAK.value(), SoundSource.PLAYERS, 0.8f, 0.8f + world.getRandom().nextFloat() * 0.4f, false);
        }
    }

    @Override
    public boolean emptyContents(@Nullable LivingEntity player, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.fluid instanceof FlowingFluid)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        boolean bl = blockState.canBeReplaced(this.fluid);
        boolean bl2 = blockState.isAir() || bl || block instanceof LiquidBlockContainer fluidFillable && fluidFillable.canPlaceLiquid(player, world, pos, blockState, this.fluid);
        if (!bl2) {
            return hitResult != null && this.emptyContents(player, world, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
        }
        if (world.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos) && this.fluid.is(FluidTags.WATER)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 2.6f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if (block instanceof LiquidBlockContainer && this.fluid == Fluids.WATER) {
            ((LiquidBlockContainer) block).placeLiquid(world, pos, blockState, ((FlowingFluid) this.fluid).getSource(false));
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        if (!world.isClientSide() && bl && !blockState.liquid()) {
            world.destroyBlock(pos, true);
        }
        if (world.setBlock(pos, this.fluid.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE) || blockState.getFluidState().isSource()) {
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        return false;
    }

    private void playEmptyingSound(@Nullable LivingEntity player, LevelAccessor world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
        world.gameEvent(player, GameEvent.FLUID_PLACE, pos);
    }
}
